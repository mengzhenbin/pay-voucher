package com.dream.pay.voucher.service.daycut.task;

import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.common.enums.RecordDir;
import com.dream.pay.voucher.model.VoucherSubjectRecordDO;
import com.dream.pay.voucher.repository.DebitCreditRepository;
import com.dream.pay.voucher.repository.SubjectRecordRepository;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    SubjectRecordRepository subjectRecordRepositoryImpl;
    @Resource
    DebitCreditRepository debitCreditRepositoryImpl;

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
            Long maxId = subjectRecordRepositoryImpl.selectMaxId(voucherDay);
            maxId = maxId == null ? 0 : maxId;
            int pageCount = (int) (maxId / PAGE_SIZE);
            if (maxId % PAGE_SIZE != 0) {
                pageCount = pageCount + 1;
            }
            //分页读取
            long startRow = maxId - PAGE_SIZE <= 0 ? 0 : (maxId - PAGE_SIZE) + 1;
            long endRow = maxId;

            List<VoucherSubjectRecordDO> voucherRecords;
            for (int i = 0; i < pageCount; ++i) {
                voucherRecords = subjectRecordRepositoryImpl.selectByVoucherDate(voucherDay, startRow, endRow);

                if (voucherRecords.size() == 0) {
                    break;
                }
                if (i != pageCount - 1) {
                    startRow = startRow - PAGE_SIZE <= 0 ? 1 : startRow - PAGE_SIZE;
                    endRow -= PAGE_SIZE;
                }

                for (VoucherSubjectRecordDO voucherSubjectRecordDO : voucherRecords) {

                    if (voucherSubjectRecordDO.getRecordDir().equals(RecordDir.D.getId())) {
                        //借方发生额
                        debitAmount += voucherSubjectRecordDO.getAmount();
                    } else {
                        //贷方发生额
                        creditAmount += voucherSubjectRecordDO.getAmount();
                    }
                }
            }
            //插入借贷发生汇总记录
            debitCreditRepositoryImpl.insertAmount(voucherDay, debitAmount, creditAmount);
            return "借贷发生额汇总完毕";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);

    }
}
