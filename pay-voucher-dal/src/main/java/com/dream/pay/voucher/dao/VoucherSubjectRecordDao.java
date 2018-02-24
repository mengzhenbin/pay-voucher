package com.dream.pay.voucher.dao;


import com.dream.pay.voucher.model.VoucherSubjectRecordEntity;

public interface VoucherSubjectRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherSubjectRecordEntity record);

    int insertSelective(VoucherSubjectRecordEntity record);

    VoucherSubjectRecordEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherSubjectRecordEntity record);

    int updateByPrimaryKey(VoucherSubjectRecordEntity record);
}