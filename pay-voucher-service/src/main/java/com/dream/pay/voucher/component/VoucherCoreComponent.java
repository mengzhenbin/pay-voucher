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
     *
     * @return
     */
    public String getVoucherDate();

    void setNextVoucherDate();
}
