package cc.chengheng.rsa;

import cc.chengheng.hash.HashUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * 数字签名测试
 */
public class SignatureTest {
    public static void main(String[] args) {
        String strPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJCZ0R+8TL21/oxNiEaHChH1nwNgEqgh1qDWq/6E1HvqnQH8bV8dZfmmlp7Xz/ICAKtpQus0HYjRqyw/BgJ8CUcCAwEAAQ==";
        String strPrivateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkJnRH7xMvbX+jE2IRocKEfWfA2ASqCHWoNar/oTUe+qdAfxtXx1l+aaWntfP8gIAq2lC6zQdiNGrLD8GAnwJRwIDAQABAkBFtYF1VSeBxXzjUnVB2tPl+I0h6WzVFQsNGrBr0MlGuwcoEUtW/YvY9HSck8yUv3WlsBlUI5tF7MenzWfqgBvRAiEA13DLgbQKLVYrpmP8j7echpQ7lGwXItWkGRtklMif0GsCIQCr0uKOGELkL+Y9Py4VpsueH11E7mfew+baSh2FA4nxlQIhAKgKdNGwSpfeNKHOL3sx7kcSa/5y6QDkvuBOe3+JlQ4NAiAUZRS1LxYJhdH7ZRtwQHscyrZEb1Pu8ivdrzxvxGrEPQIgDFWaj0wJbRCzS8nO8SLFE7KvmiUszpH3pnD7MJilFi0=";

        String data = "数字签名发送方，接收方验证有没有被篡改，网络传输重要信息要加密";

        String encryptData = generateSignatureMessageDigest(strPrivateKey, data);
        System.out.println(encryptData);

        boolean flag = verifySignatureMessage(strPublicKey, data, encryptData);
        System.out.println(flag);
    }

    /**
     * 信息的发送方 模拟的数字签名的过程
     * 签名的过程就是先对原有的字符串进行hash， 在和私钥一起，进行加密。
     *  发送给对方的时候， 把这个信息以及公钥，以及原始的明文，都发送给对方
     * @param stringPrivateKey
     * @param data
     */
    public static String generateSignatureMessageDigest(String stringPrivateKey, String data) {
        // 私钥说明书
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(stringPrivateKey));

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            // 对data进行hash算法后，执行私钥加密
            data = HashUtil.sha1Bytes(data.getBytes(StandardCharsets.UTF_8));

            return RSAUtil.privateKeyEncryptData(data, privateKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 接收方 模拟接收方验证签名的过程
     * 生成签名需要通过网络传输，传输到对方，对方根据接收到的信息去验证这个签名的信息
     *
     * 解密的过程：利用传过来的公钥，对这个签名信息进行解密，解密之后，就得到hash之后的那个信息， 传过来的数据也是明文data，在hash
     *           和解密得到的hash进行equals，相等就没有被篡改，就代表传输过来的这个明文，中间没有被任何的串改
     */
    private static boolean verifySignatureMessage(String stringPublicKey, String data, String encryptMessage) {
        // 公钥说明书
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(stringPublicKey));

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            // 解密对方签名传过来的信息，得到对方hash之后的摘要
            String decryptData = RSAUtil.publicKeyDecryptData(encryptMessage, publicKey);

            // 得到对方hash之后的摘要和 【明文data进行hash之后】 进行比对，相同，就没有被篡改
            if (Objects.requireNonNull(decryptData).equals(HashUtil.sha1Bytes(data.getBytes(StandardCharsets.UTF_8)))) {
                System.out.println("明文hash以后与公钥解密后信息相同，所以明文没有被篡改");
                System.out.println("数字验证OK!");
                return true;
            } else {
                System.out.println("明文hash以后与公钥解密后信息不相同，所以明文被篡改");
                System.out.println("数字验证error!");
                return false;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return false;
    }

}
