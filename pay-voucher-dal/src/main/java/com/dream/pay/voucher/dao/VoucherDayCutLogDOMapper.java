package com.dream.pay.voucher.dao;

import com.dream.pay.voucher.model.VoucherDayCutLogDO;
import org.apache.ibatis.annotations.Param;

public interface VoucherDayCutLogDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherDayCutLogDO record);

    int insertSelective(VoucherDayCutLogDO record);

    VoucherDayCutLogDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherDayCutLogDO record);

    int updateByPrimaryKey(VoucherDayCutLogDO record);


    /**
     * 根据会计日期和任务ID获取VoucherDayCutJobEntity
     *
     * @param voucherDate 当前会计日
     * @param taskId      任务ID
     * @return VoucherDayCutJobEntity
     */
    public VoucherDayCutLogDO select(@Param("voucherDate") String voucherDate,
                                     @Param("taskId") int taskId);

    /**
     * 成功/失败修改日切任务状态
     *
     * @param desc
     * @param state
     * @param taskId
     * @param voucherDate
     * @return int
     */
    public int updateDayCutState(@Param("voucherDate") String voucherDate,
                                 @Param("taskId") int taskId,
                                 @Param("taskExecuteDesc") String desc,
                                 @Param("taskExecuteState") int state);
}