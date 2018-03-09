package com.dream.pay.voucher.repository;

import com.dream.pay.voucher.model.VoucherSubjectItemDO;

/**
 * @author mengzhenbin
 * @since 17/1/12
 */
public interface SubjectItemRepository {

    /**
     * 根据科目编码查询科目明细
     *
     * @param code 科目编号
     * @return
     */
    public VoucherSubjectItemDO selectByCode(String code);

}
