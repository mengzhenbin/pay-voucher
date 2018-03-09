package com.dream.pay.voucher.repository.impl;

import com.dream.pay.voucher.dao.VoucherSubjectItemDOMapper;
import com.dream.pay.voucher.model.VoucherSubjectItemDO;
import com.dream.pay.voucher.repository.SubjectItemRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mengzhenbin
 * @since 2017/1/12
 */
@Component
public class SubjectItemRepositoryImpl implements SubjectItemRepository {
    @Resource
    VoucherSubjectItemDOMapper voucherSubjectItemDao;

    @Override
    public VoucherSubjectItemDO selectByCode(String code) {
        return voucherSubjectItemDao.selectByCode(code);
    }

}
