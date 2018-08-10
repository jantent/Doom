package cert;


import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;

import static java.util.Base64.getEncoder;

public class TestCert {
    static String filePath = "./src/main/resource/keystore/test.jks";

    static String password = "123456";

    static String alias = "test";

    public static void main(String args[]) throws Exception{

        // 生成证书
//        CertAndKey certAndKey = generateCert();
//        KeyStoreUtil.createKeyStore(filePath,"123456","test",certAndKey.getPrivateKey(),certAndKey.getX509Certificate());

        // 从keyStore取出证书
        X509Certificate certificate = (X509Certificate) KeyStoreUtil.getCertFromJks(filePath,password,"");
        System.out.println(Base64.getEncoder().encodeToString(certificate.getEncoded()));

        // 从KeyStore取出私钥
//        PrivateKey privateKey = KeyStoreUtil.getPriKeyFromJks(filePath,password,alias);
//        System.out.println(new String(privateKey.getEncoded()));
    }

    public static CertAndKey generateCert() throws Exception{
        CertBuilder genCert = CertBuilder.getInstance();

        CertAndKey certAndKey = genCert.generateCert();
        String base64 = getEncoder().encodeToString(certAndKey.getX509Certificate().getEncoded());
        System.out.println(base64);
        return certAndKey;
    }


}
