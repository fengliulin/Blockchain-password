package cc.chengheng.数字签名;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 签名
 */
public class SignatureDemo {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        String a = "123";
        
        PublicKey publicKey = SignatureDemo.getPublicKey("a.pub.txt", "RSA");
        PrivateKey privateKey = SignatureDemo.getPrivateKey("a.pri.txt", "RSA");

        // 获取数字签名
        String signatureDate = getSignature(a, "SHA256withRSA", privateKey);

        System.out.println(signatureDate);

        // 校验签名
        boolean b = verifySignature(a, "SHA256withRSA", publicKey, signatureDate);
        System.out.println(b);
    }

    /**
     * 校验签名
     *
     * @param input         原文
     * @param algorithm     算法
     * @param publicKey     公钥
     * @param signatureDate 签名的密文
     * @return
     */
    private static boolean verifySignature(String input, String algorithm, PublicKey publicKey, String signatureDate) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        // 获取签名对象
        Signature signature = Signature.getInstance(algorithm);

        // 初始化校验
        signature.initVerify(publicKey);

        // 传入原文
        signature.update(input.getBytes());

        // 校验数据
        return signature.verify(Base64.decode(signatureDate));
    }

    /**
     * 生成数字签名
     *
     * @param input      表示原文
     * @param algorithm  表示算法
     * @param privateKey 私钥key
     * @return
     */
    private static String getSignature(String input, String algorithm, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        // 获取签名对象
        Signature signature = Signature.getInstance(algorithm);

        // 初始化签名
        signature.initSign(privateKey);

        // 传入原文
        signature.update(input.getBytes());

        byte[] sign = signature.sign();

        // 使用base64进行编码
        return Base64.encode(sign);
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
}
