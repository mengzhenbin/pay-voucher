package com.dream.pay.voucher.service.daycut.task;


import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.common.enums.RecordDir;
import com.dream.pay.voucher.dao.VoucherSubjectItemDao;
import com.dream.pay.voucher.dao.VoucherSubjectRecordDao;
import com.dream.pay.voucher.dao.VoucherSubjectSummaryDao;
import com.dream.pay.voucher.model.VoucherSubjectItemEntity;
import com.dream.pay.voucher.model.VoucherSubjectRecordEntity;
import com.dream.pay.voucher.model.VoucherSubjectSummaryEntity;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 内部户科目余额,发生额汇总
 *
 * @author hechengqi  17/2/1
 */
@Slf4j
@Component
public class AccountBalanceSummaryTask implements DayCutTask {
    @Resource
    VoucherSubjectSummaryDao voucherSubjectSummaryDao;
    @Resource
    VoucherSubjectRecordDao voucherSubjectRecordDao;
    @Resource
    DayCutTaskController dayCutTaskController;
    @Resource
    VoucherSubjectItemDao voucherSubjectItemDao;

    /**
     * 任务ID
     */
    private static final int TASK_ID = DayCutTaskList.INNER_ACCT_BALANCE_SUMMARY_TASK.getId();

    /**
     * 任务名称
     */
    private static final String TASK_NAME = DayCutTaskList.INNER_ACCT_BALANCE_SUMMARY_TASK.getName();

    /**
     * 分页读取最大数据量
     **/
    private static final int PAGE_SIZE = 200;

