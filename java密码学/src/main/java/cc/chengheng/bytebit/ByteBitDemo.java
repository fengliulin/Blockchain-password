package cc.chengheng.bytebit;

import java.nio.charset.StandardCharsets;

public class ByteBitDemo {
    public static void main(String[] args) {
        // 一个中文对应三个字节
        // UTF-8 ： 一个中文对应三个字节
        // GBK: 一个中文对应二个字节
        // 英文无论什么编码都是1个字节
        String a= "镠";
        byte[] bytes = a.getBytes(StandardCharsets.UTF_8);
        for (byte aByte : bytes) {
            System.out.println(aByte);
            String s = Integer.toBinaryString(aByte);
            System.out.println(s);
        }
    }
}
