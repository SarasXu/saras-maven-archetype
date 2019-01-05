package com.saras.archetype.annotation;



import com.saras.archetype.validator.EnumConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * message：枚举check注解
 * saras_xu@163.com 2017-04-18 10:07 创建
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EnumConstraintValidator.class})
public @interface EnumConstraint {

    String message() default "枚举值异常";

    /**
     * 枚举class，不能为空
     */
    Class value();

    /**
     * 是否可以为null,默认可为null
     */
    boolean nullable() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
