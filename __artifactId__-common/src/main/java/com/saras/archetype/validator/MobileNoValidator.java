package com.saras.archetype.validator;


import com.saras.archetype.annotation.MobileNo;
import com.saras.archetype.utils.AppUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * message:
 * saras_xu@163.com 2017-04-18 10:07 创建
 */
public class MobileNoValidator implements ConstraintValidator<MobileNo, String> {

    private boolean nullable;
    private boolean blankable;
    private String message;

    @Override
    public void initialize(MobileNo constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
        message = constraintAnnotation.message();
        blankable = constraintAnnotation.blankable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            if (!nullable) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("手机号不能为空").addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }
        if (value.length() == 0) {
            if (!blankable) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("手机号不能为空白").addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }
        if (!AppUtils.isMobile(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    StringUtils.defaultIfBlank(message, "手机号格式不对")).addConstraintViolation();
            return false;
        }
        return true;
    }
}
