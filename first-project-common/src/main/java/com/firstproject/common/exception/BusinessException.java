package com.firstproject.common.exception;

import com.firstproject.common.result.ResultPrefixEnum;

/**
 * Description：
 * 业务逻辑异常
 * @author zhouzhongyi1
 * @date 2018/8/3 14:14
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
        super("业务逻辑异常");
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String code, String desc) {
        super(String.format("业务逻辑异常；错误码:[%s], 错误信息:[%s].", code, desc));
    }

    public static BusinessException rpcBusinessException() {
        return new BusinessException(ResultPrefixEnum.RPC_ERROR.getCode(), ResultPrefixEnum.RPC_ERROR.getDesc()) ;
    }
    public static BusinessException paramBusinessException() {
        return new BusinessException(ResultPrefixEnum.PARAM_ERROR.getCode(), ResultPrefixEnum.PARAM_ERROR.getDesc()) ;
    }
    public static BusinessException serviceBusinessException() {
        return new BusinessException(ResultPrefixEnum.SYSTEM_ERROR.getCode(), ResultPrefixEnum.SYSTEM_ERROR.getDesc()) ;
    }
}
