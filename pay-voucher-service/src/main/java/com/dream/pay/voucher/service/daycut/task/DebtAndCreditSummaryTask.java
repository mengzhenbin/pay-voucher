package com.dream.pay.voucher.service.daycut.task;

import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.common.enums.RecordDir;
import com.dream.pay.voucher.dao.VoucherDebitCreditSummaryDao;
import com.dream.pay.voucher.dao.VoucherSubjectRecordDao;
import com.dream.pay.voucher.model.VoucherDebitCreditSummaryEntity;
import com.dream.pay.voucher.model.VoucherSubjectRecordEntity;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author mengzhnebin
 * @since 20118/2/7
 */
@Slf4j
@Component
public class DebtAndCreditSummaryTask implements DayCutTask {

    @Resource
    DayCutTaskController dayCutTaskController;
    @Resource
    VoucherSubjectRecordDao voucherSubjectRecordDao;
    @Resource
    VoucherDebitCreditSummaryDao voucherDebitCreditSummaryDao;

    /**
     * 任务ID
     */
    private static final int TASK_ID = DayCutTaskList.DEBT_AND_CREDIT_SUMMARY_TASK.getId();
    /**
     * 任务名称
     */
    private static final String TASK_NAME = DayCutTaskList.DEBT_AND_CREDIT_SUMMARY_TASK.getName();

    private static final int PAGE_SIZE = 200;

    @Override
    public void execute(String voucherDay, boolean isRetry) {
        dayCutTaskController.execute(() -> {
            long debitAmount = 0L;
            long creditAmount = 0L;
            //获取最大记录ID
            long maxId = voucherSubjectRecordDao.selectMaxId(voucherDay);
            int pageCount = (int) (maxId / PAGE_SIZE);
            if (maxId % PAGE_SIZE != 0) {
                pageCount = pageCount + 1;
            }
            //分页读取
            long startRow = maxId - PAGE_SIZE <= 0 ? 0 : (maxId - PAGE_SIZE) + 1;
            long endRow = maxId;

            List<VoucherSubjectRecordEntity> voucherRecords;
            for (int i = 0; i < pageCount; ++i) {
                voucherRecords = voucherSubjectRecordDao.selectByVoucherDay(voucherDay, startRow, endRow);

                if (voucherRecords.size() == 0) {
                    break;
                }
                if (i != pageCount - 1) {
                    startRow = startRow - PAGE_SIZE <= 0 ? 1 : startRow - PAGE_SIZE;
                    endRow -= PAGE_SIZE;
                }

                for (VoucherSubjectRecordEntity voucherSubjectRecordEntity : voucherRecords) {

                    if (voucherSubjectRecordEntity.getRecordDir().equals(RecordDir.D.getId())) {
                        //借方发生额
                        debitAmount += voucherSubjectRecordEntity.getAmount();
                    } else {
                        //贷方发生额
                        creditAmount += voucherSubjectRecordEntity.getAmount();
                    }
                }
            }
            //插入借贷发生汇总记录
            voucherDebitCreditSummaryDao.insert(buildVoucherDebitCreditSummaryEntity(voucherDay, debitAmount, creditAmount));
            return "借贷发生额汇总完毕";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);

    }

    /**
     * 组装借贷发生汇总记录
     *
     * @param voucherDay
     * @param debitAmount
     * @param creditAmount
     * @return
     */
    private VoucherDebitCreditSummaryEntity buildVoucherDebitCreditSummaryEntity(String voucherDay, long debitAmount, long creditAmount) {
        VoucherDebitCreditSummaryEntity voucherDebitCreditSummaryEntity = new VoucherDebitCreditSummaryEntity();
        voucherDebitCreditSummaryEntity.setVoucherDate(voucherDay);
        voucherDebitCreditSummaryEntity.setCreditAmount(creditAmount);
        voucherDebitCreditSummaryEntity.setDebitAmount(debitAmount);
        voucherDebitCreditSummaryEntity.setCreateTime(new Date());
        voucherDebitCreditSummaryEntity.setUpdateTime(new Date());
        return voucherDebitCreditSummaryEntity;
    }
}
