package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherSubjectRecordDO {
    private Long id;

    private String voucherCode;

    private String subjectCode;

    private String outerWaterNo;

    private String payAcquireNo;

    private String bizOrderNo;

    private String voucherDate;

    private String accountNo;

    private String recordDir;

    private Long amount;

    private String currency;

    private String remark;

    private Date createTime;

    private Date updateTime;

}