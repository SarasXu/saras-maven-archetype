package com.saras.archetype.api.utils;


import com.saras.archetype.utils.DateUtils;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;


/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
public class IdUtils {
    private static AtomicLong seqNo = new AtomicLong(0);
    private static int SEQ_NO_MAX_VALUE = 100000;
    private static String ipAddress = getLastThreeCharOfIpV4();
    private static int SEQ_NO_DEFAULT_LENGTH = 5;

    /**
     * 生成25位Id
     *
     * @return
     */
    public static String newCode() {
        StringBuilder number = new StringBuilder();
        //17位时间
        number.append(DateUtils.allDate(new Date()));
        //3位ip地址
        number.append(ipAddress);
        //5位序列号
        String seqNoForCurrentTime = genSeqNo() + "";
        if (seqNoForCurrentTime.length() < SEQ_NO_DEFAULT_LENGTH) {
            for (int i = 0; i < SEQ_NO_DEFAULT_LENGTH - seqNoForCurrentTime.length(); i++) {
                number.append("0");
            }
        }
        number.append(seqNoForCurrentTime);

        return number.toString();
    }

    /**
     * 生成23位Id
     *
     * @return
     */
    public static String newCode(String code) {
        StringBuilder number = new StringBuilder();
        //17位时间
        number.append(DateUtils.allDate(new Date()));
        //3位ip地址
        number.append(ipAddress);
        Integer seqNoLength = 3;
        //5位序列号
        String seqNoForCurrentTime = genSeqNo(1000) + "";
        if (seqNoForCurrentTime.length() < seqNoLength) {
            for (int i = 0; i < seqNoLength - seqNoForCurrentTime.length(); i++) {
                number.append("0");
            }
        }
        number.append(seqNoForCurrentTime);

        return code + number.toString();
    }

    //获取5位序列号，范围0-99999
    private static long genSeqNo() {
        while (true) {
            long lastSeqNo = seqNo.get();
            if (seqNo.compareAndSet(lastSeqNo, (lastSeqNo + 1) % SEQ_NO_MAX_VALUE)) {
                return (lastSeqNo + 1) % SEQ_NO_MAX_VALUE;
            }
        }
    }

    //获取5位序列号，范围0-(maxValue-1)
    private static long genSeqNo(Integer maxValue) {
        while (true) {
            long lastSeqNo = seqNo.get();
            if (seqNo.compareAndSet(lastSeqNo, (lastSeqNo + 1) % maxValue)) {
                return (lastSeqNo + 1) % maxValue;
            }
        }
    }

    //获取服务器的地址,返回ipV4最后一个网段的地址，3位，不足3位的前补0
    private static String getLastThreeCharOfIpV4() {
        String ip = IPUtils.getFirstNoLoopbackIPV4Address();
        int index = ip.lastIndexOf('.');
        String ipNums = ip.substring(index + 1);
        int ipNumsLength = ipNums.length();
        if (ipNumsLength < 3) {
            for (int i = 0; i < 3 - ipNumsLength; i++) {
                ipNums = "0" + ipNums;
            }
        }
        return ipNums;
    }
}
