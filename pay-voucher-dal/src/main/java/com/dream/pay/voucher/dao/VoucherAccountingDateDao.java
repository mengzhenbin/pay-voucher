package com.dream.pay.voucher.dao;


import org.apache.ibatis.annotations.Param;

public interface VoucherAccountingDateDao {

    int updateAccountingDate(@Param("lastAccountingDate") String lastAccountingDate,
                             @Param("accountingDate") String accountingDate);

    String selectAccountingDate();
}