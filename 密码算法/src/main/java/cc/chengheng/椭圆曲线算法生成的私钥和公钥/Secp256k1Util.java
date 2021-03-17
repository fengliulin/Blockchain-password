package cc.chengheng.椭圆曲线算法生成的私钥和公钥;

import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.LinkedList;

/**
 * 椭圆算法生成的私钥和公钥
 */
public class Secp256k1Util {
    /** 随机数算法 */
    private static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";

    /** 随机数算法提供者 */
    private static final String RANDOM_NUMBER_ALGORITHM_PROVIDER = "SUN";

    /**
     * 随机产生私钥
     * @return
     */
    public static byte[] generatePrivateKey() {
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM, RANDOM_NUMBER_ALGORITHM_PROVIDER);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        BigInteger privateKeyCheck = BigInteger.ZERO;

        /*
         * 随机选取一个32字节的数，大小介于
         * 1 ～ 0xFFFF FFFF FFFF FFFF FFFF FFFF FFFF FFFE BAAE DCE6 AF48 A03B BFD2 5E8C D036 4141"
         */
        BigInteger maxKey = new BigInteger("00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364140", 16);

        // 产生随机字节数组
        byte[] privateKeyAttempt = new byte[32];
        secureRandom.nextBytes(privateKeyAttempt);

        // 第二个参数二进制数字
        privateKeyCheck = new BigInteger(1, privateKeyAttempt);

        while (privateKeyCheck.compareTo(BigInteger.ZERO) == 0 || privateKeyCheck.compareTo(maxKey) == 1) {
            secureRandom.nextBytes(privateKeyAttempt);
            privateKeyCheck = new BigInteger(1, privateKeyAttempt);
        }

        return privateKeyAttempt;
    }

    /**
     * 通过私钥生成公钥
     * @param privateKey
     * @return
     */
    public static byte[] generatePublicKey(byte[] privateKey) {
        // 椭圆曲线加密算法
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");

        ECPoint pointQ = spec.getG().multiply(new BigInteger(1, privateKey));

        return pointQ.getEncoded();
    }

    /*public static byte[][] signTransaction(byte[] message, byte[] privateKey) {
        Security.addProvider(new BouncyCastleProvider());
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");

        ECDSASigner ecdsaSigner = new ECDSASigner();
        ECDomainParameters domin = new ECDomainParameters(spec.getCurve(), spec.getG(), spec.getN());
        ECPrivateKeyParameters privateKeyParms = new ECPrivateKeyParameters(new BigInteger(1, privateKey), domin);

        // 随机签名参数
        ParametersWithRandom params = new ParametersWithRandom(privateKeyParms);
        ecdsaSigner.init(true, params);

        BigInteger[] sig = ecdsaSigner.generateSignature(message); // 生成签名

        LinkedList<byte[]> sigData = new LinkedList<>();
        byte[] publicKey = generatePublicKey(privateKey);
        byte recoveryId = getRecoveryId(sig[0].toByteArray(), sig[1].toByteArray(), message, publicKey);
        for (BigInteger sigChunk : sig) {
            sigData.add(sigChunk.toByteArray());
        }
        sigData.add(new byte[]{recoveryId});
        return sigData.toArray(new byte[][]{});
    }*/

    /*public static void main(String[] args) {
        // 根据私钥
        String strPrivateKey = "20738c3da5ba9839192dbea48d5107e71bd6b55d35d9d070b546711b9d44dd5a";
        byte[] arrPrivateKey = HashUtil.hexStrToHexBytes(strPrivateKey);
        // 根据私钥生成公钥
        byte[] arrPubKey = generatePublicKey(arrPrivateKey);
        String strPubKey = HashUtil.bytesToHexStr(arrPubKey);
        System.out.println(strPubKey);

        // 获取私钥
        byte[] arrResult = generatePrivateKey();
        String strResult = HashUtil.bytesToHexStr(arrResult);
        System.out.println(strResult);
    }*/
}
