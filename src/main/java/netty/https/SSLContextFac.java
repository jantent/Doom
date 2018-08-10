package netty.https;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

public class SSLContextFac {

    static String filePath = "./src/main/resource/keystore/test.jks";

    static String password = "123456";

    static String alias = "test";

    public static SSLContext createSSlContent() throws Exception{

        SSLContext sslContext = SSLContext.getInstance("TLSv1");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore keyStore = getKeyStore(filePath,password);
        kmf.init(keyStore,password.toCharArray());
        sslContext.init(kmf.getKeyManagers(),null,null);
        return sslContext;

    }

    private static KeyStore getKeyStore(String filePath, String password){
        FileInputStream fis = null;
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("JKS");
            File file = new File(filePath);
            fis = new FileInputStream(file);
            keyStore.load(fis,password.toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                }

            }
        }
        return keyStore;

    }
}
