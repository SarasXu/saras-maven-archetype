package com.saras.archetype.core.template;

import com.saras.archetype.annotation.DoInTransaction;
import com.saras.archetype.core.exception.BizErrorException;
import com.saras.archetype.enums.Status;
import com.saras.archetype.service.BaseOrder;
import com.saras.archetype.service.BaseResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 修订记录: 业务基础骨架
 * <p>
 * 1.对异常进行统一处理，定义业务最基础模板
 * <p>
 * 2.各业务模块可根据业务情况在继承的基础上再定义模板方法
 * <p>
 * 3.业务异常抛出BizError
 * <p>
 * 4.设置MDC等，业务无需再关心
 * <p>
 * saras_xu@163.com 2016-10-10 10:16 创建
 * <p>
 * 2017-02-15 修改记录：①加入自定义事务开关注解：需要开启事务的子类加上注解@DoInTransaction
 */
public abstract class AbstractBizService<O extends BaseOrder, R extends BaseResult> {
    private final Logger logger = LoggerFactory.getLogger(AbstractBizService.class);
    @Autowired
    protected TransactionTemplate transactionTemplate;

    public final R execute(String bieMemo, O order) {
        //设置mdc
        setMDC(order);
        logger.info("收到业务[{}]处理请求，请求参数：{}", bieMemo, order);
        //1.初始化result
        R result = initResult();
        try {
            //2.设置默认应答
            setDefaultResult(result);
            //3.订单基础信息效验
            orderCheck(order, result);
            //4.执行业务逻辑
            doAppBiz(order, result);

        } catch (IllegalArgumentException ie) {
            //参数校验错误(未知)
            logger.error("参数类型异常[订单：{}，msgError：{}]", order, ie.getMessage());
            result.setStatus(Status.FAIL);
            result.setMessage("参数类型异常，请核实");
        } catch (BizErrorException bizError) {
            logger.warn("系统业务错误[订单：{}，msgError：{}，errorCode：{}]", order, bizError.getMessage(), bizError.getCode());
            result.setStatus(Status.FAIL);
            result.setMessage(bizError.getMessage());
            result.setCode(bizError.getCode());
        } catch (Exception e) {
            //系统未知错误，抛出详细异常信息
            logger.error("系统未知错误[订单：{}，msgError：{}]", order, e.getMessage(), e);
            result.setStatus(Status.FAIL);
            result.setMessage("系统繁忙");
        } finally {

            if (result.isSuccess()) {
                logger.info("[{}]处理成功，结果：{}", bieMemo, result);
            } else if (result.isProcessing()) {
                logger.info("[{}]处理中，结果：{}", bieMemo, result);
            } else {
                logger.info("[{}]处理失败，原因：[{}]，结果：{}", bieMemo, result.getMessage(), result);
            }

            //打印摘要日志
            MDC.clear();
        }

        logger.info("业务[{}]处理结束，返回参数：result={}", bieMemo, result);

        return result;
    }

    /**
     * 实例化result对象
     *
     * @return
     */
    protected abstract R initResult();

    /**
     * 订单check
     *
     * @param order
     * @param result
     */
    protected abstract void orderCheck(O order, R result);

    /**
     * 设置MDC
     *
     * @param order
     */
    private void setMDC(O order) {
        if (order != null && StringUtils.isNotBlank(order.getSerialNo())) {
            if (StringUtils.isNotBlank(MDC.get("serialNo"))) {
                MDC.remove("serialNo");
            }
            MDC.put("serialNo", "-[serialNo:" + order.getSerialNo() + "]");
        }
    }

    /**
     * 设置默认应答
     */
    private void setDefaultResult(R result) {
        result.setStatus(Status.SUCCESS);
        result.setMessage("执行成功");
    }

    /**
     * 事务注解，判定是否开启编程式事务
     *
     * @param order
     */
    private void doAppBiz(O order, R result) {
        DoInTransaction doInTransaction = getAnnotation();
        if (null != doInTransaction) {
            logger.info("业务预处理完成，开始执行业务[事务开启]");
            appBizInTransaction(order, result);
        } else {
            logger.info("业务预处理完成，开始执行业务[事务未开启]");
            appBiz(order, result);
        }
    }

    /**
     * 获取事务注解
     *
     * @return
     */
    private DoInTransaction getAnnotation() {
        Class bizClass = this.getClass();
        return bizClass.isAnnotationPresent(DoInTransaction.class) ?
                (DoInTransaction) bizClass.getAnnotation(DoInTransaction.class) : null;

    }

    /**
     * 执行业务逻辑，加入事务保证
     *
     * @param order
     * @param result
     */
    protected void appBizInTransaction(O order, R result) {
        AppBiz biz = new AppBiz(order, result);
        transactionTemplate.execute(biz);
    }

    /**
     * 业务执行方法
     *
     * @param order
     * @return
     */
    protected abstract void appBiz(O order, R result);

    private class AppBiz implements TransactionCallback<Void> {

        private O order;
        private R result;

        public AppBiz(O order, R result) {
            this.order = order;
            this.result = result;
        }

        @Override
        public Void doInTransaction(TransactionStatus status) {

            try {
                //业务执行
                appBiz(order, result);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
            return null;
        }
    }
}
