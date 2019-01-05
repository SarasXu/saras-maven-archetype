package com.saras.archetype.utils;

import com.saras.archetype.core.exception.BizErrorException;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * description:
 * saras_xu@163.com 2017-11-25 00:11 创建
 */
public class AESSignUtils {

    public final static String ENCODING = "UTF-8";

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte aBuf : buf) {
            String hex = Integer.toHexString(aBuf & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 生成密钥
     * 自动生成base64 编码后的AES128位密钥
     */
    public static String getAESKey(String keyGen) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(keyGen.getBytes());
            kgen.init(128, random);//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kgen.generateKey();
            byte[] b = sk.getEncoded();
            return parseByte2HexStr(b);
        } catch (Exception e) {
            throw new BizErrorException("生成随机key失败");
        }
    }

    /**
     * AES 加密
     *
     * @param base64Key base64编码后的 AES key
     * @param text      待加密的字符串
     * @return 加密后的byte[] 数组
     * @throws Exception
     */
    public static String getAESEncode(String base64Key, String text) {
        try {
            byte[] key = parseHexStr2Byte(base64Key);
            SecretKeySpec sKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
            return Base64.encode(cipher.doFinal(text.getBytes(ENCODING)));
        } catch (Exception e) {
            throw new BizErrorException("使用随机keyAES加密失败");
        }
    }

    /**
     * AES解密
     *
     * @param base64Key base64编码后的 AES key
     * @param text      待解密的字符串
     * @return 解密后的byte[] 数组
     * @throws Exception
     */
    public static String getAESDecode(String base64Key, String text) {
        try {
            byte[] key = parseHexStr2Byte(base64Key);
            SecretKeySpec sKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
            byte[] bytes = cipher.doFinal(Base64.decode(text));
            return new String(bytes, ENCODING);
        } catch (Exception e) {
            throw new BizErrorException("随机keyAES解密业务参数失败");
        }
    }
}
