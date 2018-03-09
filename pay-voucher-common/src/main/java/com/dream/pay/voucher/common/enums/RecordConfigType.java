package com.dream.pay.voucher.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 内外户标志
 *
 * @author mengzhenbin
 * @since 2017/1/12
 */
@AllArgsConstructor
public enum RecordConfigType {
    INNER_FOR_ALL(1, "借贷都是内部户"),
    INNER_FOR_D(2, "借方为内部户"),
    INNER_FOR_C(3, "贷方为内部户"),
    CUSTOMER_FOR_ALL(4, "借贷都是客户");
    @Getter
    private int id;
    @Getter
    private String name;

    public static RecordConfigType selectById(int id) {
        for (RecordConfigType acctInnerFlag : RecordConfigType.values()) {
            if (acctInnerFlag.getId() == id) {
                return acctInnerFlag;
            }
        }
        return null;
    }
}
