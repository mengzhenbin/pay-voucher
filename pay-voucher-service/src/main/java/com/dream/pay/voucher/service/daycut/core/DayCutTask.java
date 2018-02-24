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
     * @param voucherDay 会计日
     * @param isRetry    是否重试
     */
    void execute(String voucherDay, boolean isRetry);

}
