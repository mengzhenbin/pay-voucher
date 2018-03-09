package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherTotalSummaryDO;

public interface VoucherTotalSummaryDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherTotalSummaryDO record);

    int insertSelective(VoucherTotalSummaryDO record);

    VoucherTotalSummaryDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherTotalSummaryDO record);

    int updateByPrimaryKey(VoucherTotalSummaryDO record);
}