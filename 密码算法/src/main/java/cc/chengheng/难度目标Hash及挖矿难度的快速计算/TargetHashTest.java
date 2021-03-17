package cc.chengheng.难度目标Hash及挖矿难度的快速计算;

import java.math.BigInteger;

public class TargetHashTest {
    public static void main(String[] args) {
        String bits = "1a44b9f2";
        String strTargetHash = calculateTargetHash(bits);
        BigInteger difficulty = calculateDifficulty(strTargetHash);

        System.out.println("目标hash：" + strTargetHash);
        System.out.println("目标difficulty：" + difficulty);
    }

    /**
     * 快速计算当前目标的hash
     * @param bits 十六进制字符串
     * @return
     */
    public static String calculateTargetHash(String bits) {
        // 1、计算系数
        String coefficient = bits.substring(2);

        // 2、计算指数
        String exponent = bits.substring(0, 2);

        int exp = Integer.parseInt(exponent, 16);

        // 3、计算高位补0
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < 64 - exp * 2; i++) {
            prefix.append("0");
        }
        
        // 3、低位补0
        StringBuilder suffix = new StringBuilder();
        for (int i = 0; i < 64 - prefix.length(); i++) {
            suffix.append("0");
        }

        // 5、计算目标hash
        String strTargetHash = prefix + coefficient + suffix;

        return strTargetHash;
    }

    /**
     * 计算挖矿难度difficulty
     * @param strTargetHash 难度目标hash
     * @return
     */
    public static BigInteger calculateDifficulty(String strTargetHash) {
        // 创世快编号
        String geniusBlockHash = "00000000ffff0000000000000000000000000000000000000000000000000000";

        BigInteger biGeniusHash = new BigInteger(geniusBlockHash, 16);
        BigInteger biTargetHash = new BigInteger(strTargetHash, 16);

        return biGeniusHash.divide(biTargetHash);
    }

}
