package com.movefeng.hexoblogadmin.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class EncryptUtils {

    /**
     * base64加密
     *
     * @param value value
     * @return
     */
    public static String base64Encode(String value) {
        byte[] encodedCookieBytes = Base64.getEncoder().encode(value.getBytes());
        return new String(encodedCookieBytes);
    }

    /**
     * base64解密
     *
     * @param base64Value base64Value
     * @return
     */
    public static String base64Decode(String base64Value) {
        try {
            byte[] decodedCookieBytes = Base64.getDecoder().decode(base64Value);
            return new String(decodedCookieBytes);
        } catch (Exception ex) {
            log.debug("Unable to Base64 decode value: " + base64Value);
            return null;
        }
    }

    /**
     * SHA-256加密
     *
     * @param text str
     * @return str
     */
    public static String SHA256(final String text) {
        return SHA(text, "SHA-256");
    }

    /**
     * SHA-512加密
     *
     * @param strText str
     * @return str
     */
    public static String SHA512(final String strText) {
        return SHA(strText, "SHA-512");
    }

    /**
     * 对字符串md5加密
     *
     * @param str 传入要加密的字符串
     * @return MD5加密后的字符串
     */
    public static String MD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串 SHA 加密
     *
     * @param text text
     * @return str
     */
    private static String SHA(final String text, final String strType) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (text != null && text.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(text.getBytes());
                // 得到 byte 類型结果
                byte[] buffer = messageDigest.digest();

                StringBuilder strHexString = new StringBuilder();
                for (byte b : buffer) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    public static void main(String[] args) {
        System.out.println(SHA256("123"));
    }
}
