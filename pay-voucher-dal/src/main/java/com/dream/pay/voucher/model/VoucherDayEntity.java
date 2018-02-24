package com.dream.pay.voucher.model;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDayEntity {

    private String lastVoucherDay;

    private String currentVoucherDay;


}