package com.dream.pay.voucher.repository;

/**
 * @author mengzhenbin
 * @since 2017/2/1
 */
public interface SysParamRepository {
    /**
     * 根据配置码码获取配置
     *
     * @param sysCode 参数编码
     * @return
     */
    public String getSysValueBySysCode(String sysCode);


    /**
     * 根据配置码码修改配置
     *
     * @param sysCode  参数编码
     * @param sysValue 参数值
     * @return
     */
    public Integer updateSysValueBySysCode(String sysCode, String sysValue);


}
