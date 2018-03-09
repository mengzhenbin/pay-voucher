package com.dream.pay.voucher.access.impl;

import com.dream.pay.voucher.access.facade.VoucherRecordFacade;
import com.dream.pay.voucher.access.request.VoucherRecordParams;
import com.dream.pay.voucher.component.VoucherCoreComponent;
import com.dream.pay.voucher.service.voucher.VoucherEngineService;
import com.youzan.platform.util.lang.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author mengzhenbin
 * @since 2017/1/17
 */
@Path("/voucher")
@Slf4j
public class VoucherRecodeFacadeImpl implements VoucherRecordFacade {

    @Resource
    VoucherEngineService voucherEngineService;
    @Resource
    VoucherCoreComponent voucherCoreComponent;

    @POST
    @Path("/doVoucher")
    @Override
    public void doVoucher(VoucherRecordParams recordParams) {

        long currentTime = System.currentTimeMillis();
        try {
            if (StringUtil.isBlank(recordParams.getAccountingDate())) {
                recordParams.setAccountingDate(voucherCoreComponent.getVoucherDate()); //设置会计日期
            }
            voucherEngineService.execute(recordParams);
        } catch (Exception e) {
            log.error("会计分录异常,耗时{}ms,{}", System.currentTimeMillis() - currentTime, recordParams, e);
        }
    }
}
