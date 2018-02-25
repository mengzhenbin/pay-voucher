package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDebitCreditSummaryEntity {
    private Long id;

    private String voucherDate;

    private Long debitAmount;

    private Long creditAmount;

    private Date createTime;

    private Date updateTime;


}