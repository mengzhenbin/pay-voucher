package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherSubjectSummaryDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoucherSubjectSummaryDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherSubjectSummaryDO record);

    int insertSelective(VoucherSubjectSummaryDO record);

    VoucherSubjectSummaryDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherSubjectSummaryDO record);

    int updateByPrimaryKey(VoucherSubjectSummaryDO record);

    Long selectMaxId(@Param("voucherDate") String voucherDate);

    Long selectMinId(@Param("voucherDate") String voucherDate);

    List<VoucherSubjectSummaryDO> selectByVoucherDate(@Param("voucherDate") String voucherDate,
                                                      @Param("startRow") long startRow,
                                                      @Param("endRow") long endRow);
}