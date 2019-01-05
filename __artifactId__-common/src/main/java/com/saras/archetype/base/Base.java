package com.saras.archetype.base;



import com.saras.archetype.core.exception.BizErrorException;
import com.saras.archetype.enums.AppValidatorFactory;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Set;

/**
 * message: 所有实体的基类，自带toString方法，子类不用再去实现
 * saras_xu@163.com 2017-04-12 17:05 创建
 */
public abstract class Base implements Serializable {

    private static final long serialVersionUID = 2159882864632157636L;

    /**
     * 参数效验方法
     * 可重写，自定义效验规则
     */
    public void check() {
        checkWithGroup();
    }

    /**
     * 通过jsr303规范的注解来校验参数
     *
     * @param groups 校验groups
     */
    public void checkWithGroup(Class<?>... groups) {
        Set<ConstraintViolation<Base>> constraintViolations = AppValidatorFactory.INSTANCE.getValidator().validate(this, groups);
        validate(constraintViolations);
    }

    private <T> void validate(Set<ConstraintViolation<T>> constraintViolations) {
        //单个信息返回
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                throw new BizErrorException(constraintViolation.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
