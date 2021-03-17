package cc.chengheng.ascii.凯撒加密解密;

/**
 * 凯撒移位加密和解密
 */
public class KaiserDemo {
    public static void main(String[] args) {
       // 原文
        String input = "Hello World";

        // 凯撒移位加密
        // 右边移动3位
        int key = 3;

        // 加密
        String s = encryptKaiser(input);
        System.out.println(s);

        // 解密
        String s1 = decryptKaiser(s, key);
        System.out.println(s1);
    }

    /**
     * 解密
     * @param s 密文
     * @param key 密钥
     * @return 解密后的字符串
     */
    private static String decryptKaiser(String s, int key) {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            int b = aChar;
            b -= 3;
            char newB = (char)b;
            sb.append((char)b);
        }
        return sb.toString();
    }

    /**
     * 加密
     * @param input 原文
     * @return 加密后的字符串
     */
    private static String encryptKaiser(String input) {
        StringBuilder sb = new StringBuilder();

        // 把字符串变成字节数组
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            int b = aChar;
            b = b + 3; // 右移动3位
            char newB = (char)b;
            sb.append(newB);
        }

        return sb.toString(); // 密文
    }
}
