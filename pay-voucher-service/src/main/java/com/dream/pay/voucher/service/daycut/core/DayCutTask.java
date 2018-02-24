package com.dream.pay.voucher.service.daycut.core;

/**
 * 抽象 [日切任务] 接口
 *
 * @author mengzhenbin
 * @since 2017/12/5.
 */
public interface DayCutTask {

    /**
     * 日切任务执行
     *
     * @param accountingDate 日切日
     * @param isRetry        是否重试
     */
    void execute(String accountingDate, boolean isRetry);

}
