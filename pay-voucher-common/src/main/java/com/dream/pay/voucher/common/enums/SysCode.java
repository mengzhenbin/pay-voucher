package com.dream.pay.voucher.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统配置
 *
 * @author mengzhenbin
 * @since 17/2/1
 */
@AllArgsConstructor
public enum SysCode {
    VOUCHER_DATE("ACCOUNTING_DATE", "会计日"),
    LAST_VOUCHER_DATE("LAST_ACCOUNTING_DATE", "上一会计日"),

    IGNORE_VOUCHER_CONFIG("IGNORE_ACCOUNTING_CONFIG", "不计会计账"),//配置值为json格式:[{"accountingCode":"ZF001","channelCode":"240"}]
    ;

    @Getter
    private String id;
    @Getter
    private String name;

    public static SysCode selectById(String id) {
        for (SysCode sysCode : SysCode.values()) {
            if (sysCode.getId().equals(id)) {
                return sysCode;
            }
        }
        return null;
    }
}
