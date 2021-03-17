package cc.chengheng;

public class TestBitcoinAddress {
    public static void main(String[] args) {

        String hexPrivateKey = "46D2463B52390023BBA5F347C8A3C346A550D8C89995DA765E71BF6F0C417CB0";

        // 测试1、将16进制私钥转换成WIF格式或WIF压缩格式
        String wifPrivateKey = BitcoinAddressUtil.generatePrivateKeyWIF(hexPrivateKey, false);
        System.out.println(wifPrivateKey);

        //5imtBQBoecwKpGKMHTJu3sLRuyEfbRdZD1Sw9CcRJcpEQbdPYbz

        // 测试2、将wif格式私钥转成16进制格式
        String result = BitcoinAddressUtil.getHexPrivateKey("5imtBQBoecwKpGKMHTJu3sLRuyEfbRdZD1Sw9CcRJcpEQbdPYbz");
        System.out.println(result);

        // 测试3、验证wif私钥是否有效
        boolean flag = BitcoinAddressUtil.validateWifPrivateKey("5imtBQBoecwKpGKMHTJu3sLRuyEfbRdZD1Sw9CcRJcpEQbdPYbz");
        System.out.println(flag);

        // 测试4 根据公钥生成比特币地址
        String addr = BitcoinAddressUtil.generateAddressPublicKey("04A0589D54B92D646B1E348884C2CDF8EB660551570C9CE32975208FE72ED82FFB50940BF82E719FF4C44C8E3971FE7E0AD821592AE299B1E373E62549C3589317");
        System.out.println(addr);

        // 测试5、根据16进制的私钥生成比特币地址
        String addr2 = BitcoinAddressUtil.generateAddressHexPrivateKey(hexPrivateKey);
        System.out.println(addr2);

        // 测试6、根据wif格式私钥生成比特币地址
        String addr3 = BitcoinAddressUtil.generateAddressWifPrivateKey(wifPrivateKey);
        System.out.println(addr3);
    }
}
