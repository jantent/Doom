package crawler.client;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MyHttpClient {
    //connectionRequestTimeout:从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
    //connectTimeout:连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
    //socketTimeout:服务器返回数据(response)的时间，超过该时间抛出read timeout
    private RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000)
            .setConnectionRequestTimeout(2000).build();

    public String sendGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            // 执行请求
            response = httpClient.execute(httpGet);
            // 服务端响应码
            int statusCode = response.getStatusLine().getStatusCode();
            System.err.println("服务端响应码为： " + statusCode);
            entity = response.getEntity();
            if (entity != null) {
                responseContent = EntityUtils.toString(entity, "UTF-8");
            } else {
                responseContent = "响应为空";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
                EntityUtils.consume(entity);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return responseContent;
    }
}
