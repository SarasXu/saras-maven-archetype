package com.saras.archetype.utils;

import com.saras.archetype.core.exception.BizErrorException;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 共同的签名和验签工具
 * <p>
 * create by hongsir in 2017/8/22 13:50:00
 */
public class SignUtils {
    private final static Logger LOG = LoggerFactory.getLogger(SignUtils.class);

    /**
     * 验签<br>
     * 1.移除sign
     * 2.sign为空抛出异常
     * 3.将剩余的参数进行签名
     * 4.比对sign
     * 5.返回boolean
     *
     * @param params
     * @return
     */
    public static boolean checkSign(Map<String, String> params, String key) {
        commonCheck(params, key);
        String requestSign = params.remove("isign");
        if (StringUtils.isBlank(requestSign)) {
            LOG.info("验签失败,签名字段【isign】不能为空");
            throw new BizErrorException("验签失败,签名字段【isign】不能为空");
        }
        String checkSign = getSignature(params, key);
        return requestSign.equals(checkSign);
    }

    /**
     * 签名生成算法<br>
     * 1、将所有请求参数按照key值排升序
     * 2、参数转换为小写
     * 3、参数名-值拼成字符串
     * 4、附加上约定appkey和版本号信息后进行base64编码
     * 5、两次MD5加密后返回
     *
     * @param params<String,String> params 请求参数集，所有参数必须已转换为字符串类型
     * @return 签名后结果
     */
    public static String getSignature(Map<String, String> params, String key) {
        commonCheck(params, key);
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<>(params);
        Set<Entry<String, String>> entrySet = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> param : entrySet) {
            String value = param.getValue();
            sb.append(param.getKey().toLowerCase()).append("=").append(value);
        }
        sb.append(key);
        String base64 = encode(sb.toString());
        //两次MD5编码后返回去
        return md5(md5(base64));
    }

    private static void commonCheck(Map<String, String> params, String key) {
        if (StringUtils.isBlank(key)) {
            LOG.info("加密key值不能为空");
            throw new BizErrorException("加密key值不能为空");
        }
        if (null == params) {
            LOG.info("签名参数不能为空,请检查参数");
            throw new BizErrorException("签名参数不能为空,请检查参数");
        }
    }

    /**
     * 将字符串BASE64编码
     *
     * @param base64String
     * @return
     */
    public static String encode(String base64String) {
        try {
            return new String(Base64.encode(base64String.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 字符串经过MD5加密返回加密结果
     *
     * @param sourceStr 加密源字符串
     * @return md5后字符串
     */
    public static String md5(String sourceStr) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte[] b = md.digest();
            int i;
            StringBuilder buf = new StringBuilder();
            for (byte aB : b) {
                i = aB;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    /**
     * 获取万年不重复的uuid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取大写的md5
     *
     * @param sourceStr
     * @return
     */
    public static String md5UpperCase(String sourceStr) {
        return md5(sourceStr).toUpperCase();
    }

    /**
     * 密码对比
     *
     * @param str
     * @param md5
     * @return
     */
    public static boolean checkPwd(String str, String md5) {
        return md5UpperCase(str).equals(md5.toUpperCase());
    }
}
