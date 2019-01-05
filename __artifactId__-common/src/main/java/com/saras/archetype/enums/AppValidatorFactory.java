package com.saras.archetype.enums;


import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * description:保证单例
 * saras_xu@163.com 2017-03-01 16:04 创建
 */
public enum AppValidatorFactory {
    INSTANCE {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        @Override
        public Validator getValidator() {
            return factory.getValidator();
        }
    };

    public abstract Validator getValidator();
}
