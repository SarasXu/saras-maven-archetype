package com.saras.archetype.api.verify;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
public class SignatureIgnoreProperties {
    /**
     * api网关签名忽略的字段
     */
    public static final List<String> API_GATEWAY_SIGN_IGNORE_LIST = new ArrayList<String>() {
        {
            add("sign");
        }
    };
}
