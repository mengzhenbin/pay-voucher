package com.dream.pay.voucher.dao;


import org.apache.ibatis.annotations.Param;

public interface VoucherDayDao {

    int updateVoucherDay(@Param("lastVoucherDay") String lastAccountingDate,
                         @Param("currentVoucherDay") String accountingDate);

    String selectVoucherDay();
}