package com.dream.pay.voucher.service.daycut.task;


import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.common.enums.RecordDir;
import com.dream.pay.voucher.model.VoucherSubjectItemDO;
import com.dream.pay.voucher.model.VoucherSubjectRecordDO;
import com.dream.pay.voucher.model.VoucherSubjectSummaryDO;
import com.dream.pay.voucher.repository.SubjectItemRepository;
import com.dream.pay.voucher.repository.SubjectRecordRepository;
import com.dream.pay.voucher.repository.SubjectSummaryRepository;
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
    DayCutTaskController dayCutTaskController;

    @Resource
    SubjectSummaryRepository subjectSummaryRepository;
    @Resource
    SubjectRecordRepository subjectRecordRepositoryImpl;
    @Resource
    SubjectItemRepository subjectItemRepository;

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
            Long maxId = subjectSummaryRepository.selectMaxId(voucherDay);
            maxId = maxId == null ? 0 : maxId;
            Long minId = subjectSummaryRepository.selectMinId(voucherDay);
            minId = minId == null ? 0 : minId;
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
                List<VoucherSubjectSummaryDO> list = subjectSummaryRepository.selectByVoucherDate(voucherDay, startRow, endRow);
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
                for (VoucherSubjectSummaryDO voucherSubjectSummaryDO : list) {
                    try {
                        sumaryNum = sumaryNum + recordSumaryTOAccout(voucherSubjectSummaryDO, voucherDay);
                    } catch (Exception e) {
                        log.error("账号[{}]汇总出现异常,subSummaryDO={}", voucherSubjectSummaryDO.getAccountNo(), voucherSubjectSummaryDO, e);
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
     * @param voucherSubjectSummaryDO
     * @param voucherDay
     */
    private long recordSumaryTOAccout(VoucherSubjectSummaryDO voucherSubjectSummaryDO, String voucherDay) {

        long startTime = System.currentTimeMillis();
        //获取最大记录ID
        Long num = subjectRecordRepositoryImpl.countByVoucherDateAndAccountNo(voucherDay, voucherSubjectSummaryDO.getAccountNo());
        num = num == null ? 0 : num;
        long pageCount = (int) ((num) / PAGE_SIZE);
        if (num % PAGE_SIZE != 0) {
            pageCount = pageCount + 1;
        }

        long amount = 0L;
        voucherSubjectSummaryDO.setDebitAmount(0L);
        voucherSubjectSummaryDO.setCreditAmount(0L);
        voucherSubjectSummaryDO.setCreditCount(0L);
        voucherSubjectSummaryDO.setDebitCount(0L);
        voucherSubjectSummaryDO.setEndBalance(0L);
        log.info("分户账更新-查询会计分录maxId,voucherDay={},accountNo={},num={}", voucherDay, voucherSubjectSummaryDO.getAccountNo(), num);
        long startSize = 0;
        long pageSize = num > PAGE_SIZE ? PAGE_SIZE : num;
        for (int i = 0; i < pageCount; ++i) {
            if (i == pageCount - 1) {//从头部的查询
                pageSize = num - startSize;
            }
            long time1 = System.currentTimeMillis();
            List<VoucherSubjectRecordDO> voucherSubjectRecords = subjectRecordRepositoryImpl.selectByVoucherDateAndAccountNo(voucherDay, voucherSubjectSummaryDO.getAccountNo(), startSize, pageSize);

            //汇总 借方发生额 贷方发生额
            for (VoucherSubjectRecordDO voucherSubjectRecordDO : voucherSubjectRecords) {
                //借方余额,贷方余额
                if (voucherSubjectRecordDO.getRecordDir().equals(RecordDir.D.getId())) {
                    voucherSubjectSummaryDO.setDebitCount(voucherSubjectSummaryDO.getDebitCount() + 1);
                    voucherSubjectSummaryDO.setDebitAmount(voucherSubjectSummaryDO.getDebitAmount() + voucherSubjectRecordDO.getAmount());
                } else {
                    voucherSubjectSummaryDO.setCreditCount(voucherSubjectSummaryDO.getCreditCount() + 1);
                    voucherSubjectSummaryDO.setCreditAmount(voucherSubjectSummaryDO.getCreditAmount() + voucherSubjectRecordDO.getAmount());
                }

            }
            log.info("分户账更新-查询会计分录列表结束,voucherDay={},acctNo={},startSize={},pageSize={},返回结果数目={},单次查询汇总耗时[{}]ms",
                    voucherDay, voucherSubjectSummaryDO.getAccountNo(), startSize, pageSize, voucherSubjectRecords.size(), (System.currentTimeMillis() - time1));

            startSize = startSize + pageSize;
        }
        ////计算内部户发生额
        VoucherSubjectItemDO voucherSubjectItemDO = subjectItemRepository.selectByCode(voucherSubjectSummaryDO.getSubjectCode());
        if (null == voucherSubjectItemDO || StringUtils.isEmpty(voucherSubjectItemDO.getBalanceDir())) {
            log.error("根据科目代码[{}]未获取到正确的科目余额方向", voucherSubjectItemDO, voucherSubjectSummaryDO.getSubjectCode());
        } else {
            if (RecordDir.D.getId().equals(voucherSubjectItemDO.getBalanceDir())) {
                amount = voucherSubjectSummaryDO.getDebitAmount() - voucherSubjectSummaryDO.getCreditAmount();
            } else {
                amount = voucherSubjectSummaryDO.getCreditAmount() - voucherSubjectSummaryDO.getDebitAmount();
            }
        }
        voucherSubjectSummaryDO.setAmount(amount);
        //计算内部户期末余额
        voucherSubjectSummaryDO.setEndBalance(voucherSubjectSummaryDO.getBeginBalance() + amount);
        voucherSubjectSummaryDO.setUpdateTime(new Date());
        log.info("recordSumaryTOAccout,voucherDay={},accountNo={},subSummaryParams={},耗时[{}]ms", voucherDay, voucherSubjectSummaryDO.getAccountNo(), voucherSubjectSummaryDO, (System.currentTimeMillis() - startTime));

        subjectSummaryRepository.update(voucherSubjectSummaryDO);
        return num;
    }
}