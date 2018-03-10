package com.dream.pay.voucher.utils;

import com.dream.pay.voucher.model.VoucherSubjectSummaryDO;
import com.dream.pay.voucher.model.VoucherTotalSummaryDO;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by mengzhenbin
 * @since 2017/7/28.
 */

public class DebitCreditSumUtils {

    /**
     * 单个科目下单个账户的金额和交易笔数累加
     *
     * @param voucherTotalSummaryDO 科目汇总记录
     * @param voucherSubjectSummaryDO 账户汇总记录
     */
    public static void sumSubjectSummaryDOAmountAndTimes(VoucherTotalSummaryDO voucherTotalSummaryDO, VoucherSubjectSummaryDO voucherSubjectSummaryDO) {
        //借方科目账户
        voucherTotalSummaryDO.setDebitAmount(voucherTotalSummaryDO.getDebitAmount() + voucherSubjectSummaryDO.getDebitAmount());
        voucherTotalSummaryDO.setDebitCount(voucherTotalSummaryDO.getDebitCount() + voucherSubjectSummaryDO.getDebitCount());
        //贷方科目账户
        voucherTotalSummaryDO.setCreditAmount(voucherTotalSummaryDO.getCreditAmount() + voucherSubjectSummaryDO.getCreditAmount());
        voucherTotalSummaryDO.setCreditCount(voucherTotalSummaryDO.getCreditCount() + voucherSubjectSummaryDO.getCreditCount());

        voucherTotalSummaryDO.setEndBalance(voucherTotalSummaryDO.getEndBalance() + voucherSubjectSummaryDO.getEndBalance());
    }


    /**
     * 科目交易金额，交易笔数累加
     *
     * @param descSummaryParams         累加的科目记录
     * @param srcTotalSummaryParamsList 被累加的科目记录
     */
    public static void sumSubjectAmountAndTimes(VoucherTotalSummaryDO descSummaryParams, List<VoucherTotalSummaryDO> srcTotalSummaryParamsList) {

        if (!CollectionUtils.isEmpty(srcTotalSummaryParamsList)) {
            for (VoucherTotalSummaryDO srcVoucherTotalSummaryDO : srcTotalSummaryParamsList) {
                descSummaryParams.setCreditAmount(descSummaryParams.getCreditAmount() + srcVoucherTotalSummaryDO.getCreditAmount());
                descSummaryParams.setCreditCount(descSummaryParams.getCreditCount() + srcVoucherTotalSummaryDO.getCreditCount());
                descSummaryParams.setDebitAmount(descSummaryParams.getDebitAmount() + srcVoucherTotalSummaryDO.getDebitAmount());
                descSummaryParams.setDebitCount(descSummaryParams.getDebitCount() + srcVoucherTotalSummaryDO.getDebitCount());
                descSummaryParams.setEndBalance(descSummaryParams.getEndBalance() + srcVoucherTotalSummaryDO.getEndBalance());
            }
        }
    }


    /**
     * 汇总单个科目下所有账户的金额和交易笔数
     *
     * @param voucherSubjectSummaryDOList
     * @param voucherTotalSummaryDO
     */
    public static void sumSubjectSummaryDOAmountAndTimes(List<VoucherSubjectSummaryDO> voucherSubjectSummaryDOList, VoucherTotalSummaryDO voucherTotalSummaryDO) {
        for (VoucherSubjectSummaryDO voucherSubjectSummaryDO : voucherSubjectSummaryDOList) {
            sumSubjectSummaryDOAmountAndTimes(voucherTotalSummaryDO, voucherSubjectSummaryDO);
        }
    }
}
