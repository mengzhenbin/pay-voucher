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

    List<VoucherSubjectSummaryDO> selectByVoucherDateAndSubjectCode(@Param("voucherDate") String voucherDate,
                                                                    @Param("subjectCode") String subjectCode,
                                                                    @Param("startRow") long startRow,
                                                                    @Param("endRow") long endRow);

    Long selectMaxIdBySubjectCode(@Param("voucherDate") String voucherDate,
                                  @Param("subjectCode") String subjectCode);

    Long selectMinIdBySubjectCode(@Param("voucherDate") String voucherDate,
                                  @Param("subjectCode") String subjectCode);

    Integer updateBeginBalance(@Param("voucherDate") String voucherDate,
                               @Param("accountNo")String accountNo,
                               @Param("beginBalance")Long beginBalance);
}