package com.dream.pay.voucher.repository.impl;

import com.dream.pay.voucher.dao.VoucherSysParamDOMapper;
import com.dream.pay.voucher.model.VoucherSysParamDO;
import com.dream.pay.voucher.repository.SysParamRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author mengzhenbin
 * @since 2017/2/1
 */
@Component
public class SysParamRepositoryImpl implements SysParamRepository {
    @Resource
    private VoucherSysParamDOMapper voucherSysParamDao;

    @Override
    public String getSysValueBySysCode(String sysCode) {
        return voucherSysParamDao.getBySysCode(sysCode).getSysValue();
    }

    @Override
    public Integer updateSysValueBySysCode(String sysCode, String sysValue) {
        VoucherSysParamDO voucherSysParamDO = new VoucherSysParamDO();
        voucherSysParamDO.setUpdateTime(new Date());
        voucherSysParamDO.setSysCode(sysCode);
        voucherSysParamDO.setSysValue(sysValue);
        return voucherSysParamDao.updateSysValue(voucherSysParamDO);
    }
}
