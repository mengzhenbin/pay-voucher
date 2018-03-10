package com.dream.pay.voucher.access.result;

/**
 * 结果码接口
 *
 * @author mengzhenbin
 * @since 2016/01/11.
 */
public interface IResultCode {

    /**
     * 获取信息码
     */
    String getInfoCode();

    /**
     * 获取业务码
     */
    String getTypeCode();

    /**
     * 获取结果描述信息
     */
    String getMessage();

}
