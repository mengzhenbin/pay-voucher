package com.dream.pay.voucher.service.daycut.task;

import com.dream.pay.utils.BeanUtil;
import com.dream.pay.voucher.common.enums.DayCutTaskList;
import com.dream.pay.voucher.model.VoucherSubjectItemDO;
import com.dream.pay.voucher.model.VoucherSubjectSummaryDO;
import com.dream.pay.voucher.model.VoucherTotalSummaryDO;
import com.dream.pay.voucher.repository.SubjectItemRepository;
import com.dream.pay.voucher.repository.SubjectSummaryRepository;
import com.dream.pay.voucher.repository.TotalSummaryRepository;
import com.dream.pay.voucher.service.daycut.core.DayCutTask;
import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
import com.dream.pay.voucher.utils.DebitCreditSumUtils;
import com.youzan.platform.util.lang.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科目余额,发生额汇总
 * 根据科目下的每个账户发生额进行汇总
 *
 * @author mengzhenbin
 * @since 2017/2/1
 */
@Slf4j
@Component
public class SubjectAmountSummaryTask implements DayCutTask {
    @Resource
    TotalSummaryRepository totalSummaryRepositoryImpl;
    @Resource
    SubjectSummaryRepository subjectSummaryRepository;
    @Resource
    DayCutTaskController dayCutTaskController;

    @Resource
    SubjectItemRepository subjectItemRepository;

    /**
     * 任务ID
     */
    private static final int TASK_ID = DayCutTaskList.SUBJECT_AMOUNT_SUMMARY_TASK.getId();

    /**
     * 任务名称
     */
    private static final String TASK_NAME = DayCutTaskList.SUBJECT_AMOUNT_SUMMARY_TASK.getName();

    /**
     * 分页读取最大数据量
     **/
    private static final int PAGE_SIZE = 200;

    @Override
    public void execute(String voucherDay, boolean isRetry) {
        dayCutTaskController.execute(() -> {
            //汇总末级科目余额
            List<VoucherTotalSummaryDO> resultTotalSummaryDO = totalSubSubjectAmountSummary(voucherDay);
            do {
                resultTotalSummaryDO = sumSubjectSumaryList(voucherDay, resultTotalSummaryDO);

            } while (resultTotalSummaryDO != null && (!resultTotalSummaryDO.isEmpty()));
            return " 科目余额,发生额汇总完成";
        }, voucherDay, TASK_ID, TASK_NAME, isRetry);

    }

    /**
     * 汇总末级科目余额
     *
     * @param voucherDay
     * @return
     */
    private List<VoucherTotalSummaryDO> totalSubSubjectAmountSummary(String voucherDay) {
        //逐级汇总 从三级科目开始
        List<VoucherTotalSummaryDO> doList = totalSummaryRepositoryImpl.selectByVoucherDateDateAndSubjectLevel(voucherDay, 3);
        List<VoucherTotalSummaryDO> resultList = new ArrayList<>();
        for (VoucherTotalSummaryDO voucherTotalSummaryDO : doList) {
            VoucherTotalSummaryDO resultDO = BeanUtil.convert(voucherTotalSummaryDO, VoucherTotalSummaryDO.class);
            resultDO.setDebitAmount(0l);
            resultDO.setCreditAmount(0l);
            resultDO.setDebitCount(0l);
            resultDO.setCreditCount(0l);
            resultDO.setEndBalance(0l);
            //获取最大记录ID
            long maxId = subjectSummaryRepository.selectMaxIdBySubjectCode(voucherDay, voucherTotalSummaryDO.getSubjectCode());
            long minId = subjectSummaryRepository.selectMinIdBySubjectCode(voucherDay, voucherTotalSummaryDO.getSubjectCode());
            int pageCount = (maxId == minId) ? 1 : (int) ((maxId - minId) / PAGE_SIZE);
            if ((maxId - minId) % PAGE_SIZE != 0) {
                pageCount = pageCount + 1;
            }
            //分页读取
            long startRow = maxId - PAGE_SIZE <= minId ? minId : (maxId - PAGE_SIZE) + 1;
            long endRow = maxId;
            int dateNum = 0;
            for (int i = 0; i < pageCount; ++i) {
                List<VoucherSubjectSummaryDO> list = subjectSummaryRepository.selectByVoucherDateAndSubjectCode(voucherDay, voucherTotalSummaryDO.getSubjectCode(), startRow, endRow);
                //循环结束
                if (CollectionUtils.isEmpty(list)) {
                    continue;
                }
                if (i != pageCount - 1) {//倒数方式计算分页
                    startRow = startRow - PAGE_SIZE <= minId ? minId : startRow - PAGE_SIZE;
                    endRow -= PAGE_SIZE;
                }
                //汇总同一科目下所有账户下交易记录汇总
                DebitCreditSumUtils.sumSubjectSummaryDOAmountAndTimes(list, resultDO);
                dateNum = dateNum + list.size();
            }
            //更新数据
            totalSummaryRepositoryImpl.updateBalance(resultDO);
            log.info("汇总科目账数据,voucherDate={},subjectCode={},maxId={},minId={},共汇总记录 {} 条", voucherDay, voucherTotalSummaryDO.getSubjectCode(), maxId, minId, dateNum);
            resultList.add(resultDO);
        }
        return resultList;
    }

