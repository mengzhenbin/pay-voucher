package com.dream.pay.voucher.service.daycut.impl;

import com.dream.pay.voucher.common.DayCutTaskList;
import com.dream.pay.voucher.service.daycut.DayCutTaskFactory;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.task.EndDayCutTask;
import com.dream.pay.voucher.service.daycut.task.StartDayCutTask;
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

    //    @Resource
//    AcctBalanceMoveTask acctBalanceMoveTask;
//    @Resource
//    SubjectBalanceMoveTask subjectBalanceMoveTask;
    @Resource
    StartDayCutTask startDayCutTask;
    //    @Resource
//    DebtAndCreditCheckTask debtAndCreditCheckTask;
//    @Resource
//    SubAcctAmountCheckTask subAcctAmountCheckTask;
//    @Resource
//    SubjectAmountCheckTask subjectAmountCheckTask;
    @Resource
    EndDayCutTask endDayCutTask;
//    @Resource
//    DebtAndCreditSummaryTask debtAndCreditSummaryTask;
//    @Resource
//    AcctBalanceSummaryTask acctBalanceSummaryTask;
//    @Resource
//    SubjectAmountSummaryTask subjectAmountSummaryTask;

    public DayCutTask getTask(int taskId) {

        //创建日切记录任务
        if (taskId == DayCutTaskList.START_DAY_CUT_TASK.getId()) {
            return startDayCutTask;
        }

//        //借贷发生额汇总任务
//        if (taskId == DayCutTaskList.DEBT_AND_CREDIT_SUMMARY_TASK.getId()) {
//            return debtAndCreditSummaryTask;
//        }
//
//        //内部户余额汇总任务
//        if (taskId == DayCutTaskList.INNER_ACCT_BALANCE_SUMMARY_TASK.getId()) {
//            return acctBalanceSummaryTask;
//        }
//
//        //借贷试算平衡任务
//        if (taskId == DayCutTaskList.DEBT_AND_CREDIT_CHECK_TASK.getId()) {
//            return debtAndCreditCheckTask;
//        }
//
//        //
//        if (taskId == DayCutTaskList.SUBJECT_AMOUNT_SUMMARY_TASK.getId()) {
//            return subjectAmountSummaryTask;
//        }
//
//        //分户发生额试算平衡
//        if (taskId == DayCutTaskList.SUB_ACCT_AMOUNT_CHECK_TASK.getId()) {
//            return subAcctAmountCheckTask;
//        }
//        //科目发生额试算平衡
//        if (taskId == DayCutTaskList.SUBJECT_AMOUNT_CHECK_TASK.getId()) {
//            return subjectAmountCheckTask;
//        }
//
//        //分户期初余额更新任务
//        if (taskId == DayCutTaskList.ACCT_BALANCE_MOVE_TASK.getId()) {
//            return acctBalanceMoveTask;
//        }
//        //科目期初余额更新任务
//        if (taskId == DayCutTaskList.SUBJECT_BALANCE_MOVE_TASK.getId()) {
//            return subjectBalanceMoveTask;
//        }
        //会计日期切换任务
        if (taskId == DayCutTaskList.END_DAY_CUT_TASK.getId()) {
            return endDayCutTask;
        }
        return null;
    }
}