    @Override
    public void execute(String voucherDay, boolean isRetry) {
        dayCutTaskController.execute(() -> {
            //分页处理
            long maxId = voucherSubjectSummaryDao.selectMaxId(voucherDay);
            long minId = voucherSubjectSummaryDao.selectMinId(voucherDay);
            int pageCount = (maxId == minId) ? 1 : (int) ((maxId - minId) / PAGE_SIZE);
            if ((maxId - minId) % PAGE_SIZE != 0) {
                pageCount = pageCount + 1;
            }
            //分页读取
            long startRow = maxId - PAGE_SIZE <= minId ? minId : (maxId - PAGE_SIZE) + 1;
            long sumaryNum = 0;
            long endRow = maxId;
            log.info("分户账更新-会计日{}分户账统计，maxId={}，minId={}", voucherDay, maxId, minId);
            for (int i = 0; i < pageCount; ++i) {
                List<VoucherSubjectSummaryEntity> list = voucherSubjectSummaryDao.selectByVoucherDate(voucherDay, startRow, endRow);
                log.info("分户账更新-查询分户账结束,voucherDay={},startRow={},endRow={},返回结果数目={}",
                        voucherDay, startRow, endRow, list.size());

                //如果查询到前一会计日数据,则结束循环
                if (CollectionUtils.isEmpty(list)) {
                    continue;
                }
                if (i != pageCount - 1) {//从尾部到头部的查询
                    startRow = startRow - PAGE_SIZE <= minId ? minId : startRow - PAGE_SIZE;
                    endRow -= PAGE_SIZE;
                }
                for (VoucherSubjectSummaryEntity voucherSubjectSummaryEntity : list) {
                    try {
                        sumaryNum = sumaryNum + recordSumaryTOAccout(voucherSubjectSummaryEntity, voucherDay);
                    } catch (Exception e) {
                        log.error("账号[{}]汇总出现异常,subSummaryDO={}", voucherSubjectSummaryEntity.getAccountNo(), voucherSubjectSummaryEntity, e);
                    }
                }
            }
            log.info("分户汇总,会计日{} 共计[{}]条记录", voucherDay, sumaryNum);
            return " 分户户期末余额汇总完成";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);
    }

    /**
     * 根据 分录汇总分户账 借方贷方发生额，发生笔数、发生额和期末余额
     *
     * @param voucherSubjectSummaryEntity
     * @param voucherDay
     */
    private long recordSumaryTOAccout(VoucherSubjectSummaryEntity voucherSubjectSummaryEntity, String voucherDay) {

        long startTime = System.currentTimeMillis();
        //获取最大记录ID
        long num = voucherSubjectRecordDao.countSubjectRecordByVoucherDayAndAcctNo(voucherDay, voucherSubjectSummaryEntity.getAccountNo());
        long pageCount = (int) ((num) / PAGE_SIZE);
        if (num % PAGE_SIZE != 0) {
            pageCount = pageCount + 1;
        }

        long amount = 0L;
        voucherSubjectSummaryEntity.setDebitAmount(0L);
        voucherSubjectSummaryEntity.setCreditAmount(0L);
        voucherSubjectSummaryEntity.setCreditCount(0L);
        voucherSubjectSummaryEntity.setDebitCount(0L);
        voucherSubjectSummaryEntity.setEndBalance(0L);
        log.info("分户账更新-查询会计分录maxId,voucherDay={},accountNo={},num={}", voucherDay, voucherSubjectSummaryEntity.getAccountNo(), num);
        long startSize = 0;
        long pageSize = num > PAGE_SIZE ? PAGE_SIZE : num;
        for (int i = 0; i < pageCount; ++i) {
            if (i == pageCount - 1) {//从头部的查询
                pageSize = num - startSize;
            }
            long time1 = System.currentTimeMillis();
            List<VoucherSubjectRecordEntity> voucherSubjectRecords = voucherSubjectRecordDao.selectByVoucherDayAndAccountNo(voucherDay, voucherSubjectSummaryEntity.getAccountNo(), startSize, pageSize);

            //汇总 借方发生额 贷方发生额
            for (VoucherSubjectRecordEntity voucherSubjectRecordEntity : voucherSubjectRecords) {
                //借方余额,贷方余额
                if (voucherSubjectRecordEntity.getRecordDir().equals(RecordDir.D.getId())) {
                    voucherSubjectSummaryEntity.setDebitCount(voucherSubjectSummaryEntity.getDebitCount() + 1);
                    voucherSubjectSummaryEntity.setDebitAmount(voucherSubjectSummaryEntity.getDebitAmount() + voucherSubjectRecordEntity.getAmount());
                } else {
                    voucherSubjectSummaryEntity.setCreditCount(voucherSubjectSummaryEntity.getCreditCount() + 1);
                    voucherSubjectSummaryEntity.setCreditAmount(voucherSubjectSummaryEntity.getCreditAmount() + voucherSubjectRecordEntity.getAmount());
                }

            }
            log.info("分户账更新-查询会计分录列表结束,voucherDay={},acctNo={},startSize={},pageSize={},返回结果数目={},单次查询汇总耗时[{}]ms",
                    voucherDay, voucherSubjectSummaryEntity.getAccountNo(), startSize, pageSize, voucherSubjectRecords.size(), (System.currentTimeMillis() - time1));

            startSize = startSize + pageSize;
        }
        ////计算内部户发生额
        VoucherSubjectItemEntity voucherSubjectItemEntity = voucherSubjectItemDao.selectByCode(voucherSubjectSummaryEntity.getSubjectCode());
        if (null == voucherSubjectItemEntity || StringUtils.isEmpty(voucherSubjectItemEntity.getBalanceDir())) {
            log.error("根据科目代码[{}]未获取到正确的科目余额方向", voucherSubjectItemEntity, voucherSubjectSummaryEntity.getSubjectCode());
        } else {
            if (RecordDir.D.getId().equals(voucherSubjectItemEntity.getBalanceDir())) {
                amount = voucherSubjectSummaryEntity.getDebitAmount() - voucherSubjectSummaryEntity.getCreditAmount();
            } else {
                amount = voucherSubjectSummaryEntity.getCreditAmount() - voucherSubjectSummaryEntity.getDebitAmount();
            }
        }
        voucherSubjectSummaryEntity.setAmount(amount);
        //计算内部户期末余额
        voucherSubjectSummaryEntity.setEndBalance(voucherSubjectSummaryEntity.getBeginBalance() + amount);
        voucherSubjectSummaryEntity.setUpdateTime(new Date());
        log.info("recordSumaryTOAccout,voucherDay={},accountNo={},subSummaryParams={},耗时[{}]ms", voucherDay, voucherSubjectSummaryEntity.getAccountNo(), voucherSubjectSummaryEntity, (System.currentTimeMillis() - startTime));

        voucherSubjectSummaryDao.updateByPrimaryKey(voucherSubjectSummaryEntity);
        //subSummaryRepositoryImpl.update(subSummaryParams);
        return num;
    }
}