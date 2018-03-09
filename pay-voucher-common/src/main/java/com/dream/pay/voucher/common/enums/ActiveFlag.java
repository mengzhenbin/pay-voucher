package com.dream.pay.voucher.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 科目可用标志
 *
 * @author mengzhenbin
 * @since 2017/1/12
 */
@AllArgsConstructor
public enum ActiveFlag {
    ABLE(1, "可用"),
    UNABLE(2, "不可用");
    @Getter
    private int id;
    @Getter
    private String name;

    public static ActiveFlag selectById(int id) {
        for (ActiveFlag subjectActiveFlag : ActiveFlag.values()) {
            if (subjectActiveFlag.getId() == id) {
                return subjectActiveFlag;
            }
        }
        return null;
    }
}
