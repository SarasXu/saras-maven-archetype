package com.saras.archetype.core.event;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * description:
 * saras_xu@163.com 2017-11-24 14:02 创建
 */
@Component
public class SubscriberExceptionHandlerImpl implements SubscriberExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(SubscriberExceptionHandlerImpl.class);

    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        logger.error("处理消息出现异常：event=" + context.getEvent() + ",method=" + context.getSubscriberMethod(), exception);
    }
}
