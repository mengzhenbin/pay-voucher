package com.dream.pay.voucher.repository;


import com.dream.pay.voucher.model.VoucherTotalSummaryDO;

import java.util.List;

/**
 * @author mengzhenbin
 * @since 2017/1/20
 */
public interface TotalSummaryRepository {
    /**
     * @param voucherDate 会计日期
     * @return
     */
    public List<VoucherTotalSummaryDO> selectByVoucherDate(String voucherDate);


    /**
     * 查询指定级别日期的科目汇总记录
     *
     * @param voucherDate  会计日期
     * @param subjectLevel 科目级别
     * @return
     */
    public List<VoucherTotalSummaryDO> selectByVoucherDateDateAndSubjectLevel(String voucherDate, Integer subjectLevel);


    /**
     * 查询指定级别日期的科目汇总记录
     *
     * @param voucherDate 会计日期
     * @param subjectCode 科目编码
     * @return
     */
    VoucherTotalSummaryDO selectByVoucherDateDateAndSubjectCode(String voucherDate, String subjectCode);

    /**
     * 插入汇总记录
     */
    public long insert(VoucherTotalSummaryDO voucherTotalSummaryDO);

    /**
     * 更新汇总金额
     *
     * @param voucherTotalSummaryDO 更新条件信息
     * @return
     */
    public long updateBalance(VoucherTotalSummaryDO voucherTotalSummaryDO);

}
