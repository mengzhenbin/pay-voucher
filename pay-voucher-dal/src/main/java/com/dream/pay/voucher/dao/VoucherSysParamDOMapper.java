package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherSysParamDO;

public interface VoucherSysParamDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherSysParamDO record);

    int insertSelective(VoucherSysParamDO record);

    VoucherSysParamDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherSysParamDO record);

    int updateByPrimaryKey(VoucherSysParamDO record);

    VoucherSysParamDO getBySysCode(String sysCode);

    Integer updateSysValue(VoucherSysParamDO voucherSysParamDO);
}