package com.dream.pay.voucher.service.daycut.task;


import com.dream.pay.voucher.common.DayCutTaskList;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 日切启动任务
 *
 * @author hechengqi  17/2/1
 */
@Slf4j
@Component
public class StartDayCutTask implements DayCutTask {
    @Resource
    DayCutTaskController dayCutTaskController;

    /**
     * 任务ID
     */
    private static final int TASK_ID = DayCutTaskList.START_DAY_CUT_TASK.getId();
    /**
     * 任务名称
     */
    private static final String TASK_NAME = DayCutTaskList.START_DAY_CUT_TASK.getName();

    public void execute(String voucherDay, boolean isRetry) {
        dayCutTaskController.execute(() -> {
            return "日切启动任务-执行成功";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);
    }
}
