package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherRecordConfigDO {
    private Long id;

    private String voucherCode;

    private String channelCode;

    private Integer configType;

    private String debitSubject;

    private String debitAccount;

    private String creditSubject;

    private String creditAccount;

    private Integer ableFlag;

    private String remark;

    private Date createTime;

    private Date updateTime;
}