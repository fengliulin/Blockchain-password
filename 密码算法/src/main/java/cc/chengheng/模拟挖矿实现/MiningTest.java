package cc.chengheng.模拟挖矿实现;

import cc.chengheng.模拟挖矿实现.util.HashUtil;
import cc.chengheng.模拟挖矿实现.util.HexUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 挖矿测试
 */
public class MiningTest {
    public static void main(String[] args) {
        String bits = "1a44b9f2";
        String version = "00000001";
        String preHash =  "00000000000008a3a41b85b8b29ad444def299fee21793cd8b9e567eab02cd81";
        String merkleRoot =  "2b12fcf1b09288fcaff797d71e950e71ae42b91e8bdb2304758dfcffc2b620e3";
        String timeStamp =  "1305998791";
        String nonce =  "9546a142";
        // 获取难度目标
        String targetHash = getMiningTarget(bits);

        // 验证挖矿结果
        // 1、时间戳是long型，转换16进制
        timeStamp = HexUtil.decimalStrToHexStr(timeStamp);
        System.out.println("timeStamp的十六进制：" + timeStamp);

        // 2、计算当前区块hash
        // version 固定值；preHash 固定值；merkleRoot 相对来说也是固定的；
        // 挖矿需要那么大的算力是因为 timeStamp 和 nonce 是不断变化的。
        // nonce 真正的挖矿，是用了while死循环，nonce不断的去叠加，当叠加到一定程度的时候 求出是否小于它
        // nonce比特币用的不是死循环
        String blockHash = calculateHash(version, preHash, merkleRoot, timeStamp, bits, nonce);
        System.out.println("blockHash为:" + blockHash);

        // 4、比较当前区块hash与target的大小
        BigInteger hashBlock = new BigInteger(blockHash, 16);
        BigInteger hashTarget = new BigInteger(targetHash, 16);
        if (hashBlock.compareTo(hashTarget) == 0) {
            System.out.println("====");
        } else {
            if (hashBlock.compareTo(hashTarget) == -1) {
                System.out.println(" < 挖矿成功！");
            } else {
                System.out.println(" > 挖矿无效");
            }
        }
    }

    public static String getMiningTarget(String bits) {
        // 1、获取目标难度系数-16进制数
        String coefficient = bits.substring(2);// 从索引2开始截取，0开始算
        System.out.println("系数是: " + coefficient);

        // 2、获取目标难度中的指数
        String exponent = bits.substring(0, 2);
        System.out.println(exponent);

        // 3、计算目标数
        String targetDecimal = Integer.parseInt(coefficient, 16)
                * Math.pow(2, 8 * (Integer.parseInt(exponent, 16)) - 3) + "";
        System.out.println("科学计数法:" + targetDecimal);

        // 4、科学技术法转普通数值
        BigDecimal bigDecimal = new BigDecimal(targetDecimal);
        BigInteger bigInteger = new BigInteger(bigDecimal.toPlainString());

        String targetHex = bigInteger.toString(16);
        System.out.println("16进制：" + targetHex);
        System.out.println("16进制长度：" + targetHex.length());

        // 5、计算长度，高位补0
        int targetLen = targetHex.length();
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < 64 - targetLen; i++) {
            prefix.append("0");
        }
        String targetHash = prefix.toString() + targetHex;
        System.out.println("64位目标hash：" + targetHash);
        return targetHash;
    }

    /**
     * 计算哈希
     * @param version 版本
     * @param preHash 前一个块
     * @param merkleRoot 默克尓根
     * @param timestamp 时间戳
     * @param bits
     * @param nonce
     * @return
     */
    public static String calculateHash(String version,
                                       String preHash,
                                       String merkleRoot,
                                       String timestamp,
                                       String bits,
                                       String nonce) {

        //region 让16进制的字符串，每两个一组，因为2个是8bit，进行翻转
        version = HexUtil.hexStrReverse(version);
        preHash = HexUtil.hexStrReverse(preHash);
        merkleRoot = HexUtil.hexStrReverse(merkleRoot);
        timestamp = HexUtil.hexStrReverse(timestamp);
        bits = HexUtil.hexStrReverse(bits);
        nonce = HexUtil.hexStrReverse(nonce);
        //endregion

        String headerHex = version + preHash + merkleRoot + timestamp + bits + nonce;

        return HashUtil.sha256HexString(HashUtil.sha256HexString(headerHex));
    }
}
