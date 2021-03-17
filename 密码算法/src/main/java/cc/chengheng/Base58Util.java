package cc.chengheng;

import cc.chengheng.hash.HashUtil;

public class Base58Util {

    // 得到的是ASCII编码对应的数字
    public static final char[] ALPHABET = "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();

    private static final int[] INDEXES = new int[128];

    private static final int BASE_58 = ALPHABET.length;
    private static final int BASE_256 = 256;

    static {
        // 初始化数组
        for (int i = 0; i < INDEXES.length; i++) {
            INDEXES[i] = -1;
        }

        // 把取出来的每一个字符的ASCII编码对应的数字当作INDEXES的下标存入数组内，对应的值为ALPHABET的顺序
        for (int i = 0; i < ALPHABET.length; i++) {
            INDEXES[ALPHABET[i]] = i;
        }
    }

    public static void main(String[] args) {
        // 加密
        String strHex = "00010966776006953D5567439E5E39F86A0D273BEED61967F6";
        String encode = encode(HashUtil.hexStrToHexBytes(strHex));
        System.out.println(encode);

        // 解密
        byte[] arrResult = decode("16tWkk9qHSB3pEoQbtVjNEhLbp7WmTJVm");
        String strResult = HashUtil.bytesToHexStr(arrResult);
        System.out.println(strResult);
    }

    /**
     * Base58编码
     * @param input
     * @return
     */
    public static String encode(byte[] input) {
        if (input.length == 0) {
            return "";
        }

        input = copyOfRange(input, 0, input.length);

        int zeroCount = 0;
        while (zeroCount < input.length && input[zeroCount] == 0) {
            ++zeroCount;
        }

        byte[] temp = new byte[input.length * 2];
        int j = temp.length;

        int startAt = zeroCount;
        while (startAt < input.length) {
            byte mod = divmod58(input, startAt);
            if (input[startAt] == 0) {
                ++startAt;
            }
            temp[--j] = (byte)ALPHABET[mod];
        }

        while (j < temp.length && temp[j] == ALPHABET[0]) {
            ++j;
        }

        while (--zeroCount >= 0) {
            temp[--j] = (byte)ALPHABET[0];
        }

        byte[] output = copyOfRange(temp, j, temp.length);
        return new String(output);
    }

    /**
     * 解码
     * @param input
     * @return
     */
    public static byte[] decode(String input) {
        if (input.length() == 0) {
            return new byte[0];
        }

        byte[] input58 = new byte[input.length()];

        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            int digit58 = -1;
            if (c >= 0 && c < 128) {
                digit58 = INDEXES[c];
            }
            if (digit58 < 0) {
                throw new RuntimeException("Not a Base58 input: " + input);
            }

            input58[i] = (byte) digit58;
        }


        int zeroCount = 0;
        while (zeroCount < input58.length && input58[zeroCount] == 0) {
            ++zeroCount;
        }


        byte[] temp = new byte[input.length()];
        int j = temp.length;

        int startAt = zeroCount;
        while (startAt < input58.length) {
            byte mod = divmod256(input58, startAt);
            if (input58[startAt] == 0) {
                ++startAt;
            }

            temp[--j] = mod;
        }


        while (j < temp.length && temp[j] == 0) {
            ++j;
        }

        return copyOfRange(temp, j - zeroCount, temp.length);
    }

    private static byte divmod58(byte[] number, int startAt) {
        int remainder = 0;
        for (int i = startAt; i < number.length; i++) {
            int digit256 = (int) number[i] & 0xFF;
            int temp = remainder * BASE_256 + digit256;
            number[i] = (byte) (temp / BASE_58);
            remainder = temp % BASE_58;
        }

        return (byte) remainder;
    }

    private static byte divmod256(byte[] number58, int startAt) {
        int remainder = 0;
        for (int i = startAt; i < number58.length; i++) {
            int digit58 = (int) number58[i] & 0xFF;
            int temp = remainder * BASE_58 + digit58;
            number58[i] = (byte) (temp / BASE_256);
            remainder = temp % BASE_256;
        }

        return (byte) remainder;
    }



    private static byte[] copyOfRange(byte[] source, int from, int to) {
        byte[] range = new byte[to - from];
        System.arraycopy(source, from, range, 0, range.length);
        return range;
    }
}
