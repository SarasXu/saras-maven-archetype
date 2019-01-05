package com.saras.archetype.annotation;



import com.saras.archetype.validator.EnglishAndNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Auther: wangzhihuan
 * Create in 2018\4\8 0008
 * Description:
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EnglishAndNumberValidator.class})
public @interface EnglishAndNumber {

    /**
     * 提示消息
     */
    String message() default "账号只能为英文字母和数字";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 是否可以为null,默认不可为null
     */
    boolean nullable() default false;

    /**
     * 是否可以为空字符串，默认不可为空
     *
     * @return
     */
    boolean blankable() default false;
}
