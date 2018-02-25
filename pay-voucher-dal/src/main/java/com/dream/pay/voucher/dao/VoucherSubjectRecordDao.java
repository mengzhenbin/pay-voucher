package com.dream.pay.voucher.dao;


import com.dream.pay.voucher.model.VoucherSubjectRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoucherSubjectRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherSubjectRecordEntity record);

    int insertSelective(VoucherSubjectRecordEntity record);

    VoucherSubjectRecordEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherSubjectRecordEntity record);

    int updateByPrimaryKey(VoucherSubjectRecordEntity record);

    long selectMaxId(@Param("voucherDate") String voucherDay);

    List<VoucherSubjectRecordEntity> selectByVoucherDay(@Param("voucherDate") String voucherDay,
                                                        @Param("startRow") long startRow,
                                                        @Param("endRow") long endRow);

    long countSubjectRecordByVoucherDayAndAcctNo(@Param("voucherDate") String voucherDay,
                                                 @Param("accountNo") String accountNo);

    List<VoucherSubjectRecordEntity> selectByVoucherDayAndAccountNo(@Param("voucherDate") String voucherDay,
                                                                 @Param("accountNo") String accountNo,
                                                                 @Param("startRow") long startRow,
                                                                 @Param("pageSize") long pageSize);
}