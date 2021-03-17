package cc.chengheng.现代加密.消息摘要;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要算法，为了防止篡改
 *  常见的加密算法: md5 sha1 sha256 sha512
 */
public class Digest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 原文
        String input = "冯镠霖"; // md5值 154bdc065b6e78756d0ade5479d18425 // base64 FUvcBltueHVtCt5UedGEJQ==

        // 算法
        String algorithm = "MD5"; // 只需要在这里修改加密算法就可以了

        // 创建消息摘要对象
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        // 执行消息摘要算法
        byte[] digest1 = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder stringBuilder = new StringBuilder();

        // 使用base64进行转码
        System.out.println(Base64.encode(digest1));

        // 对密文进行迭代
        for (byte b : digest1) {
            // 把密文转换成16进制

            /*
             * int类型是32位，读取字节是8位，所以高位不够补0，但是出现负号会在高位，也就是24位补1。
             * & 0xff 会在低8位补1111 1111，因为16进制的ff就是8个1。高位的24位会全部补0，因为是int类型，32位。
             * 由于高位24位全是0，和负号补的24位1， 与， 那么就全是0， 低位全是1，和任何二进制 与 还是原来的二进制
             * 这样转出来的 十六进制 每一个字节都在 1 或 2个， 但是消息摘要加密一个十六进制的，要补0
             * 为什么二进制转十六进制不要负号，暂时就这样用吧，以后也许某天就明白了
             */
            String s = Integer.toHexString(b & 0xff);
            // 判断如果密文的长度是1，需要在高位补0
            if (s.length() == 1) {
                s = "0" + s;
            }
            stringBuilder.append(s);
        }
        System.out.println(stringBuilder);
    }
}
