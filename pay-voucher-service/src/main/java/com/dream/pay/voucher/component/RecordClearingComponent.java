package com.dream.pay.voucher.component;


import com.dream.pay.voucher.access.request.VoucherRecordParams;
import com.dream.pay.voucher.model.VoucherRecordConfigDO;

import java.util.List;

/**
 * 借贷清分组件
 * 功能:根据科目代码和收付类型，得出分录的借贷方向
 *
 * @author mengzhenbin
 * @since 2017/1/31
 */
public interface RecordClearingComponent {

    public List<Boolean> getRecord(VoucherRecordParams voucherRecordParams, VoucherRecordConfigDO voucherRecordConfigDO);
}
