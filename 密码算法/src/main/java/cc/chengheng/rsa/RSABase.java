package cc.chengheng.rsa;

/**
 * 非对称加密模拟原理，了解即可
 * 真正工作使用也就是用jdk自带的已经封装好的现成的api来使用
 */
public class RSABase {
    public static void main(String[] args) {
        // 2个质数
        int p = 11, q = 3;

        int N = 0, F = 0;

        int e = 0; // 公钥的信息
        int d = 0; // 私钥的信息
        int c = 0; // 加密之后的数值
        int m = 0; // 加密之前的数值

        N = p * q;
        F = (p - 1) * (q - 1);

        for (int i = 2; i < N; i++) {
            if ((F + 1) % i == 0) {
                e = i;
                break;
            }
        }

        d = (F + 1) / e; // 取反的过程简化了下，实际是非常复杂，这里只是模拟下

        // 公钥: (N, e); 私钥 (N, d)
        // 公钥: (33, 3); 私钥 (33, 7)

        m = 31; // 明文数据

        // 计算 加密
        c = (int)(Math.pow(m, e) % N);
        System.out.println("密文：c = " + c);

        // 计算 解密
        m = (int)Math.pow(c, d) % N;
        System.out.println("明文 m = " + m);

        System.out.println(Math.pow(2, 2));
    }
}
