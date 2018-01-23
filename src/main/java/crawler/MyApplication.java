package crawler;

import crawler.client.MyHttpClient;
import crawler.parse.ParseUtil;
import springBoot.util.PrintUtil;

public class MyApplication {

    public static void main(String[] args){
        MyHttpClient myHttpClient = new MyHttpClient();
        String url = "http://www.koal.com";
        String result = myHttpClient.sendGet(url);
        PrintUtil.print(result);
        ParseUtil.parseLinks(url);
    }
}
