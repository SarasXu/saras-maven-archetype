
package com.saras.archetype.annotation;



import com.saras.archetype.validator.MobileNoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * message：手机号check注解
 * saras_xu@163.com 2017-04-18 10:07 创建
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MobileNoValidator.class})
public @interface MobileNo {

    String message() default "手机号格式不正确";

    /**
     * 是否可以为null,默认不可为null
     */
    boolean nullable() default false;

    /**
     * 是否可以为空字符串,默认不可为空
     */
    boolean blankable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
