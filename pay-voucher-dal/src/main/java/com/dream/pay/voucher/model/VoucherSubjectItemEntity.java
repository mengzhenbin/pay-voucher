package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherSubjectItemEntity {
    private Long id;

    private String subjectCode;

    private Boolean subjectType;

    private Boolean subjectLevel;

    private String subjectName;

    private String subjectAbbName;

    private String parentsCode;

    private String balanceDir;

    private String enableDate;

    private String unableDate;

    private Boolean overFlag;

    private Boolean activeFlag;

    private Date createTime;

    private Date updateTime;
}