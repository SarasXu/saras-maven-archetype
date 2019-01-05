package com.saras.archetype.service;


import com.saras.archetype.base.Base;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * message:建议作为所有订单和表单的基类
 * saras_xu@163.com 2017-03-01 14:45 创建
 */
public abstract class BaseOrder extends Base {
    private static final long serialVersionUID = -2427019332806095916L;
    /**
     * 此流水号没有任何业务规则，只是为了跟踪业务形态设置
     */
    @NotBlank(message = "业务请求流水号不能为空")
    @Size(max = 64, message = "业务请求流水号不能超过64位")
    private String serialNo;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
