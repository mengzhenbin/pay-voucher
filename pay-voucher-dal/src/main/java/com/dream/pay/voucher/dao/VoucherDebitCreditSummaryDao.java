package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherDebitCreditSummaryEntity;

public interface VoucherDebitCreditSummaryDao {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherDebitCreditSummaryEntity record);

    int insertSelective(VoucherDebitCreditSummaryEntity record);

    VoucherDebitCreditSummaryEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherDebitCreditSummaryEntity record);

    int updateByPrimaryKey(VoucherDebitCreditSummaryEntity record);
}