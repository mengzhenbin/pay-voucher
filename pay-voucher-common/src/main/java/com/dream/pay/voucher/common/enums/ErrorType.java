package com.dream.pay.voucher.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 错误日志类型
 *
 * @author mengzhenbin
 * @since 2017/2/1
 */
@AllArgsConstructor
public enum ErrorType {
    RECORD(1, "分录"),
    DAY_CUT(2, "日切");

    @Getter
    private int id;
    @Getter
    private String name;

    public static ErrorType selectById(int id) {
        for (ErrorType errorType : ErrorType.values()) {
            if (errorType.getId() == id) {
                return errorType;
            }
        }
        return null;
    }
}
