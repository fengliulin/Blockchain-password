package cc.chengheng.现代加密.非对称加密;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class 本地文件读取私钥和公钥 {
    public static void main(String[] args) throws Exception {

        String input = "冯镠霖"; // 原文

        // 创建密钥对
        // KeyPairGenerator 密钥对生成器对象
        String algorithm = "RSA";

        //生成密钥对并保存在本地文件中
        generateKeyToFile(algorithm, "a.pub.txt", "a.pri.txt");

        // 从文件中读取私钥
        PrivateKey privateKeyString = getPrivateKey("a.pri.txt", algorithm);
        System.out.println(privateKeyString);

        // 从文件中读取公钥
        PublicKey publicKeyString = getPublicKey("a.pub.txt", algorithm);
        System.out.println(publicKeyString);


        /*KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);

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
        System.out.println(s1);*/
    }

    /**
     * 读取公钥
     * @param publicPath 公钥路径
     * @param algorithm  算法
     * @return
     */
    private static PublicKey getPublicKey(String publicPath, String algorithm) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        String publicKeyString = FileUtils.readFileToString(new File(publicPath), Charset.defaultCharset());

        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        // 创建私钥key的规则
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));

        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * 读取私钥
     *
     * @param privatePath priPath 私钥路径
     * @param algorithm   算法
     * @return 返回私钥的key对象
     */
    private static PrivateKey getPrivateKey(String privatePath, String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKeyString = FileUtils.readFileToString(new File(privatePath), Charset.defaultCharset());

        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        // 创建私钥key的规则
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));

        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
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
