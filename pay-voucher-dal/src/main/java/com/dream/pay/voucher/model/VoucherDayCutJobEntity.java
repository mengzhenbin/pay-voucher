package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDayCutJobEntity {
    private Long id;

    private String accountingDate;

    private Integer taskId;

    private Integer taskExecuteState;

    private String taskExecuteOpertor;

    private String taskExecuteDesc;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date updateTime;
}