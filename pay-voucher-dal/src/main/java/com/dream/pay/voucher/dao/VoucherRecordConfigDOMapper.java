package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherRecordConfigDO;
import org.apache.ibatis.annotations.Param;

public interface VoucherRecordConfigDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherRecordConfigDO record);

    int insertSelective(VoucherRecordConfigDO record);

    VoucherRecordConfigDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherRecordConfigDO record);

    int updateByPrimaryKey(VoucherRecordConfigDO record);

    VoucherRecordConfigDO selectByVoucherCode(@Param("voucherCode") String voucherCode,
                                              @Param("channelCode") String channelCode);
}