package com.saras.archetype.base;

import com.saras.archetype.core.exception.BizErrorException;
import com.saras.archetype.enums.OperationStatus;
import com.saras.archetype.utils.DateUtils;
import com.saras.archetype.utils.ReplyJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.IllegalFormatException;

/**
 * description:统一异常处理
 * saras_xu@163.com 2017-04-30 11:10 创建
 */
public abstract class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(value = Exception.class)
    public Object doException(HttpServletRequest request, Exception ex) {
        logger.error("请求接口：{}，异常信息：{}，异常类型：{}，详情：", request.getRequestURI(), ex.getMessage(), ex.getClass(), ex);
        //不同的异常对应不同的实现，目前未区分异常类型,暂时只做统一提示
        if (ex.getClass().equals(NullPointerException.class)) {
            return ReplyJsonUtil.fail(OperationStatus.EXCEPTION.message());
        } else if (ex.getClass().equals(IllegalFormatException.class)) {
            return ReplyJsonUtil.fail(OperationStatus.EXCEPTION.message());
        } else if (ex.getClass().equals(BizErrorException.class)) {
            return ReplyJsonUtil.fail(ex.getMessage());
        } else {
            return ReplyJsonUtil.fail(OperationStatus.EXCEPTION.message());
        }
    }

    /**
     * 初始化开始时间
     *
     * @param dateStr
     * @return
     */
    protected Date initStartTime(String dateStr) {
        try {
            return DateUtils.string2DateTimeByAutoZero(dateStr);
        } catch (ParseException e) {
            return DateUtils.strToDtSimpleFormat(dateStr);
        }
    }

    /**
     * 初始化结束时间
     *
     * @param dateStr
     * @return
     */
    protected Date initEndTime(String dateStr) {
        try {
            return DateUtils.string2DateTimeBy23(dateStr);
        } catch (ParseException e) {
            return DateUtils.strToDtSimpleFormat(dateStr);
        }
    }
}
