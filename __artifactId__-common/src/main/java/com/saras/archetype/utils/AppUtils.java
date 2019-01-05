package com.saras.archetype.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.saras.archetype.api.utils.IdUtils;
import com.saras.archetype.core.exception.BizErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

/**
 * description:
 * saras_xu@163.com 2017-03-07 08:54 创建
 */
public class AppUtils {

    private static final Map<String, String> AREA = initArea();
    private static final String VERIFY_CODES = "10X98765432";

    private static final Pattern LEAP_YEAR_15 = Pattern
            .compile("^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$");
    private static final Pattern NORMAL_YEAR_15 = Pattern
            .compile("^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$");

    private static final Pattern LEAP_YEAR_18 = Pattern
            .compile("^[1-9][0-9]{5}[1-9][0-9]{3}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$");
    private static final Pattern NORMAL_YEAR_18 = Pattern
            .compile("^[1-9][0-9]{5}[1-9][0-9]{3}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$");

    private static final String REGEX_CENT_NO = "(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)";

    /**
     * 大月
     */
    private static List<Integer> BIG_MONTHS = Lists.newArrayList();

    static {
        BIG_MONTHS.add(1);
        BIG_MONTHS.add(3);
        BIG_MONTHS.add(5);
        BIG_MONTHS.add(7);
        BIG_MONTHS.add(8);
        BIG_MONTHS.add(10);
        BIG_MONTHS.add(12);
    }


    /**
     * 生成25位流水号编号
     *
     * @return
     */
    public static String newNo() {
        return IdUtils.newCode();
    }

    /**
     * 生成25位编码
     *
     * @return
     */
    public static String newCodeNo() {
        return IdUtils.newCode();
    }

    /**
     * 生成25位编码，可自定义2位首位code
     *
     * @return
     */
    public static String newCodeNo(String code) {
        if (isBlank(code) || code.length() != 2) {
            return IdUtils.newCode();
        } else {
            return IdUtils.newCode(code);
        }
    }

    /**
     * 是否包含特殊字符(姓名除下划线、数字、字母以及·以外的所有字符) 包含：true 不包含：false
     * 支持英文名、中文名、少数名族特殊字符效验
     *
     * @param str
     * @return
     */
    public static boolean hasSpecSymbol(String str) {
        return !isBlank(str) && !str.matches("^([\\u4e00-\\u9fa5]{1,20}|[a-zA-Z\\.\\s]{1,20})$");
    }

    /**
     * 是否全为数字 是：true 不是：false
     *
     * @param str
     * @return
     */
    public static boolean isAllNumber(String str) {
        return !isBlank(str) && str.matches("^\\d+$");
    }

    /**
     * 是否为手机
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        return !isBlank(str) && str.matches("^(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[57])[0-9]{8}$");
    }

    /**
     * 检查字符串是否是邮件地址
     *
     * @param address
     * @return
     */
    public static boolean isEmail(String address) {
        return !isBlank(address) && address.matches("^\\s*?(.+)@(.+?)\\s*$");
    }

    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <p/>
     * <p>
     * <pre>
     * AppUtils.isBlank(null)      = true
     * AppUtils.isBlank("")        = true
     * AppUtils.isBlank(" ")       = true
     * AppUtils.isBlank("yyy")     = false
     * AppUtils.isBlank("  yyy  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查字符串是否不是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <p/>
     * <p>
     * <pre>
     * AppUtils.isNotBlank(null)      = false
     * AppUtils.isNotBlank("")        = false
     * AppUtils.isNotBlank(" ")       = false
     * AppUtils.isNotBlank("yyy")     = true
     * AppUtils.isNotBlank("  yyy  ") = true
     * </pre>
     *
     * @param str 要检查的字符串
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }


