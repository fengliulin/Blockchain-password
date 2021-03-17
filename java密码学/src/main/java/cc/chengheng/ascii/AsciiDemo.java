package cc.chengheng.ascii;

public class AsciiDemo {
    public static void main(String[] args) {
        char a = 'A';
        int b = a;
        System.out.println(b);

        // 字符串对应的ASCII编码
        String str = "AaZ";
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            System.out.println((int)aChar);
        }

    }
}
