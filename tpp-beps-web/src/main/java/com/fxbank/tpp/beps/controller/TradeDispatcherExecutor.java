package com.fxbank.tpp.beps.controller;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.controller.TradeDispatcherBase;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.beps.dto.pmts.DTO_BASE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author : 周勇沩
 * @description: 交易处理入口类
 * @Date : 2019/4/23 21:12
 */
@Controller
public class TradeDispatcherExecutor {

    private static Logger logger = LoggerFactory.getLogger(TradeDispatcherExecutor.class);

    @Resource
    private TradeDispatcherBase tradeDispatcherBase;

    @Resource
    private LogPool logPool;

    public DataTransObject txMainFlowController(DTO_BASE dtoBase) {
        MyLog myLog = logPool.get();
        myLog.debug(logger, "交易流程执行开始...");
        DataTransObject reqDto = dtoBase;
        DataTransObject repDto = tradeDispatcherBase.txMainFlowController(reqDto);
        myLog.debug(logger, "交易流程执行完毕...");
        return repDto;
    }

}
