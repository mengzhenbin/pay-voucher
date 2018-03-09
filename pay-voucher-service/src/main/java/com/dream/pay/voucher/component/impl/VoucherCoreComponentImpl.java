package com.dream.pay.voucher.component.impl;


import com.dream.pay.voucher.common.enums.SysCode;
import com.dream.pay.voucher.common.utils.AccountingDateUtil;
import com.dream.pay.voucher.component.VoucherCoreComponent;
import com.dream.pay.voucher.repository.SysParamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 会计系统核心工具实现类
 * 1:获取会计日期
 *
 * @author mengzhenbin
 * @since 2017/1/31
 */
@Slf4j
@Component
public class VoucherCoreComponentImpl implements VoucherCoreComponent {
    @Resource
    private SysParamRepository sysParamRepositoryImpl;

    /**
     * 获取会计日期
     *
     * @return
     */
    public String getVoucherDate() {
        return sysParamRepositoryImpl.getSysValueBySysCode(SysCode.VOUCHER_DATE.getId());
    }

    /**
     * 会计日期日切
     */
    public void setNextVoucherDate() {
        //更新上一会计日期
        sysParamRepositoryImpl.
                updateSysValueBySysCode(SysCode.LAST_VOUCHER_DATE.getId(),
                        getVoucherDate());
        //更新会计日期
        sysParamRepositoryImpl.
                updateSysValueBySysCode(SysCode.VOUCHER_DATE.getId(),
                        AccountingDateUtil.getNextDay(getVoucherDate()));

    }
}

