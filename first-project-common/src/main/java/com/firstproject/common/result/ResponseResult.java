package com.firstproject.common.result;

import lombok.Getter;

/**
 * Description:
 * Ajax返回值响应ResponseBody
 * @author zhouzhongyi
 * Date: 2018/7/29 9:06
 */
public class ResponseResult<T> {

    @Getter
    private String code ;
    @Getter
    private String desc ;
    @Getter
    private T value ;
    @Getter
    private boolean success ;

    public ResponseResult<T> setCode(ResultPrefixEnum prefix, int codeNum) {
        this.code = prefix.getCode() + codeNum ;
        return this ;
    }
    @Deprecated
    public ResponseResult<T> setCode(String codeStr) {
        this.code = codeStr ;
        return this ;
    }
    public ResponseResult<T> setDesc(String desc) {
        this.desc = desc ;
        return this ;
    }
    public ResponseResult<T> setDesc(ResultPrefixEnum prefix, String desc) {
        this.desc = prefix.getDesc() + desc ;
        return this ;
    }

    public ResponseResult<T> setValue(T value) {
        this.value = value ;
        return this ;
    }
    public ResponseResult<T> setSuccess(boolean success) {
        this.success = success ;
        return this ;
    }

    public ResponseResult() {
    }
    public ResponseResult(boolean success, String desc) {
        this.success = success ;
        this.desc = desc ;
    }
    public ResponseResult(boolean success, String desc, T value) {
        this(success, desc) ;
        this.value = value;
    }
    public ResponseResult(boolean success, ResultPrefixEnum prefix) {
        this.code = prefix.getCode() ;
        this.success = success ;
        this.desc = prefix.getDesc();
    }
    public ResponseResult(boolean success, ResultPrefixEnum prefix, T value) {
        this(success, prefix) ;
        this.value = value;
    }
    public ResponseResult(boolean success, ResultPrefixEnum prefix, int code, String desc) {
        this.code = prefix.getCode() + code ;
        this.success = success ;
        this.desc = prefix.getDesc() + desc ;
    }
    @Deprecated
    public ResponseResult(boolean success, String codeNum, String desc) {
        this.code = codeNum ;
        this.success = success ;
        this.desc = desc ;
    }

    public static <T> ResponseResult<T> success(T value) {
        return new ResponseResult<>(true, ResultPrefixEnum.SUCCESS, value) ;
    }
    public static <T> ResponseResult<T> failure() {
        return new ResponseResult<>(false, "操作失败。") ;
    }
    public static <T> ResponseResult<T> paramError(int codeNum, String errMsg) {
        return new ResponseResult<>(false, ResultPrefixEnum.PARAM_ERROR, codeNum, errMsg) ;
    }
    public static <T> ResponseResult<T> paramError() {
        return new ResponseResult<>(false, ResultPrefixEnum.PARAM_ERROR) ;
    }
    public static <T> ResponseResult<T> systemError() {
        return new ResponseResult<>(false, ResultPrefixEnum.SYSTEM_ERROR) ;
    }
    public static <T> ResponseResult<T> systemError(int codeNum, String errMsg) {
        return new ResponseResult<>(false, ResultPrefixEnum.SYSTEM_ERROR, codeNum, errMsg) ;
    }
    public static <T> ResponseResult<T> rpcError() {
        return new ResponseResult<>(false, ResultPrefixEnum.RPC_ERROR) ;
    }
    public static <T> ResponseResult<T> rpcError(int codeNum, String errMsg) {
        return new ResponseResult<>(false, ResultPrefixEnum.RPC_ERROR, codeNum, errMsg) ;
    }


}
