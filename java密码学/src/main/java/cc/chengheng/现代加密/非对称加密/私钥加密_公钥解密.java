package cc.chengheng.现代加密.非对称加密;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class 私钥加密_公钥解密 {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        String input = "冯镠霖"; // 原文

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
        /*System.out.println(privateEncodeString);
        System.out.println(publicEncodeString);*/

        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);

        // 对加密进行初始化. 第一个参数：加密的模式。 第二个参数：你想使用公钥加密还是私钥加密
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        // 使用私钥进行加密
        byte[] bytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        String encode = Base64.encode(bytes);
        System.out.println(encode);

        // 私钥解密, 需要用公钥解密
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] bytes1 = cipher.doFinal(Base64.decode(encode));
        System.out.println(new String(bytes1));
    }
}