    /**
     * 科目余额汇总更新
     *
     * @param voucherDate               科目日期
     * @param voucherTotalSummaryDOList 下级科目汇总信息
     * @return
     */
    private List<VoucherTotalSummaryDO> sumSubjectSumaryList(String voucherDate, List<VoucherTotalSummaryDO> voucherTotalSummaryDOList) {

        //获取当前科目下级科目汇总记录列表
        Map<String, List<VoucherTotalSummaryDO>> tempSummaryMap = groupListByParentSubjectCode(voucherTotalSummaryDOList);

        if (tempSummaryMap == null || tempSummaryMap.isEmpty()) {
            return null;
        }

        List<VoucherTotalSummaryDO> resultList = new ArrayList<>();

        for (String subjectCode : tempSummaryMap.keySet()) {
            List<VoucherTotalSummaryDO> subLevelTotalSummaryList = tempSummaryMap.get(subjectCode);

            VoucherTotalSummaryDO resultVoucherTotalSummaryDO;
            //更新或者插入当前科目借贷汇总金额和笔数
            VoucherTotalSummaryDO dbVoucherTotalSummaryDO = totalSummaryRepositoryImpl.selectByVoucherDateDateAndSubjectCode(voucherDate, subjectCode);

            if (dbVoucherTotalSummaryDO == null) {
                resultVoucherTotalSummaryDO = bulidParentTotalSummaryDO(subjectCode, subLevelTotalSummaryList.get(0));
                //汇总计算下级科目金额和笔数
                DebitCreditSumUtils.sumSubjectAmountAndTimes(resultVoucherTotalSummaryDO, subLevelTotalSummaryList);
                totalSummaryRepositoryImpl.insert(resultVoucherTotalSummaryDO);
            } else {
                resultVoucherTotalSummaryDO = BeanUtil.convert(dbVoucherTotalSummaryDO, VoucherTotalSummaryDO.class);
                resultVoucherTotalSummaryDO.setDebitCount(0L);
                resultVoucherTotalSummaryDO.setDebitAmount(0L);
                resultVoucherTotalSummaryDO.setCreditCount(0L);
                resultVoucherTotalSummaryDO.setCreditAmount(0L);
                resultVoucherTotalSummaryDO.setEndBalance(0L);
                //汇总计算下级科目金额和笔数
                DebitCreditSumUtils.sumSubjectAmountAndTimes(resultVoucherTotalSummaryDO, subLevelTotalSummaryList);
                totalSummaryRepositoryImpl.updateBalance(resultVoucherTotalSummaryDO);
            }
            log.info("汇总科目账数据,voucherDate={},subjectCode={} ,共汇总记录 {} 条", voucherDate, subjectCode, subLevelTotalSummaryList.size());

            resultList.add(resultVoucherTotalSummaryDO);
        }
        return resultList;
    }

    /**
     * ListToMap 按照按照上级科目代码进行分组转换
     *
     * @param voucherTotalSummaryDOList
     * @return Map<String, List<VoucherTotalSummaryDO>>
     */
    private Map<String, List<VoucherTotalSummaryDO>> groupListByParentSubjectCode(List<VoucherTotalSummaryDO> voucherTotalSummaryDOList) {
        Map<String, List<VoucherTotalSummaryDO>> resultMap = new HashMap<>();
        for (VoucherTotalSummaryDO current : voucherTotalSummaryDOList) {
            if (StringUtil.isEmpty(current.getParentSubjectCode())) {
                continue;
            }
            List<VoucherTotalSummaryDO> middleTotalSummaryDOList = resultMap.get(current.getParentSubjectCode());
            if (middleTotalSummaryDOList == null) {
                List<VoucherTotalSummaryDO> summaryParamsList = new ArrayList<>();
                summaryParamsList.add(current);
                resultMap.put(current.getParentSubjectCode(), summaryParamsList);
            } else {
                middleTotalSummaryDOList.add(current);
            }
        }
        return resultMap;
    }


    /**
     * 根据子类科目汇总信息构建父类科目汇总信息
     *
     * @param subjectCode
     * @param voucherTotalSummaryDO
     * @return VoucherTotalSummaryDO
     */
    private VoucherTotalSummaryDO bulidParentTotalSummaryDO(String subjectCode, VoucherTotalSummaryDO voucherTotalSummaryDO) {
        VoucherTotalSummaryDO parentVoucherTotalSummaryDO = BeanUtil.convert(voucherTotalSummaryDO, VoucherTotalSummaryDO.class);
        parentVoucherTotalSummaryDO.setDebitCount(0L);
        parentVoucherTotalSummaryDO.setDebitAmount(0L);
        parentVoucherTotalSummaryDO.setCreditCount(0L);
        parentVoucherTotalSummaryDO.setCreditAmount(0L);
        parentVoucherTotalSummaryDO.setEndBalance(0L);
        parentVoucherTotalSummaryDO.setSubjectCode(subjectCode);

        VoucherSubjectItemDO subjectItemsDO = subjectItemRepository.selectByCode(subjectCode);
        parentVoucherTotalSummaryDO.setSubjectLevel(subjectItemsDO.getSubjectLevel());
        parentVoucherTotalSummaryDO.setSubjectName(subjectItemsDO.getSubjectName());
        parentVoucherTotalSummaryDO.setBalanceDir(subjectItemsDO.getBalanceDir());
        parentVoucherTotalSummaryDO.setParentSubjectCode(subjectItemsDO.getParentsCode());

        return parentVoucherTotalSummaryDO;
    }


}
