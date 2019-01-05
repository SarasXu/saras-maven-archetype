package com.saras.archetype.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: liuli
 * @Description:
 * @Date: Create in 20:43 2017/4/25
 * @Modified By:
 */
public class CommonUtils {
    @SuppressWarnings("unchecked")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;

        } else if (obj instanceof String && (StringUtils.isBlank(obj.toString()))) {
            return true;

        } else if (obj instanceof Number && ((Number) obj).doubleValue() == 0) {
            return true;

        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;

        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;

        } else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
            return true;

        }
        return false;
    }

    /**
     * 合并两个数组
     *
     * @param a
     * @param b
     * @return
     */
    public static String[] concat(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * 有一个空就返回true
     *
     * @param args
     * @return
     */
    public static boolean existEmpty(Object... args) {

        for (int i = 0; i < args.length; i++) {
            if (isEmpty(args[i])) {
                return true;
            }
        }
        return false;
    }


    public static int[] stringArrayToIntArray(String str[]) {
        int array[] = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        return array;
    }

    /**
     * 有一个空就返回true
     *
     * @param jo
     * @param args JSONObject 对应key
     * @return
     */
    public static boolean existEmpty(JSONObject jo, Object... args) {

        for (int i = 0; i < args.length; i++) {
            if (isEmpty(jo.get(args[i]))) {
                return true;
            }
        }
        return false;
    }


    public static boolean isEmpty(String obj) {
        if (obj == null) {
            return true;
        } else if (StringUtils.isBlank(obj)) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String obj) {
        if (obj == null || obj == "null") {
            return false;
        } else if (StringUtils.isBlank(obj)) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static boolean isEmpty(Map obj) {
        if (obj == null) {
            return true;
        } else if (obj.isEmpty()) {
            return true;
        }
        return false;
    }


    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 比较版本
     *
     * @param ver1
     * @param ver2
     * @return ver1>ver2
     */
    public static boolean compareVersion(String ver1, String ver2) {
        String[] v1s = ver1.split("\\.");
        String[] v2s = ver2.split("\\.");

        if (v1s.length > v2s.length) {

            for (int i = 0; i < v2s.length; i++) {
                if (Integer.parseInt(v2s[i]) > Integer.parseInt(v1s[i])) {
                    return false;
                } else if (Integer.parseInt(v2s[i]) < Integer.parseInt(v1s[i])) {
                    return true;
                }
            }
            return true;
        } else {

            for (int i = 0; i < v1s.length; i++) {
                if (Integer.parseInt(v2s[i]) > Integer.parseInt(v1s[i])) {
                    return false;
                } else if (Integer.parseInt(v2s[i]) < Integer.parseInt(v1s[i])) {
                    return true;
                }
            }
            return false;
        }
    }


    //获得全球唯一性的id
    public static String getId() {
        String id = UUID.randomUUID().toString();//生成的id942cd30b-16c8-449e-8dc5-028f38495bb5中间含有横杠，
        id = id.replace("-", "");//替换掉中间的那个斜杠
        return id;
    }

}
