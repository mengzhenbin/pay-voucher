package com.dream.pay.voucher.service.voucher;


import com.dream.pay.voucher.access.request.VoucherRecordParams;

/**
 * 记账引擎
 * 功能:负责把账务收付消息转化为分录保存并更新会计日账户余额
 *
 * @author mengzhenbin
 * @since 2017/1/10
 */
public interface VoucherEngineService {
    /**
     * 记账处理
     *
     * @param voucherRecordParams
     */
    void execute(VoucherRecordParams voucherRecordParams);

}
