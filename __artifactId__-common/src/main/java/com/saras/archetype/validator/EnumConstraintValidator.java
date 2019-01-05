package com.saras.archetype.validator;

import com.google.common.base.Enums;
import com.saras.archetype.annotation.EnumConstraint;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * message:
 * saras_xu@163.com 2017-04-18 10:07 创建
 */
public class EnumConstraintValidator implements ConstraintValidator<EnumConstraint, String> {

    public static final Logger logger = LoggerFactory.getLogger(EnumConstraintValidator.class);

    private boolean nullable;
    private String message;
    private Class clazz;

    @Override
    public void initialize(EnumConstraint enumConstraint) {
        nullable = enumConstraint.nullable();
        message = enumConstraint.message();
        clazz = enumConstraint.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            if (!nullable) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("枚举值不能为空").addConstraintViolation();
                return false;
            }
            return true;
        }
        boolean checkFlag = Enums.getIfPresent(clazz, value).isPresent();
        if (!checkFlag) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    StringUtils.defaultIfBlank(message, "枚举值异常")).addConstraintViolation();
        }
        return checkFlag;
    }
}
