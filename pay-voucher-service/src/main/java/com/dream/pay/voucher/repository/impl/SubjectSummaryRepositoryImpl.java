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
        Long minId = voucherSubjectSummaryDao.selectMinId(voucherDate);
        if (minId == null) {
            return 0;
        } else {
            return minId;
        }
    }

    @Override
    public long selectMaxIdBySubjectCode(String voucherDate, String subjectCode) {
        Long maxId = voucherSubjectSummaryDao.selectMaxIdBySubjectCode(voucherDate,subjectCode);
        if (maxId == null) {
            return 0;
        } else {
            return maxId;
        }
    }

    @Override
    public long selectMinIdBySubjectCode(String voucherDate, String subjectCode) {
        Long minId = voucherSubjectSummaryDao.selectMinIdBySubjectCode(voucherDate,subjectCode);
        if (minId == null) {
            return 0;
        } else {
            return minId;
        }
    }

    @Override
    public List<VoucherSubjectSummaryDO> selectByVoucherDate(String voucherDate, long startRow, long endRow) {
        return voucherSubjectSummaryDao.selectByVoucherDate(voucherDate, startRow, endRow);
    }

    @Override
    public List<VoucherSubjectSummaryDO> selectByVoucherDateAndSubjectCode(String voucherDate, String subjectCode, long startRow, long endRow) {
        return voucherSubjectSummaryDao.selectByVoucherDateAndSubjectCode(voucherDate, subjectCode, startRow, endRow);
    }

    @Override
    public int update(VoucherSubjectSummaryDO voucherSubjectSummaryDO) {
        return voucherSubjectSummaryDao.updateByPrimaryKey(voucherSubjectSummaryDO);
    }
}
