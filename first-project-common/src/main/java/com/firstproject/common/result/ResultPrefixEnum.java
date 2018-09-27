package com.firstproject.common.result;

import lombok.Getter;

/**
 * Description：
 * 错误码前缀
 * @author zhouzhongyi1
 * DATE： 2018/9/27 11:33
 */
public enum ResultPrefixEnum {
    /**
     * 操作成功
     */
    SUCCESS("200", "操作成功。"),
    PARAM_ERROR("400-", "参数错误。"),
    URL_ERROR("404-", "错误的请求。"),
    SYSTEM_ERROR("500-", "系统异常,请稍后再试。"),
    RPC_ERROR("600-", "远程服务异常,请稍后再试。");

    ResultPrefixEnum(String code, String desc) {
        this.code = code ;
        this.desc = desc ;
    }
    @Getter
    private String code ;
    @Getter
    private String desc ;

}
