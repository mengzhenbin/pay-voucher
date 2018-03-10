package com.dream.pay.voucher.access.result;


/**
 * 会计分录相关结果码
 * 负责定义会计分录模块相关的业务结果码信息
 * Type: 10
 *
 * @author mengzhenbin
 * @since 2016/01/11.
 */
public enum ResultCode implements VoucherResultCode {

    RECORD_ILLEGAL_PARAMS("1001", "会计分录入参异常"),
    CACHE_RECORD_EXECUTE_EXCEPTION("1002", "缓存消息处理异常"),
    RECORD_ILLEGAL_CONFIG("1003", "记账规则不存在或者不可用"),
    RECORD_SUBJECT_UNABLE("1004", "会计科目不可用"),
    DUPLICATE_KEY_FOR_RECORD("1005", "记账消息幂等"),
    REQUEST_ILLEGAL_PARAMS("1006", "请求参数异常"),


    //基础配置相关异常
    SUBJECT_NAME_ILLEGAL("2001", "会计科目名称非法"),
    SUBJECT_NOT_EIXIT("2002", "会计科目不存在"),
    SUBJECT_IS_ILLEGAL("2013", "会计科目非法"),


    SUBJECT_CONFIG_IS_ILLEGAL("3001", "会计科目非法"),

    CUT_ACCT_CHECK_FAIL("4001", "发生额不等于期末余额减期初余额"),

    DB_UPDATE_ERROR("9001", "数据库更新操作异常");

    /**
     * 信息码
     */
    private String infoCode;
    /**
     * 描述信息
     */
    private String message;

    /**
     * 全参数构造方法
     *
     * @param infoCode 信息码
     * @param message  描述信息
     */
    ResultCode(String infoCode, String message) {
        this.infoCode = infoCode;
        this.message = message;
    }

    /**
     * 获取错误码
     */
    @Override
    public String getInfoCode() {
        return infoCode;
    }

    /**
     * 获取模块码
     */
    @Override
    public String getTypeCode() {
        return "10";
    }

    /**
     * 获取错误描述信息
     */
    @Override
    public String getMessage() {
        return message;
    }
}
