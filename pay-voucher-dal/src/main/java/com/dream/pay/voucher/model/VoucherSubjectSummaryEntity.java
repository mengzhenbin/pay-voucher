package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherSubjectSummaryEntity {
    private Long id;

    private String accountNo;

    private Boolean innerFlag;

    private String subjectCode;

    private Long amount;

    private Long creditAmount;

    private Long debitAmount;

    private Long beginBalance;

    private Long serialNo;

    private Long endBalance;

    private String currency;

    private Long creditCount;

    private Long debitCount;

    private String voucherDate;

    private Date createTime;

    private Date updateTime;
}