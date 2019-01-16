package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS004;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS004;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_TS004;
import com.fxbank.tpp.tcex.dto.esb.REQ_TS004;
/**
 * 商行通兑业务
 * @author liye
 *
 */
@Service("REQ_TS004")
public class TS004 implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		/**
		REP_TS004 repDto = new REP_TS004();
		repDto.getRepBody().setSts("1");
		**/
		REQ_TS004 reqDto = (REQ_TS004) dto;
		REP_TS004 repDto = new REP_TS004();
		ESB_REQ_TS004 esbReq_TS004 = new ESB_REQ_TS004(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqTS004SysHead = new EsbReqHeaderBuilder(esbReq_TS004.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_TS004.setReqSysHead(reqTS004SysHead);
		ESB_REQ_TS004.REQ_BODY esbReqBody_TS004 = esbReq_TS004.getReqBody();
		esbReqBody_TS004.setPlatDate(reqDto.getReqBody().getPlatDate());
		esbReqBody_TS004.setPlatTraceno(reqDto.getReqBody().getPlatTraceno());
		ESB_REP_TS004 esbRep_TS004 = forwardToTownService.sendToTown(esbReq_TS004, esbReqBody_TS004,
				ESB_REP_TS004.class);
		repDto.getRepBody().setSts(esbRep_TS004.getRepBody().getSts());
		return repDto;
	}
}
