package com.saras.archetype.utils;

import com.saras.archetype.base.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * description:
 * saras_xu@163.com 2017-03-06 16:18 创建
 *
 * @author saras
 */
public class ViewUtil implements Serializable {
    private final static Logger logger = LoggerFactory.getLogger(ViewUtil.class);

    private static final long serialVersionUID = 6681841768477488492L;

    private static final String DEFAULT_SUCCESS_CODE = "1";

    private static final String DEFAULT_FAIL_CODE = "0";

    private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";

    private static final String DEFAULT_FAIL_MESSAGE = "操作失败";

    private static final String DEFAULT_EXCEPTION_MESSAGE = "服务器繁忙，请稍后重试";

    private static final Integer DEFAULT_SUCCESS_COUNT = 0;

    /**
     * 状态
     */
    private boolean success = false;

    /**
     * 结果码
     */
    private String code;

    /**
     * 应答信息
     */
    private String message;

    /**
     * 返回详情数据
     */
    private Object data;

    /**
     * 分页总条数
     */
    private Integer count;


    public ViewUtil(boolean success, String code, String message, Object data, Integer count) {
        this.code = code;
        this.success = success;
        if (hasException(message) && !success) {
            this.message = DEFAULT_EXCEPTION_MESSAGE;
        } else {
            this.message = message;
        }
        this.data = data;
        this.count = count;
        logger.info("Ajax请求应答>>>状态：{}，code：{}，message：{}，data：{}，count：{}", success, code, message, data, count);
    }

    public static boolean hasException(String message) {
        return StringUtils.hasText(message) && message.contains("Exception");
    }

    /**
     * 返回成功信息到客户端
     *
     * @param code    返回码
     * @param message 描述信息
     * @param data    还回的数据对象
     * @return
     */
    public static ViewUtil success(String code, String message, Object data, Integer count) {
        return new ViewUtil(true, code, message, data, count);
    }

    /**
     * 返回成功信息到客户端
     *
     * @param message 描述信息
     * @param data    还回的数据对象
     * @return
     */
    public static ViewUtil success(String message, Object data) {
        return success(DEFAULT_SUCCESS_CODE, message, data, DEFAULT_SUCCESS_COUNT);
    }

    /**
     * 返回成功信息到客户端
     *
     * @param message
     * @param data
     * @param count
     * @return
     */
    public static ViewUtil success(String message, Object data, Integer count) {
        return success(DEFAULT_SUCCESS_CODE, message, data, count);
    }


    /**
     * 返回成功信息到客户端
     *
     * @param data
     * @param count
     * @return
     */
    public static ViewUtil success(Object data, Integer count) {
        return success(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, data, count);
    }

    /**
     * 返回成功信息到客户端 描述信息固定为<code>DEFAULT_SUCCESS_MESSAGE</code>
     *
     * @param data 返回的数据对象
     * @return
     */
    public static ViewUtil success(Object data) {
        return success(DEFAULT_SUCCESS_MESSAGE, data);
    }

    /**
     * 返回成功信息到客户端 描述信息固定为<code>DEFAULT_SUCCESS_MESSAGE</code>
     *
     * @param message 返回的描述信息
     * @return
     */
    public static ViewUtil success(String message) {
        return success(message, null, DEFAULT_SUCCESS_COUNT);
    }

    /**
     * 返回失败信息到客户端 描述信息固定为<code>DEFAULT_fail_MESSAGE</code>
     *
     * @param data 返回的数据对象
     * @return
     */
    public static ViewUtil fail(Object data) {
        return fail(DEFAULT_FAIL_MESSAGE, data);
    }

    /**
     * 返回成功信息到客户端
     *
     * @param message 描述信息
     * @param data    还回的数据对象
     * @return
     */
    public static ViewUtil fail(String message, Object data) {
        return fail(DEFAULT_FAIL_CODE, message, data);
    }

    /**
     * 返回成功信息到客户端
     *
     * @param message 描述信息
     * @return
     */
    public static ViewUtil fail(String message) {
        return fail(DEFAULT_FAIL_CODE, message, null);
    }

    /**
     * 返回成功信息到客户端
     *
     * @param code    返回码
     * @param message 描述信息
     * @param data    还回的数据对象
     * @return
     */
    public static ViewUtil fail(String code, String message, Object data) {
        return new ViewUtil(false, code, message, data, DEFAULT_SUCCESS_COUNT);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public ViewUtil setData(Object data) {
        this.data = data;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
