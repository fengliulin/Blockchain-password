package cc.chengheng.模拟挖矿实现.util;

import java.nio.charset.StandardCharsets;

public class HexUtil {

    /**
     * 将字节数组转成16进制字符串
     *
     * @param src 字节数组
     * @return
     */
    public static String bytesToHexStr(byte[] src) {
        String strHex = "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < src.length; i++) {
            strHex = Integer.toHexString(src[i] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex);
        }

        /*for (byte byf : bytes) {
            sb.append(Integer.toString((byf & 0xff) + 0x100, 16).substring(1));
        }*/

        return sb.toString().trim();
    }

    /**
     * 将十进制转换为十六进制字符串
     *
     * @param decimalStr 十进制的字符串
     * @return 十六进制字符串
     */
    public static String decimalStrToHexStr(String decimalStr) {
        long number = Long.parseLong(decimalStr);
        return Long.toHexString(number);
    }

    /**
     * 字符串转字节数组
     */
    public static byte[] decode(String src) {
        int m = 0, n = 0;
        int byteLen = src.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = 1 * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;
    }

    /**
     * 将16进制字符串大小端翻转 例如： 123456 -> 563412
     *
     * @param str
     * @return
     */
    public static String hexStrReverse(String str) {
        int length = str.length();
        String[] arr = new String[str.length() / 2];
        int j = 0;
        for (int i = length; i > 1; i -= 2) {
            arr[j] = str.substring(i - 2, i);
            j++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        return sb.toString().toLowerCase();
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
        hexStr = (hexStr.length() == 1) ? "0" + hexStr : hexStr;

        // 每两个一分组，所以总长度/2
        byte[] arr = new byte[hexStr.length() / 2];
        byte[] temp = hexStr.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < temp.length / 2; i++) {
            arr[i] = unitBytes(temp[i * 2], temp[i * 2 + 1]);
        }
        return arr;
    }

    private static Byte unitBytes(byte src0, byte src1) {
        Byte b0 = Byte.decode("0x" + new String(new byte[]{src0}));
        b0 = (byte)(b0 << 4);
        Byte b1 = Byte.decode("0x" + new String(new byte[]{src1}));
        Byte ret = (byte)(b0 ^ b1);
        return ret;
    }
}
