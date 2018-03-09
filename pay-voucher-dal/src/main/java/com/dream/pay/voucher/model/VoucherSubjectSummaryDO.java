package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherSubjectSummaryDO {
    private Long id;

    private Long serialNo;

    private String accountNo;

    private Boolean innerFlag;

    private String subjectCode;

    private Long beginBalance;

    private Long amount;

    private String currency;

    private Long creditAmount;

    private Long creditCount;

    private Long debitAmount;

    private Long debitCount;

    private Long endBalance;

    private String voucherDate;

    private Date createTime;

    private Date updateTime;

}