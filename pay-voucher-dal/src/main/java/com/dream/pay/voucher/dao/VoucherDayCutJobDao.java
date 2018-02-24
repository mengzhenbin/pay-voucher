package com.dream.pay.voucher.dao;


import com.dream.pay.voucher.model.VoucherDayCutJobEntity;
import org.apache.ibatis.annotations.Param;

public interface VoucherDayCutJobDao {
    int deleteByPrimaryKey(Long id);

    int insert(VoucherDayCutJobEntity record);

    int insertSelective(VoucherDayCutJobEntity record);

    VoucherDayCutJobEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VoucherDayCutJobEntity record);

    int updateByPrimaryKey(VoucherDayCutJobEntity record);

    /**
     * 根据会计日期和任务ID获取VoucherDayCutJobEntity
     *
     * @param accountingDate 会计日期
     * @param taskId         任务ID
     * @return VoucherDayCutJobEntity
     */
    public VoucherDayCutJobEntity select(@Param("accountingDate") String accountingDate,
                                         @Param("taskId") int taskId);

    /**
     * 成功/失败修改日切任务状态
     *
     * @return
     */
    public int UpdateDayCutState(@Param("accountingDate") String accountingDate,
                                 @Param("taskId") int taskId,
                                 @Param("taskExecuteDesc") String desc,
                                 @Param("taskExecuteState") int state);
}