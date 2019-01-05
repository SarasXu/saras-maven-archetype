package com.saras.archetype.validator;

import com.saras.archetype.annotation.EnglishAndNumber;
import com.saras.archetype.utils.AppUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Auther: wangzhihuan
 * Create in 2018\4\8 0008
 * Description:
 */
public class EnglishAndNumberValidator implements ConstraintValidator<EnglishAndNumber, String> {

    private boolean nullable;
    private boolean blankable;
    private String message;

    @Override
    public void initialize(EnglishAndNumber constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
        message = constraintAnnotation.message();
        blankable = constraintAnnotation.blankable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            if (!nullable) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("用户名不能为空").addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }
        if (value.length() == 0) {
            if (!blankable) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("用户名不能为空白").addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }
        if (!AppUtils.isEnglishAndNumber(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    StringUtils.defaultIfBlank(message, "用户名只能为字母或数字")).addConstraintViolation();
            return false;
        }
        return true;
    }
}
