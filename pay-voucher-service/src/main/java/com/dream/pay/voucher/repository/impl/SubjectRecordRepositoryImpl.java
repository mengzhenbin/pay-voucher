package com.dream.pay.voucher.repository.impl;

import com.dream.pay.voucher.dao.VoucherSubjectRecordDOMapper;
import com.dream.pay.voucher.model.VoucherSubjectRecordDO;
import com.dream.pay.voucher.repository.SubjectRecordRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会计分录
 *
 * @author mengzhnebin
 * @since 2017/1/10
 */
@Component
public class SubjectRecordRepositoryImpl implements SubjectRecordRepository {
    @Resource
    VoucherSubjectRecordDOMapper voucherSubjectRecordDao;

    @Override
    public long selectMaxId(String accountingDate) {
        Long maxId = voucherSubjectRecordDao.selectMaxId(accountingDate);
        if (maxId == null) {
            return 0;
        } else {
            return maxId;
        }
    }

    @Override
    public List<VoucherSubjectRecordDO> selectByVoucherDate(String voucherDate, long startRow, long endRow) {
        return voucherSubjectRecordDao.selectByVoucherDay(voucherDate, startRow, endRow);
    }

    @Override
    public long countByVoucherDateAndAccountNo(String voucherDate, String accountNo) {
        Long maxId = voucherSubjectRecordDao.countByVoucherDayAndAccountNo(voucherDate, accountNo);
        if (maxId == null) {
            return 0;
        } else {
            return maxId;
        }
    }

    @Override
    public List<VoucherSubjectRecordDO> selectByVoucherDateAndAccountNo(String voucherDate, String accountNo, long startNo, long pageSize) {
        return voucherSubjectRecordDao.selectByVoucherDayAndAccountNo(voucherDate, accountNo, startNo, pageSize);
    }
}
