package com.dream.pay.voucher.service.daycut.task;


import com.dream.pay.voucher.access.result.ResultCode;
import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.common.enums.ErrorType;
import com.dream.pay.voucher.component.VoucherCoreComponent;
import com.dream.pay.voucher.model.VoucherSubjectSummaryDO;
import com.dream.pay.voucher.repository.SubjectSummaryRepository;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 分户发生额试算平衡
 *
 * @author mengzhenbin
 * @since 2017/2/1
 */
@Slf4j
@Component
public class SubAccountAmountCheckTask implements DayCutTask {
    @Resource
    SubjectSummaryRepository subjectSummaryRepository;
    @Resource
    DayCutTaskController dayCutTaskController;
    @Resource
    VoucherCoreComponent voucherCoreComponent;

    /**
     * 任务ID
     */
    private static final int TASK_ID = DayCutTaskList.SUB_ACCOUNT_AMOUNT_CHECK_TASK.getId();

    /**
     * 任务名称
     */
    private static final String TASK_NAME = DayCutTaskList.SUB_ACCOUNT_AMOUNT_CHECK_TASK.getName();

    /**
     * 分页读取最大数据量
     **/
    private static final int PAGE_SIZE = 200;

    @Override
    public void execute(String voucherDay, boolean isRetry) {
        dayCutTaskController.execute(() -> {
            boolean checkFlag = true;
            long maxId = subjectSummaryRepository.selectMaxId(voucherDay);
            int pageCount = (int) (maxId / PAGE_SIZE);
            if (maxId % PAGE_SIZE != 0) {
                pageCount = pageCount + 1;
            }
            //分页读取
            long startRow = maxId - PAGE_SIZE <= 0 ? 0 : (maxId - PAGE_SIZE) + 1;
            long endRow = maxId;

            List<VoucherSubjectSummaryDO> list = new ArrayList();
            for (int i = 0; i < pageCount; ++i) {
                list = subjectSummaryRepository.selectByVoucherDate(voucherDay, startRow, endRow);
                if (i != pageCount - 1) {
                    startRow = startRow - PAGE_SIZE <= 0 ? 1 : startRow - PAGE_SIZE;
                    endRow -= PAGE_SIZE;
                }

                if (list.size() == 0) {
                    break;
                }

                for (VoucherSubjectSummaryDO voucherSubjectSummaryDO : list) {
                    if (voucherSubjectSummaryDO.getEndBalance() - voucherSubjectSummaryDO.getBeginBalance() != voucherSubjectSummaryDO.getAmount()) {
                        log.warn("账户{},发生额试算不通过", voucherSubjectSummaryDO.getAccountNo());
                        checkFlag = false;
                        //登记错误日志
                        voucherCoreComponent.
                                regErrorLog(voucherSubjectSummaryDO.getAccountNo(),
                                        ErrorType.DAY_CUT.getId(), ResultCode.CUT_ACCT_CHECK_FAIL.getInfoCode(), TASK_ID + ":" + TASK_NAME, ResultCode.CUT_ACCT_CHECK_FAIL.getMessage());
                    }
                }
            }
            if (!checkFlag) return "分户发生额试算平衡不通过";
            return "分户发生额试算平衡通过";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);

    }
}
