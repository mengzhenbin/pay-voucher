package com.dream.pay.voucher.service.daycut.impl;

import com.dream.pay.voucher.common.DayCutTaskList;
import com.dream.pay.voucher.service.daycut.DayCutTaskFactory;
import com.dream.pay.voucher.service.daycut.DayCutTaskScheduler;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 日切任务执行器实现
 *
 * @author mengzhenbin
 * @since 2018/2/1
 */
@Component
@Slf4j
public class DayCutTaskSchedulerImpl implements DayCutTaskScheduler {
    @Resource
    private DayCutTaskFactory dayCutTaskFactoryImpl;

    /**
     * 遍历按顺序执行所有任务
     *
     * @param accountingDate
     */
    public void run(String accountingDate) {

        for (DayCutTaskList dayCutTaskList : DayCutTaskList.values()) {
            DayCutTask dayCutTask = dayCutTaskFactoryImpl.getTask(dayCutTaskList.getId());
            if (null != dayCutTask) {
                dayCutTask.execute(accountingDate, false);
            } else {
                log.error("[{}]未找到相应的处理类，未做处理", dayCutTaskList.getName());
            }
        }
    }
}