    public static boolean hasBlank(String... str) {
        if (str == null) {
            return true;
        }
        for (String aStr : str) {
            if (isBlank(aStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将map转换成JSONObject
     *
     * @param map
     * @return JSONObject
     */
    public static JSONObject mapToJson(Map<?, ?> map) {
        return JSONObject.parseObject(JSON.toJSONString(map));
    }


    public static boolean isCertNo(String certNo) {
        if (certNo == null || (certNo.length() != 15 && certNo.length() != 18)) {
            //身份证号码位数不对
            return false;
        }

        //正则验证是否为身份证
        if (!certNo.matches(REGEX_CENT_NO)) {
            return false;
        }

        char[] certNoArr = certNo.toCharArray();

        String areaCode = String.copyValueOf(certNoArr, 0, 2);
        if (AREA.get(areaCode) == null) {
            // 身份证地区非法
            return false;
        }

        switch (certNo.length()) {
            case 15:
                // 15位身份号码检测
                Pattern p = null;
                int year = Integer.parseInt(String.copyValueOf(certNoArr, 6, 2)) + 1900;
                if (year % 4 == 0 || (year % 100 == 0 && year % 4 == 0)) {
                    p = LEAP_YEAR_15;
                } else {
                    p = NORMAL_YEAR_15;
                }
                if (!p.matcher(certNo).matches()) {
                    return false;
                }
                break;
            case 18:
                //18位身份号码检测
                year = Integer.parseInt(String.copyValueOf(certNoArr, 6, 4));
                if (year % 4 == 0 || (year % 100 == 0 && year % 4 == 0)) {
                    p = LEAP_YEAR_18;
                } else {
                    p = NORMAL_YEAR_18;
                }

                if (!p.matcher(certNo).matches()) {
                    return false;
                }

                //计算校验位
                int[] factor = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
                int sum = 0;
                for (int i = 0; i < 17; i++) {
                    sum += (certNoArr[i] - 48) * factor[i];
                }
                int index = sum % 11;

                //判断校验位
                char verifyCode = VERIFY_CODES.charAt(index);
                if (verifyCode != certNoArr[17]) {
                    return false;
                }
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * 从request中获取所有参数放入map
     *
     * @param request
     * @return
     */
    public static Map<String, String> getRequestMap(HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String parameterName = en.nextElement();
            map.put(parameterName, request.getParameter(parameterName));
        }
        return map;
    }

    private static Map<String, String> initArea() {
        Map<String, String> areaMap = new HashMap<String, String>();
        areaMap.put("11", "北京");
        areaMap.put("12", "天津");
        areaMap.put("13", "河北");
        areaMap.put("14", "山西");
        areaMap.put("15", "内蒙古");
        areaMap.put("21", "辽宁");
        areaMap.put("22", "吉林");
        areaMap.put("23", "黑龙江");
        areaMap.put("31", "上海");
        areaMap.put("32", "江苏");
        areaMap.put("33", "浙江");
        areaMap.put("34", "安徽");
        areaMap.put("35", "福建");
        areaMap.put("36", "江西");
        areaMap.put("37", "山东");
        areaMap.put("41", "河南");
        areaMap.put("42", "湖北");
        areaMap.put("43", "湖南");
        areaMap.put("44", "广东");
        areaMap.put("45", "广西");
        areaMap.put("46", "海南");
        areaMap.put("50", "重庆");
        areaMap.put("51", "四川");
        areaMap.put("52", "贵州");
        areaMap.put("53", "云南");
        areaMap.put("54", "西藏");
        areaMap.put("61", "陕西");
        areaMap.put("62", "甘肃");
        areaMap.put("63", "青海");
        areaMap.put("64", "宁夏");
        areaMap.put("65", "新疆");
        areaMap.put("71", "台湾");
        areaMap.put("81", "香港");
        areaMap.put("82", "澳门");
        areaMap.put("91", "国外");
        return areaMap;
    }

    /**
     * 计算两个时间的月数差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Integer countMonth(Date startTime, Date endTime) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startTime);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endTime);
        int i = 0;
        i += endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
        int y = 0;
        if (endCal.get(Calendar.DAY_OF_MONTH) >= startCal.get(Calendar.DAY_OF_MONTH)) {
            y = 1;
        }
        return 12 * i + endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH) + y;
    }

    /**
     * 获取当前时间贷款期限月数后的日期
     * 当日期为31，期限月数为小月时，按自然月计算
     */
    public static Date getAfterNumMonthTime(Date time, Integer num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        Integer monthNum = cal.get(Calendar.MONTH) + 1 + num;
        Integer yearNum = cal.get(Calendar.YEAR);
        //计算月数和年数
        if (monthNum > 12) {
            monthNum -= 12;
            yearNum += 1;
        }
        //处理日期
        if (cal.get(Calendar.DAY_OF_MONTH) > 28) {
            if (cal.get(Calendar.DAY_OF_MONTH) == 31) {
                if (!BIG_MONTHS.contains(monthNum)) {
                    cal.set(Calendar.DAY_OF_MONTH, 30);
                }
            }
            if (monthNum == 2) {
                if (DateUtils.isLeapYear(yearNum)) {
                    cal.set(Calendar.DAY_OF_MONTH, 29);
                } else {
                    cal.set(Calendar.DAY_OF_MONTH, 28);
                }
            }
        }
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + num);
        return cal.getTime();
    }

    public static Integer getDayOfMonth(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据日期获取当前月最大天数
     *
     * @param date
     * @return
     */
    public static int getMaxDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期当前月的最后一天, 若null则默认当前时间
     */
    public static Date getMonthLastDay(Date date) {
        if (null == date) {
            date = new Date();
        }
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        try {
            return DateUtils.string2DateTimeBy23(DateUtils.dtSimpleFormat(cale.getTime()));
        } catch (Exception e) {
            throw BizErrorException.newBizError("日期格式错误");
        }

    }


    public static void main(String[] args) {
        List<String> codes = Lists.newArrayList();
        for (int i = 0; i < 10000; i++) {
            String code = AppUtils.newCodeNo("MI");
            if (codes.contains(code)) {
                System.out.println("重复");
            }
            codes.add(code);
            System.out.println(code);
        }
        System.out.println(codes);
    }

    public static boolean isPhone(String str) {
        return !isBlank(str) && str.matches("^((0\\d{2,3}-\\d{7,8})|((13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[57])[0-9]{8}))$");
    }

    /**
     * 用户名只能为字母和数字
     *
     * @param str
     * @return
     */
    public static boolean isEnglishAndNumber(String str) {
        return !isBlank(str) && str.matches("^[0-9a-zA_Z]+$");
    }
}
