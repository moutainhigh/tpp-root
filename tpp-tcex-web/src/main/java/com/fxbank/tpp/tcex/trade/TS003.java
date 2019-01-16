package com.fxbank.tpp.tcex.trade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS002;
import com.fxbank.tpp.esb.model.ses.ESB_REP_TS003;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS002;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_TS003;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IForwardToTownService;
import com.fxbank.tpp.tcex.dto.esb.REP_TS003;
import com.fxbank.tpp.tcex.dto.esb.REQ_30041000901;
import com.fxbank.tpp.tcex.dto.esb.REQ_TS003;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;
/**
 * 商行通存记账确认
 * @author liye
 *
 */
@Service("REQ_TS003")
public class TS003 implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IForwardToTownService forwardToTownService;
	
	static SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		/**
		REP_TS003 repDto = new REP_TS003();
		repDto.getRepBody().setTownDate(sdf1.format(new Date()));
		repDto.getRepBody().setTownTraceNo(UUID.randomUUID().toString().replace("-", "").substring(0, 15));
		repDto.getRepBody().setSts("1");
		**/
		REQ_TS003 reqDto = (REQ_TS003) dto;
		REP_TS003 repDto = new REP_TS003();
		ESB_REQ_TS003 esbReq_TS003 = new ESB_REQ_TS003(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_TS003.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.build();
		esbReq_TS003.setReqSysHead(reqSysHead);
		ESB_REQ_TS003.REQ_BODY esbReqBody_TS003 = esbReq_TS003.getReqBody();
		esbReqBody_TS003.setPlatDate(reqDto.getReqBody().getPlatDate().toString());
		esbReqBody_TS003.setPlatTraceno(reqDto.getReqBody().getPlatTraceno().toString());

        
		ESB_REP_TS003 esbRep_TS003 = forwardToTownService.sendToTown(esbReq_TS003, esbReqBody_TS003,
				ESB_REP_TS003.class);
		repDto.getRepBody().setTownDate(esbRep_TS003.getRepBody().getTownDate());
		repDto.getRepBody().setTownTraceNo(esbRep_TS003.getRepBody().getTownTraceNo());
		repDto.getRepBody().setSts(esbRep_TS003.getRepBody().getSts());
		return repDto;
	}
}
