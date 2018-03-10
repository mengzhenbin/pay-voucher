package com.dream.pay.voucher.access.result;

/**
 * 会计系统结果码接口
 * <p>
 * 系统内部结果码枚举均实现此接口
 *
 * @author mengzhenbin
 * @since 2016/01/11.
 */
public interface VoucherResultCode extends IResultCode {

    /**
     * 系统码
     */
    String SYSTEM_CODE = "1181";


    default int getCode() {
        return Integer.valueOf(SYSTEM_CODE + getTypeCode() + getInfoCode());
    }

    /**
     * 验证结果码是否相同
     *
     * @param code 待验证结果码
     * @return 相同: true, 不同: false
     */
    default boolean isCode(int code) {
        return getCode() == code;
    }
}
