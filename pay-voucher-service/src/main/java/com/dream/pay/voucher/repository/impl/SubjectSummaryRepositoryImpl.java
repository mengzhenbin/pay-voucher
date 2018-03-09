package com.dream.pay.voucher.repository.impl;

import com.dream.pay.voucher.dao.VoucherSubjectSummaryDOMapper;
import com.dream.pay.voucher.model.VoucherSubjectSummaryDO;
import com.dream.pay.voucher.repository.SubjectSummaryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mengzhenbin
 * @since 2017/1/13
 */
@Component
public class SubjectSummaryRepositoryImpl implements SubjectSummaryRepository {
    @Resource
    VoucherSubjectSummaryDOMapper voucherSubjectSummaryDao;

    @Override
    public long selectMaxId(String voucherDate) {
        Long maxId = voucherSubjectSummaryDao.selectMaxId(voucherDate);
        if (maxId == null) {
            return 0;
        } else {
            return maxId;
        }
    }

    @Override
    public long selectMinId(String voucherDate) {
        Long maxId = voucherSubjectSummaryDao.selectMinId(voucherDate);
        if (maxId == null) {
            return 0;
        } else {
            return maxId;
        }
    }

    @Override
    public List<VoucherSubjectSummaryDO> selectByVoucherDate(String voucherDate, long startRow, long endRow) {
        return voucherSubjectSummaryDao.selectByVoucherDate(voucherDate, startRow, endRow);
    }

    @Override
    public int update(VoucherSubjectSummaryDO voucherSubjectSummaryDO) {
        return voucherSubjectSummaryDao.updateByPrimaryKey(voucherSubjectSummaryDO);
    }
}
