package cc.chengheng.现代加密.非对称加密;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.*;

public class RSA {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 创建密钥对
        // KeyPairGenerator 密钥对生成器对象
        String algorithm = "RSA";
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);

        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 生成私钥
        PrivateKey privateKey = keyPair.getPrivate();

        // 生成公钥
        PublicKey publicKey = keyPair.getPublic();

        // 获取私钥的字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();

        // 获取公钥字节数组
        byte[] publicKeyEncoded = publicKey.getEncoded();

        // 使用base64进行编码
        String privateEncodeString = Base64.encode(privateKeyEncoded);
        String publicEncodeString = Base64.encode(publicKeyEncoded);

        // 打印公钥和私钥
        System.out.println(privateEncodeString);
        System.out.println(publicEncodeString);

    }
}
