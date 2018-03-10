package com.dream.pay.voucher.service.daycut.task;


import com.dream.pay.utils.BeanUtil;
import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.common.utils.AccountingDateUtil;
import com.dream.pay.voucher.model.VoucherTotalSummaryDO;
import com.dream.pay.voucher.repository.TotalSummaryRepository;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 科目期初余额更新
 *
 * @author hechengqi  17/2/1
 */
@Slf4j
@Component
public class SubjectBalanceInitTask implements DayCutTask {
    @Resource
    TotalSummaryRepository totalSummaryRepositoryImpl;
    @Resource
    DayCutTaskController dayCutTaskController;

    /**
     * 任务ID
     */
    private static final int TASK_ID = DayCutTaskList.SUBJECT_BALANCE_INIT_TASK.getId();

    /**
     * 任务名称
     */
    private static final String TASK_NAME = DayCutTaskList.SUBJECT_BALANCE_INIT_TASK.getName();


    @Override
    public void execute(String voucherDay, boolean isRetry) {
        dayCutTaskController.execute(() -> {

            List<VoucherTotalSummaryDO> doList = totalSummaryRepositoryImpl.selectByVoucherDate(voucherDay);

            for (VoucherTotalSummaryDO voucherTotalSummaryDO : doList) {
                try {
                    totalSummaryRepositoryImpl.insert(bulidVoucherTotalSummaryDO(voucherTotalSummaryDO, voucherDay));
                } catch (DuplicateKeyException e) {
                    //说明日切没有做完就有新交易入库
                    log.warn("日切发生时 已经有新入账记录,会计日[{}],科目号[{}]", voucherDay, voucherTotalSummaryDO.getSubjectCode());
                    totalSummaryRepositoryImpl.updateBeginBalance(AccountingDateUtil.getNextDay(voucherDay), voucherTotalSummaryDO.getSubjectCode(), voucherTotalSummaryDO.getEndBalance());
                }
            }

            return "科目期初余额更新完毕";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);


    }

    private VoucherTotalSummaryDO bulidVoucherTotalSummaryDO(VoucherTotalSummaryDO srcVoucherTotalSummaryDO, String voucherDate) {
        VoucherTotalSummaryDO descVoucherTotalSummaryDO = BeanUtil.convert(srcVoucherTotalSummaryDO, VoucherTotalSummaryDO.class);
        descVoucherTotalSummaryDO.setVoucherDate(AccountingDateUtil.getNextDay(voucherDate));
        descVoucherTotalSummaryDO.setBeginBalance(srcVoucherTotalSummaryDO.getEndBalance());
        descVoucherTotalSummaryDO.setEndBalance(0L);
        descVoucherTotalSummaryDO.setDebitAmount(0L);
        descVoucherTotalSummaryDO.setCreditAmount(0L);
        descVoucherTotalSummaryDO.setCreditCount(0L);
        descVoucherTotalSummaryDO.setDebitCount(0L);
        descVoucherTotalSummaryDO.setZeroFlag(0);
        return descVoucherTotalSummaryDO;
    }
}