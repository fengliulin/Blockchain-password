package cc.chengheng.现代加密.非对称加密;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class 保存公钥和私钥_代码抽取 {
    public static void main(String[] args) throws Exception {

        String input = "冯镠霖"; // 原文

        // 创建密钥对
        // KeyPairGenerator 密钥对生成器对象
        String algorithm = "RSA";

        //生成密钥对并保存在本地文件中
//        generateKeyToFile(algorithm, "a.pub", "a.pri");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);

        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 生成私钥
        PrivateKey privateKey = keyPair.getPrivate();

        // 生成公钥
        PublicKey publicKey = keyPair.getPublic();

        // 加密密文
        String s = encryptRSA(algorithm, privateKey, input);
        System.out.println(s);

        // 解密
        String s1 = decryptRSA(algorithm, publicKey, s);
        System.out.println(s1);
    }

    /**
     * 保存公钥和私钥保存到根目录
     *
     * @param algorithm   算法
     * @param publicPath  公钥路径
     * @param privatePath 私钥路径
     */
    private static void generateKeyToFile(String algorithm, String publicPath, String privatePath) throws NoSuchAlgorithmException, IOException {

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

        // 把公钥和私钥保存到根目录
        FileUtils.writeStringToFile(new File(publicPath), publicEncodeString, StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(new File(privatePath), privateEncodeString, StandardCharsets.UTF_8);
    }

    /**
     * 解密数据
     *
     * @param algorithm 算法
     * @param publicKey 密钥密文
     * @param encrypted 密文
     * @return 原文
     * @throws Exception
     */
    public static String decryptRSA(String algorithm, Key publicKey, String encrypted) throws Exception {
        // 创建加密解密对象
        Cipher cipher = Cipher.getInstance(algorithm);

        // 私钥解密, 需要用公钥解密
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] bytes1 = cipher.doFinal(Base64.decode(encrypted));
        return new String(bytes1);
    }

    /**
     * 使用密钥加密数据
     *
     * @param algorithm  算法
     * @param privateKey 密钥
     * @param input      原文
     * @return : 密文
     * @throws Exception
     */
    public static String encryptRSA(String algorithm, Key privateKey, String input) throws Exception {
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);

        // 对加密进行初始化. 第一个参数：加密的模式。 第二个参数：你想使用公钥加密还是私钥加密
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        // 使用私钥进行加密
        byte[] bytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

        return Base64.encode(bytes);
    }
}
