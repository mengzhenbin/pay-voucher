package com.dream.pay.voucher.dao;


import com.dream.pay.voucher.model.VoucherSubjectItemEntity;
import org.apache.ibatis.annotations.Param;

public interface VoucherSubjectItemDao {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherSubjectItemEntity record);

    int insertSelective(VoucherSubjectItemEntity record);

    VoucherSubjectItemEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherSubjectItemEntity record);

    int updateByPrimaryKey(VoucherSubjectItemEntity record);

    VoucherSubjectItemEntity selectByCode(@Param("subjectCode") String subjectCode);
}