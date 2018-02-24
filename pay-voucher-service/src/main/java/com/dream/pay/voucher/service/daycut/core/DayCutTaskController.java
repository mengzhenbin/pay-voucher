package com.dream.pay.voucher.service.daycut.core;

import com.dream.pay.voucher.common.DayCutTaskList;
import com.dream.pay.voucher.common.DayCutTaskStatus;
import com.dream.pay.voucher.dao.VoucherDayCutJobDao;
import com.dream.pay.voucher.model.VoucherDayCutJobEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 */
@Slf4j
@Component
public class DayCutTaskController<T> {
    @Resource
    VoucherDayCutJobDao voucherDayCutJobDao;

    public void execute(DayCutTaskCallback<T> task, String accountingDate, int taskId, String taskName, boolean isRetry) {
        long startTime = System.currentTimeMillis();
        try {
            log.info("[{}]-[{}]开始执行", accountingDate, taskName);

            if (isRetry) {
                String desc = task.execute();//重试执行 不记录操作日志表
                log.info("[{}]-[{}] 重试执行 正常结束:[{}],耗时[{}]ms", accountingDate, taskName, desc, (System.currentTimeMillis() - startTime));
                return;
            }
            //1.检查任务是否能被执行
            Boolean bool = checkTaskCanBeExecute(accountingDate, taskId, taskName);
            if (bool) {
                //2.创建日切任务执行记录
                this.createDayCutLog(accountingDate, taskId);
                //3.任务执行
                String desc = task.execute();
                //4.执行成功
                this.taskSuccess(accountingDate, taskId, desc);
                log.info("[{}]-[{}]正常结束:[{}],耗时[{}]ms", accountingDate, taskName, desc, (System.currentTimeMillis() - startTime));

            } else {
                log.warn("[{}]-[{}]有关联任务未完成,该任务不允许执行", accountingDate, taskName);

            }
        } catch (Exception e) {
            //执行失败
            this.taskFail(accountingDate, taskId, e.getMessage());
            log.error("[{}]-[{}]执行未知异常,耗时[{}]ms", accountingDate, taskName, (System.currentTimeMillis() - startTime), e);
        }
    }

    /**
     * 检查任务是否能被执行
     *
     * @param accountingDate
     * @param taskId
     * @param taskName
     * @return Boolean
     */
    private Boolean checkTaskCanBeExecute(String accountingDate, int taskId, String taskName) {
        VoucherDayCutJobEntity currentDayCutJobEntity = voucherDayCutJobDao.select(accountingDate, taskId);
        if (null != currentDayCutJobEntity) {
            log.error("[{}]-[{}]已经存在,不能重复执行", accountingDate, taskName);
            return Boolean.FALSE;
        }
        //START_DAY_CUT_TASK任务除外其他任务,如果START_DAY_CUT_TASK任务未执行成功,其他任务不允许执行
        if (DayCutTaskList.START_DAY_CUT_TASK.getId() != taskId) {
            VoucherDayCutJobEntity startDayCutJobEntity = voucherDayCutJobDao.select(accountingDate, DayCutTaskList.START_DAY_CUT_TASK.getId());
            if (DayCutTaskStatus.DAY_CUT_SUCCESS.getId() != startDayCutJobEntity.getTaskExecuteState())
                return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 创建日切任务执行记录
     *
     * @param accountingDate
     * @param taskId
     */
    private void createDayCutLog(String accountingDate, int taskId) {
        VoucherDayCutJobEntity voucherDayCutJobEntity = new VoucherDayCutJobEntity();
        voucherDayCutJobEntity.setTaskId(taskId);
        voucherDayCutJobEntity.setAccountingDate(accountingDate);
        voucherDayCutJobEntity.setStartTime(new Date());
        voucherDayCutJobEntity.setCreateTime(new Date());
        voucherDayCutJobEntity.setTaskExecuteState(DayCutTaskStatus.DAY_CUT_ING.getId());
        voucherDayCutJobEntity.setTaskExecuteOpertor("SYS");
        voucherDayCutJobDao.insert(voucherDayCutJobEntity);
    }

    /**
     * 任务成功
     *
     * @param accountingDate
     * @param taskId
     */
    private void taskSuccess(String accountingDate, int taskId, String desc) {
        voucherDayCutJobDao.UpdateDayCutState(accountingDate, taskId, desc, DayCutTaskStatus.DAY_CUT_SUCCESS.getId());
    }

    /**
     * 任务失败
     *
     * @param accountingDate
     * @param taskId
     */
    private void taskFail(String accountingDate, int taskId, String desc) {
        voucherDayCutJobDao.UpdateDayCutState(accountingDate, taskId, desc, DayCutTaskStatus.DAY_CUT_FAIL.getId());
    }
}
