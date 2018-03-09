package com.dream.pay.voucher.access.facade;


import com.dream.pay.voucher.access.request.VoucherRecordParams;

/**
 * 会计分录-记账
 *
 * @author mengzhenbin
 * @since 2017/1/17
 */
public interface VoucherRecordFacade {

    void doVoucher(VoucherRecordParams recordParams);
}
