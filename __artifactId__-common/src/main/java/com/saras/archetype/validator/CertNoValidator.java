
package com.saras.archetype.validator;

import com.saras.archetype.annotation.CertNo;
import com.saras.archetype.utils.AppUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * message:
 * saras_xu@163.com 2017-04-18 10:07 创建
 */
public class CertNoValidator implements ConstraintValidator<CertNo, String> {
    private boolean nullable;
    private boolean blankable;
    private String message;

    @Override
    public void initialize(CertNo constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
        blankable = constraintAnnotation.blankable();
        message = constraintAnnotation.message();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            if (!nullable) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("身份证号不能为空")
                        .addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }

        if (value.length() == 0) {
            if (!blankable) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("身份证号码不能为空白")
                        .addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }

        if (!AppUtils.isCertNo(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(StringUtils.defaultString(message, "身份证格式不对")).addConstraintViolation();
            return false;
        }
        return true;
    }
}
