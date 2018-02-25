package com.dream.pay.voucher.dao;


import com.dream.pay.voucher.model.VoucherSubjectSummaryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoucherSubjectSummaryDao {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherSubjectSummaryEntity record);

    int insertSelective(VoucherSubjectSummaryEntity record);

    VoucherSubjectSummaryEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherSubjectSummaryEntity record);

    int updateByPrimaryKey(VoucherSubjectSummaryEntity record);

    long selectMaxId(@Param("voucherDay") String voucherDay);

    long selectMinId(@Param("voucherDay") String voucherDay);

    List<VoucherSubjectSummaryEntity> selectByVoucherDate(@Param("voucherDay") String voucherDay,
                                                          @Param("startRow")long startRow,
                                                          @Param("endRow")long endRow);
}