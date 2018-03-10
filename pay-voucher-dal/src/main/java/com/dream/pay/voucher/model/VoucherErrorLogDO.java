package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherErrorLogDO {
    private Long id;

    private String voucherDate;

    private String accountNo;

    private Integer errorType;

    private String errorCode;

    private String errorSrc;

    private String errorDesc;

    private Date createTime;

    private Date updateTime;

}