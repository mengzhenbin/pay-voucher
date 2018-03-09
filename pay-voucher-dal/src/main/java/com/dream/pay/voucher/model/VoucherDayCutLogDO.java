package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDayCutLogDO {
    private Long id;

    private String voucherDate;

    private Integer taskId;

    private Integer taskExecuteState;

    private String taskExecuteOpertor;

    private String taskExecuteDesc;

    private Date createTime;

    private Date updateTime;
}