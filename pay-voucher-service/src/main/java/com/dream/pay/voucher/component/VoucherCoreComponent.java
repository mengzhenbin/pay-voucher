package com.dream.pay.voucher.component;


/**
 * 会计系统核心工具
 * 1:获取会计日期
 *
 * @author mengzhenbin
 * @since 2017/1/31
 */
public interface VoucherCoreComponent {

    /**
     * 获取会计日期
     */
    String getVoucherDate();

    /**
     * 设置下一会计日期
     */
    void setNextVoucherDate();

    /**
     * 登记错误日志
     */
    void regErrorLog(String accountNo, int errType, String errCode, String errSrc, String errDesc);

}
