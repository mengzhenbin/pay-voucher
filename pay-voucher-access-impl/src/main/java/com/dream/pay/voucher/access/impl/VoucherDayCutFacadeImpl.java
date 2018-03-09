package com.dream.pay.voucher.access.impl;

import com.dream.pay.voucher.access.facade.VoucherDayCutFacade;
import com.dream.pay.voucher.component.VoucherCoreComponent;
import com.dream.pay.voucher.service.daycut.DayCutTaskScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * 会计日切
 * 执行日切任务
 *
 * @author mengzhenbin
 * @since 18/1/11
 */
@Path("/cut")
@Slf4j
public class VoucherDayCutFacadeImpl implements VoucherDayCutFacade {
    @Resource
    DayCutTaskScheduler dayCutTaskSchedulerImpl;
    @Resource
    VoucherCoreComponent voucherCoreComponent;
    @Resource
    ThreadPoolTaskExecutor taskExecutor;

    @GET
    @Path("/doDayCut")
    public void doDayCut() {
        log.info("会计日切任务开始处理");
        taskExecutor.execute(() -> {
            try {
                dayCutTaskSchedulerImpl.run(voucherCoreComponent.getVoucherDate());
            } catch (Exception e) {
                log.error("会计日切任务处理异常", e);
            }
        });
        log.info("会计日切任务处理结束");
    }
}
