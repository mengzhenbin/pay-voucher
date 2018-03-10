package com.dream.pay.voucher.service.daycut.task;


import com.dream.pay.voucher.access.result.ResultCode;
import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.common.enums.ErrorType;
import com.dream.pay.voucher.component.VoucherCoreComponent;
import com.dream.pay.voucher.model.VoucherTotalSummaryDO;
import com.dream.pay.voucher.repository.TotalSummaryRepository;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 科目发生额试算平衡
 *
 * @author mengzhenbin
 * @since 2017/2/1
 */
@Slf4j
@Component
public class SubjectAmountCheckTask implements DayCutTask {
    @Resource
    TotalSummaryRepository totalSummaryRepositoryImpl;
    @Resource
    DayCutTaskController dayCutTaskController;
    @Resource
    VoucherCoreComponent voucherCoreComponent;

    /**
     * 任务ID
     */
    private static final int TASK_ID = DayCutTaskList.SUBJECT_AMOUNT_CHECK_TASK.getId();

    /**
     * 任务名称
     */
    private static final String TASK_NAME = DayCutTaskList.SUBJECT_AMOUNT_CHECK_TASK.getName();


    @Override
    public void execute(String voucherDay, boolean isRetry) {
        dayCutTaskController.execute(() -> {
            Boolean checkFlag = true;
            List<VoucherTotalSummaryDO> doList = totalSummaryRepositoryImpl.selectByVoucherDate(voucherDay);

            for (VoucherTotalSummaryDO voucherTotalSummaryDO : doList) {
                if (Math.abs(voucherTotalSummaryDO.getDebitAmount() - voucherTotalSummaryDO.getCreditAmount())
                        != Math.abs(voucherTotalSummaryDO.getEndBalance() - voucherTotalSummaryDO.getBeginBalance())) {
                    log.warn("科目{}:{},会计日[{}]发生额试算不通过", voucherTotalSummaryDO.getSubjectCode(), voucherTotalSummaryDO.getSubjectName(), voucherDay);
                    checkFlag = false;
                    //登记错误日志
                    voucherCoreComponent.
                            regErrorLog(voucherTotalSummaryDO.getSubjectCode(),
                                    ErrorType.DAY_CUT.getId(), ResultCode.CUT_ACCT_CHECK_FAIL.getInfoCode(), TASK_ID + ":" + TASK_NAME, ResultCode.CUT_ACCT_CHECK_FAIL.getMessage());
                }
            }
            if (!checkFlag) return "科目发生额试算平衡不通过";
            return "科目发生额试算平衡通过";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);


    }
}