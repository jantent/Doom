package httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import springBoot.util.GsonUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class HttpPostClient {
    private RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000).build();

    public static void main(String args[]) throws Exception{

        String url = "http://127.0.0.1:3180/download/FIRSTCA.crl";
        HttpPostClient postClient = new HttpPostClient();
        String response = postClient.sendHttpGet(url);
        System.out.println("响应为："+response);

    }

    public static void verifyTimeStamp() throws Exception{
        HttpPostClient postClient = new HttpPostClient();
        String httpUrl = "http://"+"192.168.109.69"+":4018";
        TimeStampRequest request = new TimeStampRequest();
        request.setType("verify");
        String source = "测试一下";
        request.setSource(Base64.encodeBase64String(source.getBytes("utf-8")));
        String timestamp = "MIIDFTADAgEAMIIDDAYJKoZIhvcNAQcCoIIC/TCCAvkCAQExCzAJBgUrDgMCGgUAMFYGCyqGSIb3DQEJEAEEoEcERTBDAgEBBgQqAwQBMCEwCQYFKw4DAhoFAAQUNIrandyZBKpyasPw/H+n89h1OdQCASAYDzIwMTgwNTAyMDQ0ODUzWgEBAKCCAVAwggFMMIHzoAMCAQICCBESIhARERERMAwGCCqBHM9VAYN1BQAwNDERMA8GA1UECAwIbnVsbFJvb3QxHzAdBgNVBAMMFuWPr+S/oeaXtumXtFNlcnZlckNlcnQwIBcNMTgwNDI1MjE1MjA5WhgPMjA2ODA0MjUyMTUyMDlaMCExHzAdBgNVBAMMFuWPr+S/oeaXtumXtFNlcnZlckNlcnQwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAASGYxaGKHLJ0ADqSnrrLXbg+yLaPilsQkxxZBG0Y/VHgi5Qq6qqYl1/hHh+Qf+2AIFxZHhKeQ82zQNv7dN6r9qiMAoGCCqBHM9VAYN1A0gAMEUCIDu1T8isPysGuDx7GqxAU1ypmaYx3raLV8o8vOWB1NFRAiEAqd5hKOCxovwUQQhspD38l3uGXHXptnvn3ObInDew9XUxggE5MIIBNQIBATBAMDQxETAPBgNVBAgMCG51bGxSb290MR8wHQYDVQQDDBblj6/kv6Hml7bpl7RTZXJ2ZXJDZXJ0AggREiIQERERETAJBgUrDgMCGgUAoIGMMBoGCSqGSIb3DQEJAzENBgsqhkiG9w0BCRABBDAcBgkqhkiG9w0BCQUxDxcNMTgwNTAyMDQ0ODUzWjAjBgkqhkiG9w0BCQQxFgQU/Bi+oRt7yBa2AzDbKxH3c3IPvMAwKwYLKoZIhvcNAQkQAgwxHDAaMBgwFgQUgQUh1E0foVrccJW1eqbkRfYFJzAwDAYIKoEcz1UBg3UFAARGMEQCIA1/ijFT5LFTPk3mDKGGIAd1ees9boc5yD6l8059k/5rAiAwVRmbTRh5XEt/rjjRkL/qCtVTO2ZKdL9jXc6djNsD+w==";
        request.setTimeStamp(timestamp);
        String verifyresult = postClient.sendHttpPost(httpUrl, GsonUtils.toJsonString(request));
        System.out.println("验证的返回结果是： "+verifyresult);
    }



    public static void applyTimeStamp() throws Exception {
        HttpPostClient postClient = new HttpPostClient();
        String httpUrl = "http://"+"10.0.90.50"+":5860";
        TimeStampRequest request = new TimeStampRequest();
        request.setType("apply");
        String source = "测试一下";
        request.setSource(Base64.encodeBase64String(source.getBytes("utf-8")));
        System.out.println(GsonUtils.toJsonString(request));
        String result =  postClient.sendHttpPost(httpUrl, GsonUtils.toJsonString(request));
        System.out.println();
        System.out.println("申请的结果为："+result);
    }

    /**
     *
     * @param httpUrl
     * @param params
     * @return
     */
    public String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, Charset.forName("utf-8"));
            stringEntity.setContentType("application/text-html");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return senPost(httpPost);
    }

    private String senPost(HttpPost httpPost) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;

        try {
            // 创建默认的httpClient实例
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity,Charset.forName("utf-8"));
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                // 关闭连接
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return responseContent;
        }
    }


    /**
     * get
     *
     * @param httpUrl
     * @return
     */
    @SuppressWarnings("unused")
    private String sendHttpGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendGet(httpGet);
    }

    @SuppressWarnings("finally")
    private String sendGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity);
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
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return responseContent;
        }
    }

}
