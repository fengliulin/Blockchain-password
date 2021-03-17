package cc.chengheng.rsa;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

/**
 * RSA公钥和私钥创建
 */
public class RSAKeyCreator {
    public static void main(String[] args) {
        createKeyPairs();
    }

    /**
     * 生成密钥对
     */
    public static void createKeyPairs() {
//        Security.addProvider(new BouncyCastleProvider());

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

            // 初始化密钥大小，随机, 限制长度
            generator.initialize(512, new SecureRandom());

            // 生成密钥对
            KeyPair keyPair = generator.generateKeyPair();

            // 生成私钥
            PrivateKey privateKey = keyPair.getPrivate();

            // 生成公钥
            PublicKey publicKey = keyPair.getPublic();

            // base64 对公钥进行编码转字符串
            byte[] publicKeyEncode = Base64.getEncoder().encode(publicKey.getEncoded());
            String strPublicKey = new String(publicKeyEncode, StandardCharsets.UTF_8);

            // base64 对私钥进行编码转字符串
            byte[] privateKeyEncode = Base64.getEncoder().encode(privateKey.getEncoded());
            String strPrivateKey = new String(privateKeyEncode, StandardCharsets.UTF_8);

            System.out.println(strPublicKey);
            System.out.println("=================");
            System.out.println(strPrivateKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
