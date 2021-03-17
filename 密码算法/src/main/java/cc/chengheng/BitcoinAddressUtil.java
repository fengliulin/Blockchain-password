package cc.chengheng;

import cc.chengheng.hash.HashUtil;
import cc.chengheng.椭圆曲线算法生成的私钥和公钥.Secp256k1Util;

import java.security.NoSuchAlgorithmException;

/**
 * 比特币地址转换
 */
public class BitcoinAddressUtil {

    /**
     * 将16禁止私钥转换成WIF格式或WIF-compressed格式
     *
     * @param hexPrivateKey 通过SECP256K1生成的16进制私钥
     * @param flag          是否采用压缩格式
     * @return
     */
    public static String generatePrivateKeyWIF(String hexPrivateKey, boolean flag) {
        String versionStr = "";
        if (flag) {
            versionStr = "80" + hexPrivateKey + "01";
        } else {
            versionStr = "80" + hexPrivateKey;
        }


        try {
            // 16进制字符串转字节
            byte[] bytes = HashUtil.hexStrToHexBytes(versionStr);

            // 获取校验码,双哈希之后的16进制字符串
            String hashDouble1 = HashUtil.sha256Bytes(bytes);

            // 16进制字符串转字节
            byte[] bytes1 = HashUtil.hexStrToHexBytes(hashDouble1);

            String hashDouble2 = HashUtil.sha256Bytes(bytes1);

            // 取前4个字节(8个十六进制)作为校验码，因为2个十六进制是1个字节，也就是8个bit
            String checkNum = hashDouble2.substring(0, 8);

            // 生成16进制私钥
            String strPrivateKey = versionStr + checkNum;

            // 用base58表示法变换以下地址（这就是最常见的比特币地址形态）
            return Base58Util.encode(HashUtil.hexStrToHexBytes(strPrivateKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将WIF格式私钥返回到16进制格式
     *
     * @param wifPrivateKey WIF格式私钥
     * @return
     */
    public static String getHexPrivateKey(String wifPrivateKey) {

        boolean flag = true;
        if (wifPrivateKey.length() == 51 && wifPrivateKey.indexOf("5") == 0) {
            flag = false;
        }
        byte[] arrPrivateKey = Base58Util.decode(wifPrivateKey);
        String hexStr = HashUtil.bytesToHexStr(arrPrivateKey);

        // 去前缀version版本好 去压缩标记符，去除校验码
        String result = "";
        if (flag) {
            result = hexStr.substring(2, hexStr.length() - 10);
        } else {
            result = hexStr.substring(2, hexStr.length() - 8);
        }

        return result;
    }

    /**
     * 验证WIF格式私钥是否有效
     * 也就是验证比特币钱包是否有效
     *
     * @param wifPrivateKey
     * @return
     */
    public static boolean validateWifPrivateKey(String wifPrivateKey) {
        try {
            byte[] arrPrivateKey = Base58Util.decode(wifPrivateKey);
            String hexStr = HashUtil.bytesToHexStr(arrPrivateKey);
            String checkNum = hexStr.substring(hexStr.length() - 8);
            String versionStr = hexStr.substring(0, hexStr.length() - 8);

            //region 双256 哈希
            // 生成新的校验码
            // 16进制字符串转字节
            byte[] bytes = HashUtil.hexStrToHexBytes(versionStr);

            // 获取校验码,双哈希之后的16进制字符串
            String hashDouble1 = HashUtil.sha256Bytes(bytes);

            // 16进制字符串转字节
            byte[] bytes1 = HashUtil.hexStrToHexBytes(hashDouble1);

            String hashDouble2 = HashUtil.sha256Bytes(bytes1).substring(0, 8);
            //endregion

            // 验证
            if (checkNum.equals(hashDouble2)) {
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 通过公钥生成Base58编码的比特币地址
     */
    public static String generateAddressPublicKey(String hexPubKey) {
        try {
            // 1、 公钥是否有效
            if (hexPubKey.length() != 130) {
                return null;
            }

            // 2、 计算公钥的hash256
            String sha256One = HashUtil.sha256Bytes(HashUtil.hexStrToHexBytes(hexPubKey));

            //3、RipeMD160
            String ripemd160 = HashUtil.ripemd160Bytes(HashUtil.hexStrToHexBytes(sha256One));

            //4、增加版本号
            String versionStr = "00" + ripemd160;

            //5、计算两次hash
            //region 双256 哈希
            // 生成新的校验码
            // 16进制字符串转字节
            byte[] bytes = HashUtil.hexStrToHexBytes(versionStr);

            // 获取校验码,双哈希之后的16进制字符串
            String hashDouble1 = HashUtil.sha256Bytes(bytes);

            // 16进制字符串转字节
            byte[] bytes1 = HashUtil.hexStrToHexBytes(hashDouble1);

            String hashDouble2 = HashUtil.sha256Bytes(bytes1);
            //endregion

            // 6、获取校验码
            String checkNum = hashDouble2.substring(0, 8);

            //7、形成16进制比特币地址
            String addrHex = versionStr + checkNum;
            return Base58Util.encode(HashUtil.hexStrToHexBytes(addrHex));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 通过16进制私钥生成比特币地址
     *
     * @param hexPrivateKey 16进制格式私钥
     */
    public static String generateAddressHexPrivateKey(String hexPrivateKey) {
        byte[] arrPrivateKey = HashUtil.hexStrToHexBytes(hexPrivateKey);
        byte[] arrPubKey = Secp256k1Util.generatePublicKey(arrPrivateKey);
        return generateAddressPublicKey(HashUtil.bytesToHexStr(arrPubKey));
    }

    /**
     * 通过16进制私钥生成比特币地址
     *
     */
    public static String generateAddressWifPrivateKey(String wifPrivateKey) {
        String hexPrivateKey = getHexPrivateKey(wifPrivateKey);
        return generateAddressHexPrivateKey(hexPrivateKey);
    }
}
