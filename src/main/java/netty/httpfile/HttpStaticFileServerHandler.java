package netty.httpfile;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.SystemPropertyUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class HttpStaticFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
	public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
	public static final int HTTP_CACHE_SECONDS = 60;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		if (request.getMethod() != HttpMethod.GET) {
			sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
		}

		final String uri = request.getUri();
		final String path = sanitizeUri(uri);
		if (path == null) {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}

		File file = new File(path);
		if (file.isHidden() || !file.exists()) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}

		if (file.isDirectory()) {
			if (uri.endsWith("/")) {
				sendListing(ctx, file, uri);
			} else {
				sendRedirect(ctx, uri + "/");
			}
			return;
		}

		if (!file.isFile()) {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}

		String ifModifiedSince = request.headers().get(HttpHeaders.Names.IF_MODIFIED_SINCE);
		if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
			SimpleDateFormat dataFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
			Date ifModifiedSinceDate = dataFormatter.parse(ifModifiedSince);

			long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
			long fileLastModifiedSeconds = file.lastModified() / 1000;
			if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
				sendNotModified(ctx);
				return;
			}
		}
		
		RandomAccessFile raf;
		raf = new RandomAccessFile(file, "r");
		
		long fileLength = raf.length();
		
		HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		setContentTypeHeader(response, file);
		setDateAndCacheHeaders(response, file);
		response.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);

		//写入最初的列和头
		ctx.write(response);

		//写入内容
		ChannelFuture sendFileFuture;
		ChannelFuture lastContentFuture;
		if(ctx.pipeline().get(SslHandler.class) == null){
			sendFileFuture =
					ctx.write(new DefaultFileRegion(raf.getChannel(), 0, fileLength),ctx.newProgressivePromise());
			lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		}else {
			sendFileFuture = 
					ctx.writeAndFlush(new HttpChunkedInput(new ChunkedFile(raf, 0, fileLength,8192)),
							ctx.newProgressivePromise());
			lastContentFuture = sendFileFuture;
		}
		
		sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
			
			@Override
			public void operationComplete(ChannelProgressiveFuture future) throws Exception {
				System.err.println(future.channel() + " Transfer complete.");
			}
			
			@Override
			public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
				if(total<0){
					System.err.println(future.channel()+"Transfer progress:"+progress);
				}else {
					System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
				}
			}
		});
		if(!HttpHeaders.isKeepAlive(request)){
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		if (ctx.channel().isActive()) {
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

	/**
	 * 处理uri，转换成绝对路径
	 * 
	 * @param uri
	 * @return
	 */
	private static String sanitizeUri(String uri) {
		// 解析path
		try {
			uri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new Error(e);
		}

		if (uri.isEmpty() || uri.charAt(0) != '/') {
			return null;
		}
		// 转换文件分隔符
		uri = uri.replace('/', File.separatorChar);

		// 简单的安全检查
		// 在实际环境中 做事必须严肃
		if (uri.contains(File.separator + '.') || uri.contains('.' + File.separator) || uri.charAt(0) == '.'
				|| uri.charAt(uri.length() - 1) == '.' || INSECURE_URI.matcher(uri).matches()) {
			return null;
		}
		// 转换成绝对路径
		return SystemPropertyUtil.get("user.dir") + File.separator + uri;
	}

	private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[^-\\._]?[^<>&\\\"]*");

	private static void sendListing(ChannelHandlerContext ctx, File dir, String dirPath) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");

		StringBuilder buf = new StringBuilder().append("<!DOCTYPE html>\r\n")
				.append("<html><head><meta charset='utf-8' /><title>").append("Listing of: ").append(dirPath)
				.append("</title></head><body>\r\n")

				.append("<h3>Listing of: ").append(dirPath).append("</h3>\r\n").append("<ul>")
				.append("<li><a href=\"../\">..</a></li>\r\n");
		for (File f : dir.listFiles()) {
			if (f.isHidden() || !f.canRead()) {
				continue;
			}

			String name = f.getName();
			if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
				continue;
			}

			buf.append("<li><a href=\"").append(name).append("\">").append(name).append("</a></li>\r\n");
		}

		buf.append("</ul></body></html>\r\n");
		ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
		response.content().writeBytes(buffer);
		buffer.release();

		// 在错误消息发送前关闭连接
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
		response.headers().set(HttpHeaders.Names.LOCATION, newUri);

		// 在错误消息发送前关闭连接
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
				Unpooled.copiedBuffer("Failure: " + status + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * When file timestamp is the same as what the browser is sending up, send a
	 * "304 Not Modified"
	 * 
	 * @param ctx
	 */
	private static void sendNotModified(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_MODIFIED);
		setDateHeader(response);

		// Close the connection as soon as the error message is sent.
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * Sets the Date header for the HTTP response
	 * 
	 * @param response
	 */
	private static void setDateHeader(FullHttpResponse response) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
		dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

		Calendar time = new GregorianCalendar();
		response.headers().set(HttpHeaders.Names.DATE, time.getTime());
	}

	private static void setDateAndCacheHeaders(HttpResponse response, File fileToCache) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
		dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

		Calendar time = new GregorianCalendar();
		response.headers().set(HttpHeaders.Names.DATE, dateFormatter.format(time.getTime()));

		time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
		response.headers().set(HttpHeaders.Names.EXPIRES, dateFormatter.format(time.getTime()));
		response.headers().set(HttpHeaders.Names.CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
		response.headers().set(HttpHeaders.Names.LAST_MODIFIED,
				dateFormatter.format(new Date(fileToCache.lastModified())));
	}

	private static void setContentTypeHeader(HttpResponse response, File file) {
		MimetypesFileTypeMap mFileTypeMap = new MimetypesFileTypeMap();
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, mFileTypeMap.getContentType(file.getPath()));
	}
}
