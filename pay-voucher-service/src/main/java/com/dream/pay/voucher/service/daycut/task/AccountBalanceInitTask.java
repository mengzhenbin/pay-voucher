package com.dream.pay.voucher.service.daycut.task;


import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.common.utils.AccountingDateUtil;
import com.dream.pay.voucher.model.VoucherSubjectSummaryDO;
import com.dream.pay.voucher.repository.SubjectSummaryRepository;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分户期初余额更新任务(当天期末余额更新到下一天作为期初余额)
 *
 * @author mengzhenbin
 * @since 2017/2/1
 */
@Slf4j
@Component
public class AccountBalanceInitTask implements DayCutTask {

    @Resource
    SubjectSummaryRepository subjectSummaryRepository;
    @Resource
    DayCutTaskController dayCutTaskController;


    /**
     * 任务ID
     */
    private static final int TASK_ID = DayCutTaskList.ACCOUNT_BALANCE_INIT_TASK.getId();

    /**
     * 任务名称
     */
    private static final String TASK_NAME = DayCutTaskList.ACCOUNT_BALANCE_INIT_TASK.getName();


    /**
     * 分页读取最大数据量
     **/
    private static final int PAGE_SIZE = 200;


    @Override
    public void execute(String voucherDay, boolean isRetry) {
        dayCutTaskController.execute(() -> {
            //获取最大记录ID
            long maxId = subjectSummaryRepository.selectMaxId(voucherDay);
            int pageCount = (int) (maxId / PAGE_SIZE);
            if (maxId % PAGE_SIZE != 0) {
                pageCount = pageCount + 1;
            }
            //分页读取
            long startRow = maxId - PAGE_SIZE <= 0 ? 0 : (maxId - PAGE_SIZE) + 1;
            long endRow = maxId;
            List<VoucherSubjectSummaryDO> list;
            for (int i = 0; i < pageCount; ++i) {
                list = subjectSummaryRepository.selectByVoucherDate(voucherDay, startRow, endRow);

                if (list.size() == 0) {
                    break;
                }
                if (i != pageCount - 1) {
                    startRow = startRow - PAGE_SIZE <= 0 ? 1 : startRow - PAGE_SIZE;
                    endRow -= PAGE_SIZE;
                }

                for (VoucherSubjectSummaryDO voucherSubjectSummaryDO : list) {
                    try {
                        subjectSummaryRepository.insert(bulidSubSummaryParams(voucherSubjectSummaryDO, voucherDay));
                    } catch (DuplicateKeyException e) {
                        //说明日切没有做完就有新交易入库
                        log.warn("日切发生时账户[{}] 已经有新入账记录,会计日[{}]", voucherSubjectSummaryDO.getAccountNo(), voucherDay);
                        subjectSummaryRepository.updateBeginBalance(AccountingDateUtil.getNextDay(voucherDay), voucherSubjectSummaryDO.getAccountNo(), voucherSubjectSummaryDO.getEndBalance());
                    }
                }
            }
            return "分户期初余额更新完毕";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);

    }

    /**
     * 构建 SubSummaryParams参数
     *
     * @param srcSubjectSummaryDO
     * @param voucherDate
     * @return VoucherSubjectSummaryDO
     */
    private VoucherSubjectSummaryDO bulidSubSummaryParams(VoucherSubjectSummaryDO srcSubjectSummaryDO, String voucherDate) {
        VoucherSubjectSummaryDO descSubjectSummaryParams = new VoucherSubjectSummaryDO();

        descSubjectSummaryParams.setAccountNo(srcSubjectSummaryDO.getAccountNo());
        descSubjectSummaryParams.setCurrency(srcSubjectSummaryDO.getCurrency());
        descSubjectSummaryParams.setSubjectCode(srcSubjectSummaryDO.getSubjectCode());
        descSubjectSummaryParams.setVoucherDate(AccountingDateUtil.getNextDay(voucherDate));
        descSubjectSummaryParams.setInnerFlag(srcSubjectSummaryDO.getInnerFlag());
        descSubjectSummaryParams.setAmount(0L);
        //上一日期初等于本日期末余额
        descSubjectSummaryParams.setBeginBalance(srcSubjectSummaryDO.getEndBalance());
        descSubjectSummaryParams.setSerialNo(srcSubjectSummaryDO.getSerialNo());
        //账户  期末余额是汇总出来的
        descSubjectSummaryParams.setEndBalance(0L);
        return descSubjectSummaryParams;
    }
}
