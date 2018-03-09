package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherDebitCreditSummaryDO;

public interface VoucherDebitCreditSummaryDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherDebitCreditSummaryDO record);

    int insertSelective(VoucherDebitCreditSummaryDO record);

    VoucherDebitCreditSummaryDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherDebitCreditSummaryDO record);

    int updateByPrimaryKey(VoucherDebitCreditSummaryDO record);

    VoucherDebitCreditSummaryDO getByVoucherDate(String voucherDate);
}