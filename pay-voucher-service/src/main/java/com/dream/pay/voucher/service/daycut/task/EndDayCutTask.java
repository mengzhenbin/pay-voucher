package com.dream.pay.voucher.service.daycut.task;


import com.dream.pay.voucher.common.AccountingDateUtil;
import com.dream.pay.voucher.common.DayCutTaskList;
import com.dream.pay.voucher.dao.VoucherDayDao;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 会计日期切换
 *
 * @author mengzhenbin
 * @since 2018/2/1
 */
@Slf4j
@Component
public class EndDayCutTask implements DayCutTask {
    @Resource
    VoucherDayDao voucherAccountingDateDao;
    @Resource
    DayCutTaskController dayCutTaskController;

    /**
     * 任务ID
     */
    private static final int TASK_ID = DayCutTaskList.END_DAY_CUT_TASK.getId();
    /**
     * 任务名称
     */
    private static final String TASK_NAME = DayCutTaskList.END_DAY_CUT_TASK.getName();

    public void execute(String voucherDay, boolean isRetry) {
        dayCutTaskController.execute(() -> {
            //切换会计日期
            voucherAccountingDateDao.updateVoucherDay(voucherDay, AccountingDateUtil.getNextDay(voucherDay));
            return "日切日期更新-执行成功";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);
    }
}
