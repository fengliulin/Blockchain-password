package cc.chengheng.base64;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.nio.charset.StandardCharsets;

/**
 * 当字节不够的时候，需要使用 = 进行补齐
 */
public class base64 {
    public static void main(String[] args) {
        // 1 表示一个字节，不够3个字节补等号 result:MQ==
        System.out.println(Base64.encode("1".getBytes(StandardCharsets.UTF_8)));

        // 如果是2个字节，就补齐一个 = 号
        System.out.println(Base64.encode("12".getBytes(StandardCharsets.UTF_8)));

        // 如果是3个字节就不会补 = 号
        System.out.println(Base64.encode("123".getBytes(StandardCharsets.UTF_8)));

        // 镠霖 是6个字节， 6 * 8 = 48。  刚好被整除，所以就没有 = 号
        System.out.println(Base64.encode("镠霖".getBytes(StandardCharsets.UTF_8)));
    }
}
