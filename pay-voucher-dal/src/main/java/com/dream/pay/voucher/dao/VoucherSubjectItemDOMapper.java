package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherSubjectItemDO;
import org.apache.ibatis.annotations.Param;

public interface VoucherSubjectItemDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherSubjectItemDO record);

    int insertSelective(VoucherSubjectItemDO record);

    VoucherSubjectItemDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherSubjectItemDO record);

    int updateByPrimaryKey(VoucherSubjectItemDO record);

    VoucherSubjectItemDO selectByCode(@Param("subjectCode") String subjectCode);
}