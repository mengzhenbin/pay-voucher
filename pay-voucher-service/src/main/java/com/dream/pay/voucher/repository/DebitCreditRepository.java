package com.dream.pay.voucher.repository;

import com.dream.pay.voucher.model.VoucherDebitCreditSummaryDO;

/**
 * @author mengzhenbin
 * @since 2017/1/20
 */
public interface DebitCreditRepository {

    /**
     * 入借贷汇总表
     *
     * @param accountingDate 会计日期
     * @param debitAmount    借方金额
     * @param creditAmount   贷方金额
     */
    public Integer insertAmount(String accountingDate, long debitAmount, long creditAmount);

    /**
     * 获取汇总记录
     *
     * @param voucherDate 会计日期
     */
    public VoucherDebitCreditSummaryDO getByVoucherDate(String voucherDate);

}
