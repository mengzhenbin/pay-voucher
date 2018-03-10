package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherTotalSummaryDO {
    private Long id;

    private String subjectCode;

    private String subjectName;

    private Integer subjectLevel;

    private String parentSubjectCode;

    private Integer zeroFlag;

    private String voucherDate;

    private String currency;

    private String balanceDir;

    private Long beginBalance;

    private Long debitAmount;

    private Long debitCount;

    private Long creditAmount;

    private Long creditCount;

    private Long endBalance;

    private Date createTime;

    private Date updateTime;

}