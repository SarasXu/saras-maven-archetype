package com.saras.archetype.api.message;

import com.saras.archetype.service.BaseOrder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
public class ApiBaseMessage extends BaseOrder {
    private static final long serialVersionUID = -8216938170342093951L;

    /**
     * 服务名
     */
    @NotBlank
    @Size(max = 40)
    private String service;
    /**
     * 版本号
     */
    @Size(max = 8)
    private String version;
    /**
     * 签名
     */
    @NotBlank
    @Size(max = 64)
    private String sign;
    /**
     * 商户Id
     */
    @NotBlank
    @Size(max = 25)
    private String companyNo;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }
}
