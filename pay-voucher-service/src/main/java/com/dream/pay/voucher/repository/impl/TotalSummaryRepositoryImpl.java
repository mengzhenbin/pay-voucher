package com.dream.pay.voucher.repository.impl;

import com.dream.pay.voucher.dao.VoucherTotalSummaryDOMapper;
import com.dream.pay.voucher.model.VoucherTotalSummaryDO;
import com.dream.pay.voucher.repository.TotalSummaryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author mengzhenbin
 * @since 2017/1/20
 */
@Component
public class TotalSummaryRepositoryImpl implements TotalSummaryRepository {

    @Resource
    VoucherTotalSummaryDOMapper voucherTotalSummaryDao;

    @Override
    public List<VoucherTotalSummaryDO> selectByVoucherDate(String voucherDate) {
        return voucherTotalSummaryDao.getByVoucherDate(voucherDate);
    }

    @Override
    public List<VoucherTotalSummaryDO> selectByVoucherDateDateAndSubjectLevel(String voucherDate, Integer subjectLevel) {
        return voucherTotalSummaryDao.getByVoucherDateAndSubjectLevel(voucherDate, subjectLevel);
    }

    @Override
    public long insert(VoucherTotalSummaryDO voucherTotalSummaryDO) {
        voucherTotalSummaryDO.setCreateTime(new Date());
        voucherTotalSummaryDO.setUpdateTime(new Date());
        return voucherTotalSummaryDao.insert(voucherTotalSummaryDO);
    }

    @Override
    public long updateBalance(VoucherTotalSummaryDO voucherTotalSummaryDO) {
        voucherTotalSummaryDO.setUpdateTime(new Date());
        return voucherTotalSummaryDao.updateByPrimaryKeySelective(voucherTotalSummaryDO);
    }

    @Override
    public VoucherTotalSummaryDO selectByVoucherDateDateAndSubjectCode(String accountingDate, String subjectCode) {
        return voucherTotalSummaryDao.getByVoucherDateAndSubjectCode(accountingDate, subjectCode);
    }
}
