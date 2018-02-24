package com.dream.pay.voucher.service.daycut;

/**
 * 日切任务执行器
 *
 * @author mengzhenbin
 * @since 2018/2/1
 */
public interface DayCutTaskScheduler {

    void run(String accountingDate);

}
