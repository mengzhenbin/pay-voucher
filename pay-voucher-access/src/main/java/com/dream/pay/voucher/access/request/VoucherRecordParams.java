package com.dream.pay.voucher.access.request;

import com.dream.pay.enums.CurrencyCode;
import com.dream.pay.validators.InEnum;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 记账请求参数
 *
 * @author mengzhenbin
 * @since 2017/1/17
 */
@Data
public class VoucherRecordParams implements Serializable {

    private static final long serialVersionUID = 3437338287528213420L;
    /**
     * 消息序列号
     */
    @NotBlank(message = "消息序列号不能为空")
    private long serialNo;
    /**
     * 记账码
     */
    @NotBlank(message = "记账码不能为空")
    private String accountingCode;
    /**
     * 渠道码
     */
    @NotBlank(message = "渠道码不能为空")
    private String channelCode;
    /**
     * 会计日期
     */
    private String accountingDate;
    /**
     * 外部订单号（业务线单号）
     */
    private String orderNo;
    /**
     * 收单号（支付收单系统）
     */
    private String acquireNo;
    /**
     * 交易流水号（上游系统提供）
     */
    @NotBlank(message = "交易流水号不能为空")
    private String outerWaterNo;
    /**
     * 付款账户号，适用支付，退款，提现，充值场景
     */
    private String payerAccountNo;

    /**
     * 收款方账号,目前只有转账场景才有
     */
    private String payeeAccountNo;
    /**
     * 交易金额
     */
    @NotBlank(message = "消息序列号不能为空")
    @Min(value = 0, message = "支付金额不能小于0")
    private long amount;

    /**
     * 币种：目前默认为人民币
     */
    @NotBlank(message = "币种参数不能为空")
    @InEnum(clazz = CurrencyCode.class, property = "num", message = "币种参数非法")
    private String currency;

    /**
     * 交易描述信息
     */
    private String remark;


}