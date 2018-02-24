package com.dream.pay.voucher.model;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherAccountingDateEntity {

    private String lastAccountingDate;

    private String accountingDate;

}