package com.dream.pay.voucher.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 借贷方向配置
 *
 * @author mengzhenbin
 * @since 2018/1/10
 */
@Getter
@AllArgsConstructor
public enum RecordDir {
    D("D", "借方"),
    C("C", "贷方");

    private String id;
    private String name;

    public static RecordDir selectById(String id) {
        for (RecordDir recordDirEnum : RecordDir.values()) {
            if (recordDirEnum.getId().equals(id)) {
                return recordDirEnum;
            }
        }
        return null;
    }
}
