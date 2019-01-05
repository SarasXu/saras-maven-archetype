package com.saras.archetype.core.strategy;

/**
 * description:渠道服务路由常量类，重要-->路由时的code要枚举code一样
 * saras_xu@163.com 2017-05-03 10:11 创建
 */
public class ChannelServiceRoutConstants {

    /**
     * 创建会议路由常量
     */
    public static final class CREATE_MEETING {
        /**
         * 公共码
         */
        public final static String COMMON_CODE = "CREATE_MEETING_";
        /**
         * WEBEX
         */
        public final static String WEBEX = COMMON_CODE + "WEBEX";
        /**
         * CMR
         */
        public final static String CMR = COMMON_CODE + "CMR";
        /**
         * OFFLINE
         */
        public final static String OFFLINE = COMMON_CODE + "OFFLINE";
    }

    /**
     * 修改会议路由常量
     */
    public static final class MODIFY_MEETING {
        /**
         * 公共码
         */
        public final static String COMMON_CODE = "MODIFY_MEETING_";
        /**
         * WEBEX
         */
        public final static String WEBEX = COMMON_CODE + "WEBEX";
        /**
         * CMR
         */
        public final static String CMR = COMMON_CODE + "CMR";
        /**
         * OFFLINE
         */
        public final static String OFFLINE = COMMON_CODE + "OFFLINE";
    }

    /**
     * 取消会议路由常量
     */
    public static final class CANCEL_MEETING {
        /**
         * 公共码
         */
        public final static String COMMON_CODE = "CANCEL_MEETING_";
        /**
         * WEBEX
         */
        public final static String WEBEX = COMMON_CODE + "WEBEX";
        /**
         * CMR
         */
        public final static String CMR = COMMON_CODE + "CMR";
        /**
         * OFFLINE
         */
        public final static String OFFLINE = COMMON_CODE + "OFFLINE";
    }

    /**
     * 获取主持人入会链接（启会）路由常量
     */
    public static final class START_MEETING {
        /**
         * 公共码
         */
        public final static String COMMON_CODE = "START_MEETING_";
        /**
         * WEBEX
         */
        public final static String WEBEX = COMMON_CODE + "WEBEX";
        /**
         * CMR
         */
        public final static String CMR = COMMON_CODE + "CMR";
        /**
         * OFFLINE
         */
        public final static String OFFLINE = COMMON_CODE + "OFFLINE";
    }

    /**
     * 结束会议路由常量
     */
    public static final class END_MEETING {
        /**
         * 公共码
         */
        public final static String COMMON_CODE = "END_MEETING_";
        /**
         * WEBEX
         */
        public final static String WEBEX = COMMON_CODE + "WEBEX";
        /**
         * CMR
         */
        public final static String CMR = COMMON_CODE + "CMR";
        /**
         * OFFLINE
         */
        public final static String OFFLINE = COMMON_CODE + "OFFLINE";
    }
}
