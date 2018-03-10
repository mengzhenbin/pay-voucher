package com.dream.pay.voucher.service.daycut.task;

import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.model.VoucherDebitCreditSummaryDO;
import com.dream.pay.voucher.repository.DebitCreditRepository;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 借贷试算平衡
 *
 * @author mengzhenbin
 * @since 2017/2/1
 */
@Slf4j
@Component
public class DebitAndCreditCheckTask implements DayCutTask {
    @Resource
    DebitCreditRepository debitCreditRepositoryImpl;
    @Resource
    DayCutTaskController dayCutTaskController;

    /**
     * 任务ID
     */
    private static final int TASK_ID = DayCutTaskList.DEBIT_AND_CREDIT_CHECK_TASK.getId();
    /**
     * 任务名称
     */
    private static final String TASK_NAME = DayCutTaskList.DEBIT_AND_CREDIT_CHECK_TASK.getName();


    @Override
    public void execute(String voucherDay, boolean isRetry) {
        dayCutTaskController.execute(() -> {
            VoucherDebitCreditSummaryDO debtCreditSummaryDO = debitCreditRepositoryImpl.getByVoucherDate(voucherDay);
            if (debtCreditSummaryDO != null) {
                if (debtCreditSummaryDO.getCreditAmount().longValue() != debtCreditSummaryDO.getDebitAmount().longValue()) {
                    return "借贷试算平衡不通过,所有借方之和不等于所有贷方之和";
                }
            }
            return "借贷试算平衡通过,所有借方之和等于所有贷方之和";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);
    }
}
