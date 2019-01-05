package com.saras.archetype.validator;


import com.saras.archetype.annotation.Email;
import com.saras.archetype.utils.AppUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * message:
 * saras_xu@163.com 2017-04-18 10:07 创建
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

    private boolean nullable;
    private boolean blankable;
    private String message;

    /**
     * @param constraintAnnotation
     * @see ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(Email constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
        blankable = constraintAnnotation.blankable();
        message = constraintAnnotation.message();
    }

    /**
     * @param value
     * @param context
     * @return
     * @see ConstraintValidator#isValid(Object,
     * ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            if (!nullable) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("邮箱不能为空").addConstraintViolation();
            }
            return nullable;
        }

        if (value.length() == 0) {
            if (!blankable) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("邮箱不能为空白").addConstraintViolation();
            }
            return blankable;
        }

        if (!AppUtils.isEmail(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(StringUtils.defaultString(message, "邮箱格式不正确")).addConstraintViolation();
            return false;
        }
        return true;
    }

}
