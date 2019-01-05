package com.saras.archetype.annotation;



import com.saras.archetype.validator.CertNoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * message:判断身份证格式是否正确，支持15位和18位
 * saras_xu@163.com 2017-04-18 10:07 创建
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {CertNoValidator.class})
public @interface CertNo {
    String message() default "身份证格式不对";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullable() default false;

    boolean blankable() default false;
}
