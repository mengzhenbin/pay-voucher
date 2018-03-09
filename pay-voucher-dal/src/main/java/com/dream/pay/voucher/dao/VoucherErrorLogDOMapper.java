package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherErrorLogDO;

public interface VoucherErrorLogDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherErrorLogDO record);

    int insertSelective(VoucherErrorLogDO record);

    VoucherErrorLogDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherErrorLogDO record);

    int updateByPrimaryKey(VoucherErrorLogDO record);
}