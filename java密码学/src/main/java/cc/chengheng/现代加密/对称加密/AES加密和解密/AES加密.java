package cc.chengheng.现代加密.对称加密.AES加密和解密;



import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES加密 {
    public static void main(String[] args) throws Exception {
        // 原文
        String input = "镠霖";

        // 定义key, AES加密：密钥必须是16个字节
        String key = "1234567887654321";

        // 算法
        String transformation = "AES";

        // 加密类型
        String algorithm = "AES";

        // 创建加密对象
        Cipher cipher = Cipher.getInstance(transformation);

        // 创建加密规则。第一个参数key 密钥转字节. 第二个参数采用什么加密类型
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);

        // 进行加密初始化 第一个参数表示 加密模式，解密模式。 第二个蚕食：加密规则
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // 调用加密方法, 参数表示原文的字节数组
        byte[] bytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
        // 打印密文，如果直接打印就会出现乱码，因为在ASCII编码上找不到字符
        System.out.println(new String(bytes, StandardCharsets.UTF_8));

        // 创建base64对象, 打印加密后的结果，就不会有乱码, 两种方法Base64都可以
//        String encode = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(bytes);
        String s = Base64.getEncoder().encodeToString(bytes);
        System.out.println(s);
    }
}
