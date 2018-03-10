package com.dream.pay.voucher.model;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherSubjectItemDO {
    private Long id;

    private String subjectCode;

    private Integer subjectType;

    private Integer subjectLevel;

    private String subjectName;

    private String subjectAbbName;

    private String parentsCode;

    private String balanceDir;

    private String enableDate;

    private String unableDate;

    private Integer overFlag;

    private Integer activeFlag;

    private Date createTime;

    private Date updateTime;

}