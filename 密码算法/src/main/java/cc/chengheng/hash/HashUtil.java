package cc.chengheng.hash;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String src = "123456";

        {
            String result_md5 = HashUtil.md5(src.getBytes(StandardCharsets.UTF_8));
            String result_sha1 = HashUtil.sha1Bytes(src.getBytes(StandardCharsets.UTF_8));
            String result_sha256 = HashUtil.sha256Bytes(src.getBytes(StandardCharsets.UTF_8));
            System.out.println(result_md5);
            System.out.println(result_sha1);
            System.out.println(result_sha256);
        }

        System.out.println("==============以下不常用===============");

        {
            // 传递进去一个16进制表达式加密的结果
            byte[] arrSrc = HashUtil.hexStrToHexBytes(src);
            String result_md5 = HashUtil.md5(arrSrc);
            String result_sha1 = HashUtil.sha1Bytes(arrSrc);
            String result_sha256 = HashUtil.sha256Bytes(arrSrc);
            System.out.println(result_md5);
            System.out.println(result_sha1);
            System.out.println(result_sha256);
        }

        System.out.println("===============ripemd160不常用=================");
        byte[] arrSrc = HashUtil.hexStrToHexBytes(src);
        String result_ripemd160 = HashUtil.ripemd160Bytes(arrSrc);
        String result_src = HashUtil.ripemd160Bytes(src.getBytes(StandardCharsets.UTF_8));
        System.out.println(result_ripemd160);
        System.out.println(result_src);
    }

    public static String md5(byte[] data) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");

        // 执行摘要方法
        byte[] digest = md.digest(data);

        return bytesToHexStr(digest);
    }

    /**
     * 将字节数组转成16进制表达式
     *
     * @param bytes 字节数组
     * @return
     */
    public static String bytesToHexStr(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte byf : bytes) {
            sb.append(Integer.toString((byf & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    /**
     * SHA-1消息摘要方法
     *
     * @param data 字节数组
     */
    public static String sha1Bytes(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        byte[] digest = md.digest(data);
        return bytesToHexStr(digest);
    }

    /**
     * SHA-256消息摘要方法
     *
     * @param data 字节数组
     */
    public static String sha256Bytes(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(data);
        return bytesToHexStr(digest);
    }

    /**
     * SHA-512消息摘要方法
     *
     * @param data 字节数组
     */
    public static String sha512Bytes(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] digest = md.digest(data);
        return bytesToHexStr(digest);
    }

    /**
     * RIPEMD160 Hash消息摘要方法
     *
     * @param data 字节数组
     */
    public static String ripemd160Bytes(byte[] data) throws NoSuchAlgorithmException {
        RIPEMD160Digest ripemd160Digest = new RIPEMD160Digest();
        ripemd160Digest.update(data, 0, data.length);
        byte[] digest = new byte[ripemd160Digest.getDigestSize()];
        ripemd160Digest.doFinal(digest, 0);
        return bytesToHexStr(digest);
    }

    /**
     * 将16进制字符串转成字节数组，按照每两位一个字节进行分组
     * 16进制是2位代表一个字节，普通的字符串一个代表一个字节，汉子是一个代表3个字节
     */
    public static byte[] hexStrToHexBytes(String hexStr) {
        if (hexStr == null || hexStr.length() == 0) {
            return null;
        }

        // 如果传过来的长度是1就补0
        hexStr = (hexStr.length() == 1) ? "0" +hexStr : hexStr;

        // 每两个一分组，所以总长度/2
        byte[] arr = new byte[hexStr.length() / 2];
        byte[] temp = hexStr.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < temp.length / 2; i++) {
            arr[i] = unitBytes(temp[i * 2], temp[i * 2 + 1]);
        }
        return arr;
    }

    public static Byte unitBytes(byte src0, byte src1) {
        Byte b0 = Byte.decode("0x" + new String(new byte[]{src0}));
        b0 = (byte)(b0<<4);
        Byte b1 = Byte.decode("0x" + new String(new byte[]{src1}));
        Byte ret = (byte)(b0^b1);
        return ret;
    }

}
