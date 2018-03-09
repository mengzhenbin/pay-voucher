package com.dream.pay.voucher.repository.impl;

import com.dream.pay.voucher.dao.VoucherDebitCreditSummaryDOMapper;
import com.dream.pay.voucher.model.VoucherDebitCreditSummaryDO;
import com.dream.pay.voucher.repository.DebitCreditRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author mengzhenbin
 * @since 2017/1/20
 */
@Component
public class DebitCreditRepositoryImpl implements DebitCreditRepository {
    @Resource
    VoucherDebitCreditSummaryDOMapper voucherDebitCreditSummaryDao;

    @Override
    public Integer insertAmount(String voucherDate, long debitAmount, long creditAmount) {
        VoucherDebitCreditSummaryDO voucherDebitCreditSummaryDO = new VoucherDebitCreditSummaryDO();
        voucherDebitCreditSummaryDO.setVoucherDate(voucherDate);
        voucherDebitCreditSummaryDO.setCreditAmount(creditAmount);
        voucherDebitCreditSummaryDO.setDebitAmount(debitAmount);
        voucherDebitCreditSummaryDO.setCreateTime(new Date());
        voucherDebitCreditSummaryDO.setUpdateTime(new Date());
        return voucherDebitCreditSummaryDao.insert(voucherDebitCreditSummaryDO);
    }

    @Override
    public VoucherDebitCreditSummaryDO getByVoucherDate(String voucherDate) {

        return voucherDebitCreditSummaryDao.getByVoucherDate(voucherDate);
    }
}
