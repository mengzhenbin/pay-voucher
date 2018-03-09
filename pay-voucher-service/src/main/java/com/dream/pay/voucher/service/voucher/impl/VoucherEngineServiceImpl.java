package com.dream.pay.voucher.service.voucher.impl;

import com.dream.pay.voucher.access.request.VoucherRecordParams;
import com.dream.pay.voucher.service.voucher.VoucherEngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 记账引擎
 * 功能:负责把账务收付消息转化为分录保存并更新会计日账户余额
 *
 * @author mengzhenbin
 * @since 2017/1/10
 */
@Slf4j
@Component
public class VoucherEngineServiceImpl implements VoucherEngineService {

    @Override
    @Transactional
    public void execute(VoucherRecordParams voucherRecordParams) {
        log.info("会计分录开始,入参:{}", voucherRecordParams);

        log.info("会计分录结束");

    }
}

