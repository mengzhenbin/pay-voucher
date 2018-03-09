package com.dream.pay.voucher.component.impl;


import com.dream.pay.voucher.access.request.VoucherRecordParams;
import com.dream.pay.voucher.component.RecordClearingComponent;
import com.dream.pay.voucher.model.VoucherRecordConfigDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mengzhenbin
 * @since 2017/1/31
 */
@Slf4j
@Component
public class RecordClearingComponentImpl implements RecordClearingComponent {
    /**
     * @return
     */
    @Override
    public List<Boolean> getRecord(VoucherRecordParams voucherRecordParams, VoucherRecordConfigDO voucherRecordConfigDO) {
        return null;
    }
}
