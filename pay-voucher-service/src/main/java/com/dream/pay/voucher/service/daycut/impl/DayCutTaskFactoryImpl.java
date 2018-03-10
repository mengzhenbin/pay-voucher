package com.dream.pay.voucher.service.daycut.impl;

import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.service.daycut.DayCutTaskFactory;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.task.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 日切任务创建工厂实现类，返回日切任务
 *
 * @author mengzhenbin
 * @since 2018/2/1
 */
@Component
public class DayCutTaskFactoryImpl implements DayCutTaskFactory {


    @Resource
    StartDayCutTask startDayCutTask;
    @Resource
    DebtAndCreditSummaryTask debitAndCreditSummaryTask;
    @Resource
    AccountBalanceSummaryTask accountBalanceSummaryTask;
    @Resource
    SubjectAmountSummaryTask subjectAmountSummaryTask;
    @Resource
    DebitAndCreditCheckTask debitAndCreditCheckTask;
    @Resource
    SubAccountAmountCheckTask subAccountAmountCheckTask;
    @Resource
    SubjectAmountCheckTask subjectAmountCheckTask;
    @Resource
    AccountBalanceInitTask accountBalanceInitTask;
    @Resource
    SubjectBalanceInitTask subjectBalanceInitTask;
    @Resource
    EndDayCutTask endDayCutTask;

    public DayCutTask getTask(int taskId) {

        //创建日切记录任务
        if (taskId == DayCutTaskList.START_DAY_CUT_TASK.getId()) {
            return startDayCutTask;
        }

        //借贷发生额汇总任务
        if (taskId == DayCutTaskList.DEBIT_AND_CREDIT_SUMMARY_TASK.getId()) {
            return debitAndCreditSummaryTask;
        }

        //内部户余额汇总任务
        if (taskId == DayCutTaskList.INNER_ACCOUNT_BALANCE_SUMMARY_TASK.getId()) {
            return accountBalanceSummaryTask;
        }

        //科目余额汇总任务
        if (taskId == DayCutTaskList.SUBJECT_AMOUNT_SUMMARY_TASK.getId()) {
            return subjectAmountSummaryTask;
        }

        //借贷试算平衡任务
        if (taskId == DayCutTaskList.DEBIT_AND_CREDIT_CHECK_TASK.getId()) {
            return debitAndCreditCheckTask;
        }

        //分户发生额试算平衡
        if (taskId == DayCutTaskList.SUB_ACCOUNT_AMOUNT_CHECK_TASK.getId()) {
            return subAccountAmountCheckTask;
        }
        //科目发生额试算平衡
        if (taskId == DayCutTaskList.SUBJECT_AMOUNT_CHECK_TASK.getId()) {
            return subjectAmountCheckTask;
        }

        //分户期初余额更新任务
        if (taskId == DayCutTaskList.ACCOUNT_BALANCE_INIT_TASK.getId()) {
            return accountBalanceInitTask;
        }
        //科目期初余额更新任务
        if (taskId == DayCutTaskList.SUBJECT_BALANCE_INIT_TASK.getId()) {
            return subjectBalanceInitTask;
        }

        //会计日期切换任务
        if (taskId == DayCutTaskList.END_DAY_CUT_TASK.getId()) {
            return endDayCutTask;
        }
        return null;
    }
}