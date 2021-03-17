package cc.chengheng.bytebit;

import java.nio.charset.StandardCharsets;

public class ByteBit {
    public static void main(String[] args) {
        String a = "a";
        byte[] bytes = a.getBytes(StandardCharsets.UTF_8);
        for (byte aByte : bytes) {
            int c = aByte;
            System.out.println(c);

            // byte字节，对应的bit是多少
            String s = Integer.toBinaryString(c);
            System.out.println(s);
        }
    }
}
