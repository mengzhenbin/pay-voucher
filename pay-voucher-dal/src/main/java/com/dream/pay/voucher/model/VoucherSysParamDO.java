package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherSysParamDO {
    private Long id;

    private String sysCode;

    private String sysValue;

    private String paramDesc;

    private Date createTime;

    private Date updateTime;

}