package com.saras.archetype.api.exception;


/**
 * description:
 * saras_xu@163.com 2017-11-29 14:26 创建
 */
public class ApiServiceException extends RuntimeException {
    private static final long serialVersionUID = 4767966578232640700L;
    /**
     * 错误编码
     */
    private String code;

    /**
     * 抛出api服务异常
     *
     * @param message 信息
     * @param code    错误码
     */
    public ApiServiceException(String message, String code) {
        super(message);
        this.code = code;
    }

//    /**
//     * 抛出api服务异常
//     *
//     * @param errorCode 错误码枚举
//     */
//    public ApiServiceException(ErrorCodeEnum errorCode) {
//        super(errorCode.getMessage());
//        this.code = errorCode.getCode();
//    }
//
//    /**
//     * 抛出api服务异常，错误码默认为未知异常code
//     *
//     * @param message 信息
//     */
//    public ApiServiceException(String message) {
//        super(message);
//        this.code = ErrorCodeEnum.UN_KNOWN_ERROR.getCode();
//    }
//
//    /**
//     * 抛出api服务异常
//     *
//     * @param apiCode api错误码枚举
//     */
//    public ApiServiceException(ApiCodeEnum apiCode) {
//        super(apiCode.getMessage());
//        this.code = apiCode.getCode();
//    }

    public String getCode() {
        return code;
    }
}
