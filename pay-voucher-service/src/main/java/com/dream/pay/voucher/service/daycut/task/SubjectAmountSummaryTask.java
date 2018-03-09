//package com.dream.pay.voucher.service.daycut.task;
//
//import com.dream.pay.voucher.common.enums.DayCutTaskList;
//import com.dream.pay.voucher.dao.VoucherSubjectItemDao;
//import com.dream.pay.voucher.service.daycut.core.DayCutTask;
//import com.dream.pay.voucher.service.daycut.core.DayCutTaskController;
//import com.youzan.platform.util.lang.StringUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * 科目余额,发生额汇总
// * 根据科目下的每个账户发生额进行汇总
// *
// * @author mengzhenbin  17/2/1
// */
//@Slf4j
//@Component
//public class SubjectAmountSummaryTask implements DayCutTask {
//    @Resource
//    TotalSummaryRepository totalSummaryRepositoryImpl;
//    @Resource
//    SubSummaryRepository subSummaryRepositoryImpl;
//    @Resource
//    DayCutTaskController dayCutTaskController;
//
//    @Resource
//    VoucherSubjectItemDao voucherSubjectItemDao;
//
//    /**
//     * 任务ID
//     */
//    private static final int TASK_ID = DayCutTaskList.SUBJECT_AMOUNT_SUMMARY_TASK.getId();
//
//    /**
//     * 任务名称
//     */
//    private static final String TASK_NAME = DayCutTaskList.SUBJECT_AMOUNT_SUMMARY_TASK.getName();
//
//    /**
//     * 分页读取最大数据量
//     **/
//    @Value("${db_read_max_size}")
//    private int PAGE_SIZE;
//
//    @Override
//    public void execute(String accountingDate, boolean isRetry) {
//        dayCutTaskController.execute(() -> {
//            //汇总末级科目余额
//            List<TotalSummaryParams> totalSummaryParams = totailSubSubjectAmountSummary(accountingDate);
//            do {
//                totalSummaryParams = sumSubjectSumaryList(accountingDate, totalSummaryParams);
//            } while (totalSummaryParams != null && (!totalSummaryParams.isEmpty()));
//            return " 科目余额,发生额汇总完成";
//        }, accountingDate, TASK_ID, TASK_NAME, isRetry);
//
//    }
//
//
//    /**
//     * 汇总末级科目余额
//     *
//     * @param accountingDate
//     * @return
//     */
//    private List<TotalSummaryParams> totailSubSubjectAmountSummary(String accountingDate) {
//        //逐级汇总 从三级科目开始
//        List<TotalSummaryDO> doList = totalSummaryRepositoryImpl.selectByAccountingDateAndSubjectLevel(accountingDate, 3);
//        List<TotalSummaryParams> totalSummaryList = new ArrayList<TotalSummaryParams>();
//        for (TotalSummaryDO totalSummaryDo : doList) {
//            TotalSummaryParams totalSummaryParams = BeanConvertUtil.convert(TotalSummaryParams.class, totalSummaryDo);
//            totalSummaryParams.setDebitAmount(0l);
//            totalSummaryParams.setCreditAmount(0l);
//            totalSummaryParams.setDebitCount(0l);
//            totalSummaryParams.setCreditCount(0l);
//            totalSummaryParams.setEndBalance(0l);
//            //获取最大记录ID
//            long maxId = subSummaryRepositoryImpl.selectMaxIdBySubject(accountingDate, totalSummaryDo.getSubjectCode());//? 避免用 count?
//            long minId = subSummaryRepositoryImpl.selectMinIdBySubject(accountingDate, totalSummaryDo.getSubjectCode());
//            int pageCount = (maxId == minId) ? 1 : (int) ((maxId - minId) / PAGE_SIZE);
//            if ((maxId - minId) % PAGE_SIZE != 0) {
//                pageCount = pageCount + 1;
//            }
//            //分页读取
//            long startRow = maxId - PAGE_SIZE <= minId ? minId : (maxId - PAGE_SIZE) + 1;
//            long endRow = maxId;
//            int dateNum = 0;
//            for (int i = 0; i < pageCount; ++i) {
//                List<SubSummaryDO> list = subSummaryRepositoryImpl.selectByAccountingDateAndSubject(accountingDate, totalSummaryDo.getSubjectCode(), startRow, endRow);
//                //循环结束
//                if (CollectionUtils.isEmpty(list)) {
//                    continue;
//                }
//                if (i != pageCount - 1) {//倒数方式计算分页
//                    startRow = startRow - PAGE_SIZE <= minId ? minId : startRow - PAGE_SIZE;
//                    endRow -= PAGE_SIZE;
//                }
//                //汇总同一科目下所有账户下交易记录汇总
//                DebtCreditSumUtils.sumSubSummaryDOAmountAndTimes(list, totalSummaryParams);
//                dateNum = dateNum + list.size();
//            }
//            //更新数据
//            totalSummaryRepositoryImpl.updateBalance(totalSummaryParams);
//            LogUtils.info(log, "汇总科目账数据,accountDate={},subjectCode={},maxId={},minId={},共汇总记录 {} 条", accountingDate, totalSummaryDo.getSubjectCode(), maxId, minId, dateNum);
//            totalSummaryList.add(totalSummaryParams);
//        }
//        return totalSummaryList;
//    }
//
//    /**
//     * 科目余额汇总更新
//     *
//     * @param accountingDate            科目日期
//     * @param subTotalSummaryParamsList 下级科目汇总信息
//     * @return
//     */
//    private List<TotalSummaryParams> sumSubjectSumaryList(String accountingDate, List<TotalSummaryParams> subTotalSummaryParamsList) {
//
//        //获取当前科目下级科目汇总记录列表
//        Map<String, List<TotalSummaryParams>> subSummaryMap = groupListByParentSubjectCode(subTotalSummaryParamsList);
//
//        if (subSummaryMap == null || subSummaryMap.isEmpty()) {
//            return null;
//        }
//
//        List<TotalSummaryParams> totalSummaryParamsList = new ArrayList<>();
//
//        for (String subjectCode : subSummaryMap.keySet()) {
//            List<TotalSummaryParams> subLevelTotalSummaryList = subSummaryMap.get(subjectCode);
//
//            TotalSummaryParams totalSummaryParams = null;
//            //更新或者插入当前科目借贷汇总金额和笔数
//            TotalSummaryDO totalSummaryDO = totalSummaryRepositoryImpl.selectByAccountingDateAndSubjectCode(accountingDate, subjectCode);
//
//            if (totalSummaryDO == null) {
//                totalSummaryParams = bulidParentTotalSummaryDO(subjectCode, subLevelTotalSummaryList.get(0));
//                //汇总计算下级科目金额和笔数
//                DebtCreditSumUtils.sumSubjectAmountAndTimes(totalSummaryParams, subLevelTotalSummaryList);
//                totalSummaryRepositoryImpl.insert(totalSummaryParams);
//            } else {
//                totalSummaryParams = BeanConvertUtil.convert(TotalSummaryParams.class, totalSummaryDO);
//                totalSummaryParams.setDebitCount(0);
//                totalSummaryParams.setDebitAmount(0);
//                totalSummaryParams.setCreditCount(0);
//                totalSummaryParams.setCreditAmount(0);
//                totalSummaryParams.setEndBalance(0L);
//                //汇总计算下级科目金额和笔数
//                DebtCreditSumUtils.sumSubjectAmountAndTimes(totalSummaryParams, subLevelTotalSummaryList);
//                totalSummaryRepositoryImpl.updateBalance(totalSummaryParams);
//            }
//            LogUtils.info(log, "汇总科目账数据,accountDate={},subjectCode={} ,共汇总记录 {} 条", accountingDate, subjectCode, subLevelTotalSummaryList.size());
//
//            totalSummaryParamsList.add(totalSummaryParams);
//        }
//        return totalSummaryParamsList;
//    }
//
//    /**
//     * ListToMap 按照按照上级科目代码进行分组转换
//     *
//     * @param totalSummaryParamsList
//     * @return
//     */
//    private Map<String, List<TotalSummaryParams>> groupListByParentSubjectCode(List<TotalSummaryParams> totalSummaryParamsList) {
//        Map<String, List<TotalSummaryParams>> totalSummaryMap = new HashMap<>();
//        for (TotalSummaryParams task : totalSummaryParamsList) {
//            if (StringUtil.isEmpty(task.getParentSubjectCode())) {
//                continue;
//            }
//            List<TotalSummaryParams> totalSummaryParams = totalSummaryMap.get(task.getParentSubjectCode());
//            if (totalSummaryParams == null) {
//                List<TotalSummaryParams> summaryParamsList = new ArrayList<>();
//                summaryParamsList.add(task);
//                totalSummaryMap.put(task.getParentSubjectCode(), summaryParamsList);
//            } else {
//                totalSummaryParams.add(task);
//            }
//        }
//        return totalSummaryMap;
//    }
//
//
//    /**
//     * 根据子类科目汇总信息构建父类科目汇总信息
//     *
//     * @param subjectCode
//     * @param totalSummaryParams
//     * @return
//     */
//    private TotalSummaryParams bulidParentTotalSummaryDO(String subjectCode, TotalSummaryParams totalSummaryParams) {
//        TotalSummaryParams parentTotalSummary = BeanConvertUtil.convert(TotalSummaryParams.class, totalSummaryParams);
//
//        parentTotalSummary.setDebitCount(0);
//        parentTotalSummary.setDebitAmount(0);
//        parentTotalSummary.setCreditCount(0);
//        parentTotalSummary.setCreditAmount(0);
//        parentTotalSummary.setEndBalance(0L);
//
//        parentTotalSummary.setSubjectCode(subjectCode);
//        SubjectItemsDO subjectItemsDO = subjectItemService.getSubjectItemsDO(subjectCode);
//        parentTotalSummary.setSubjectLevel(subjectItemsDO.getLevel());
//        parentTotalSummary.setSubjectName(subjectItemsDO.getName());
//        parentTotalSummary.setBalanceDir(subjectItemsDO.getBalanceDir());
//
//        parentTotalSummary.setParentSubjectCode(subjectItemsDO.getParentsCode());
//        return parentTotalSummary;
//    }
//
//
//}
