package com.dream.pay.voucher.common;


import lombok.Getter;

/**
 * 日切任务列表
 *
 * @author mengzhenbin
 * @since 2018/2/1
 */
@Getter
public enum DayCutTaskList {
    START_DAY_CUT_TASK(1, "日切创建任务"),
    DEBT_AND_CREDIT_SUMMARY_TASK(2, "借贷发生额汇总任务"),
    INNER_ACCT_BALANCE_SUMMARY_TASK(3, "内部户余额汇总任务"),
    SUBJECT_AMOUNT_SUMMARY_TASK(4, "科目余额汇总任务"),
    DEBT_AND_CREDIT_CHECK_TASK(5, "借贷试算平衡任务"),
    SUB_ACCT_AMOUNT_CHECK_TASK(6, "分户发生额试算平衡任务"),
    SUBJECT_AMOUNT_CHECK_TASK(7, "科目发生额试算平衡任务"),
    ACCT_BALANCE_MOVE_TASK(8, "分户下一日期初余额更新任务"),
    SUBJECT_BALANCE_MOVE_TASK(9, "科目下一日期初余额更新任务"),
    END_DAY_CUT_TASK(10, "会计日期切换任务");

    private int id;
    private String name;

    DayCutTaskList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DayCutTaskList selectById(int id) {
        for (DayCutTaskList dayCutTaskList : DayCutTaskList.values()) {
            if (dayCutTaskList.getId() == id) {
                return dayCutTaskList;
            }
        }
        return null;
    }
}
