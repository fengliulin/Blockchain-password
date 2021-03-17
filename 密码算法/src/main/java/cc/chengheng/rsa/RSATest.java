package cc.chengheng.rsa;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSATest {
    public static void main(String[] args) {
        String strPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJCZ0R+8TL21/oxNiEaHChH1nwNgEqgh1qDWq/6E1HvqnQH8bV8dZfmmlp7Xz/ICAKtpQus0HYjRqyw/BgJ8CUcCAwEAAQ==";
        String strPrivateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkJnRH7xMvbX+jE2IRocKEfWfA2ASqCHWoNar/oTUe+qdAfxtXx1l+aaWntfP8gIAq2lC6zQdiNGrLD8GAnwJRwIDAQABAkBFtYF1VSeBxXzjUnVB2tPl+I0h6WzVFQsNGrBr0MlGuwcoEUtW/YvY9HSck8yUv3WlsBlUI5tF7MenzWfqgBvRAiEA13DLgbQKLVYrpmP8j7echpQ7lGwXItWkGRtklMif0GsCIQCr0uKOGELkL+Y9Py4VpsueH11E7mfew+baSh2FA4nxlQIhAKgKdNGwSpfeNKHOL3sx7kcSa/5y6QDkvuBOe3+JlQ4NAiAUZRS1LxYJhdH7ZRtwQHscyrZEb1Pu8ivdrzxvxGrEPQIgDFWaj0wJbRCzS8nO8SLFE7KvmiUszpH3pnD7MJilFi0=";

        // 公钥规则对象、公钥说明书
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(strPublicKey));

        // 私钥规则对象、私钥说明书
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(strPrivateKey));

        try {
            String data = "区块链技术abc123!@#$##$%$%$%^$";

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            // 调用公钥加密方法
            String encryptData = RSAUtil.publicKeyEncryptData(data, publicKey);
            System.out.println("公钥加密后：" + encryptData);

            // 调用私钥解密方法
            String decryptData = RSAUtil.privateKeyDecryptData(encryptData, privateKey);
            System.out.println("私钥解密后：" + decryptData);

            /*-***********************************************************************/

            // 调用私钥加密方法
            String encryptData2 = RSAUtil.privateKeyEncryptData(data, privateKey);
            System.out.println("私钥加密后：" + encryptData2);

            // 调用私钥解密方法
            String decryptData2 = RSAUtil.publicKeyDecryptData(encryptData2, publicKey);
            System.out.println("公钥解密后：" + decryptData2);


        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
