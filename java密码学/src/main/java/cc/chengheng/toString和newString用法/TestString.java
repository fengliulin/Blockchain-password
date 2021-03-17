package cc.chengheng.toString和newString用法;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * toString 和 new String 原理区别
 */
public class TestString {
    public static void main(String[] args) {

        // 表示base64密文
        String str="TU0jV0xBTiNVYys5bEdiUjZlNU45aHJ0bTdDQStBPT0jNjQ2NDY1Njk4IzM5OTkwMDAwMzAwMA==";

        // 使用base64进行解码
        String rlt1=new String(Base64.decode(str));

        // 使用base64解码
        String rlt2=Base64.decode(str).toString();

        System.out.println("new String() = " + rlt1);

        System.out.println("toString() = " + rlt2);
    }
}
