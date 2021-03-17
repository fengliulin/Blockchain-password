package cc.chengheng.rsa;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAUtil {

    /**
     * RSA公钥加密
     *
     * @param data      需要加密的明文字符串
     * @param publicKey 公钥对象
     * @return
     */
    public static String publicKeyEncryptData(String data, PublicKey publicKey) {
        try {
            // 因为公钥和密钥生成也是RSA，所以这里算法也要RSA
            Cipher cipher = Cipher.getInstance("RSA");

            // 公钥加密
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return Base64.encode(encryptData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * RSA私钥解密
     *
     * @param data       需要解密的字符串
     * @param privateKey 私钥对象
     * @return
     */
    public static String privateKeyDecryptData(String data, PrivateKey privateKey) {
        try {
            // 因为加密转了base64，所以要先把加密的数据先base64解码
            byte[] base64Decode = Base64.decode(data);

            // 因为公钥和密钥生成也是RSA，所以这里算法也要RSA
            Cipher cipher = Cipher.getInstance("RSA");

            // 私钥解密
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decryptData = cipher.doFinal(base64Decode);

            return new String(decryptData, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * RSA私钥加密
     *
     * @param data      需要加密的明文字符串
     * @param privateKey 私钥对象
     * @return
     */
    public static String privateKeyEncryptData(String data, PrivateKey privateKey) {
        try {
            // 因为公钥和密钥生成也是RSA，所以这里算法也要RSA
            Cipher cipher = Cipher.getInstance("RSA");

            // 公钥加密
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] encryptData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return Base64.encode(encryptData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * RSA公钥解密
     *
     * @param data       需要解密的字符串
     * @param publicKey  公钥对象
     * @return
     */
    public static String publicKeyDecryptData(String data, PublicKey publicKey) {
        try {
            // 因为加密转了base64，所以要先把加密的数据先base64解码
            byte[] base64Decode = Base64.decode(data);

            // 因为公钥和密钥生成也是RSA，所以这里算法也要RSA
            Cipher cipher = Cipher.getInstance("RSA");

            // 私钥解密
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            byte[] decryptData = cipher.doFinal(base64Decode);

            return new String(decryptData, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
