package com.dream.pay.voucher.service.daycut;


import com.dream.pay.voucher.service.daycut.core.DayCutTask;

/**
 * 日切任务创建工厂类，返回日切任务
 *
 * @author mengzhenbin
 * @since 2018/2/1
 */
public interface DayCutTaskFactory {

    /**
     * 根据任务ID获取日切任务
     *
     * @param taskId
     * @return DayCutTask
     */
    DayCutTask getTask(int taskId);
}
