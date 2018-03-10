package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherTotalSummaryDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoucherTotalSummaryDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherTotalSummaryDO record);

    int insertSelective(VoucherTotalSummaryDO record);

    VoucherTotalSummaryDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherTotalSummaryDO record);

    int updateByPrimaryKey(VoucherTotalSummaryDO record);

    List<VoucherTotalSummaryDO> getByVoucherDate(@Param("voucherDate") String voucherDate);

    List<VoucherTotalSummaryDO> getByVoucherDateAndSubjectLevel(@Param("voucherDate") String voucherDate,
                                                                @Param("subjectLevel") Integer subjectLevel);

    VoucherTotalSummaryDO getByVoucherDateAndSubjectCode(@Param("voucherDate") String voucherDate,
                                                         @Param("subjectCode") String subjectCode);
}