package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherSubjectRecordDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoucherSubjectRecordDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherSubjectRecordDO record);

    int insertSelective(VoucherSubjectRecordDO record);

    VoucherSubjectRecordDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherSubjectRecordDO record);

    int updateByPrimaryKey(VoucherSubjectRecordDO record);

    Long selectMaxId(@Param("voucherDate") String voucherDay);

    List<VoucherSubjectRecordDO> selectByVoucherDay(@Param("voucherDate") String voucherDate,
                                                    @Param("startRow") long startRow,
                                                    @Param("endRow") long endRow);

    Long countByVoucherDayAndAccountNo(@Param("voucherDate") String voucherDate,
                                       @Param("accountNo") String accountNo);

    List<VoucherSubjectRecordDO> selectByVoucherDayAndAccountNo(@Param("voucherDate") String voucherDate,
                                                                @Param("accountNo") String accountNo,
                                                                @Param("startRow") long startRow,
                                                                @Param("pageSize") long pageSize);
}