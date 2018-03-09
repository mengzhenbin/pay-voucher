package com.dream.pay.voucher.repository;

import com.dream.pay.voucher.model.VoucherSubjectRecordDO;

import java.util.List;

/**
 * 会计分录接口
 *
 * @author mengzhenbin  17/1/10
 */
public interface SubjectRecordRepository {

    /**
     * 查询最大记录数
     *
     * @param voucherDate
     * @return long
     */
    public long selectMaxId(String voucherDate);

    /**
     * @param voucherDate 会计日期
     * @param startRow
     * @param endRow
     * @return List<VoucherSubjectRecordDO>
     */
    public List<VoucherSubjectRecordDO> selectByVoucherDate(String voucherDate, long startRow, long endRow);


    /**
     * 统计指定日期和账号的记录条数
     *
     * @param voucherDate
     * @param accountNo
     * @return long
     */
    public long countByVoucherDateAndAccountNo(String voucherDate, String accountNo);


    /**
     * @param voucherDate 会计日期
     * @param acctNo
     * @param startNo
     * @param pageSize
     * @return
     */
    public List<VoucherSubjectRecordDO> selectByVoucherDateAndAccountNo(String voucherDate, String acctNo, long startNo, long pageSize);


}
