package com.dream.pay.voucher.service.daycut.core;

/**
 * 日切任务-执行结果回调
 *
 * @author mengzhenbin
 * @since 2017/11/29.
 */
public interface DayCutTaskCallback<T> {

    String execute();
}
