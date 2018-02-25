package com.dream.pay.voucher.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日切状态配置
 * <p>
 * 1：日切中；2：日切完成；3：日切失败
 *
 * @author mengzhenbin
 * @since 2018/1/11
 */
@Getter
@AllArgsConstructor
public enum DayCutTaskStatus {

    DAY_CUT_ING(1, "执行中"),
    DAY_CUT_SUCCESS(2, "执行成功"),
    DAY_CUT_FAIL(3, "执行失败");

    private int id;
    private String name;

    public static DayCutTaskStatus selectById(int id) {
        for (DayCutTaskStatus dayCutStatus : DayCutTaskStatus.values()) {
            if (dayCutStatus.getId() == id) {
                return dayCutStatus;
            }
        }
        return null;
    }
}
