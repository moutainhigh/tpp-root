package com.fxbank.tpp.tcex.trade;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30013000801;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30013000201;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.esb.service.IPasswordService;
import com.fxbank.tpp.tcex.dto.esb.REP_TR004;
import com.fxbank.tpp.tcex.dto.esb.REQ_TR004;
import com.fxbank.tpp.tcex.model.RcvTraceInitModel;
import com.fxbank.tpp.tcex.model.RcvTraceQueryModel;
import com.fxbank.tpp.tcex.model.RcvTraceUpdModel;
import com.fxbank.tpp.tcex.service.IRcvTraceService;
/**
 * 村镇冲正业务
 * @author liye
 *
 */
@Service("REQ_TR004")
public class TownReversal implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(CityDeposit.class);


	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IRcvTraceService rcvTraceService;
	
	@Reference(version = "1.0.0")
	private IPasswordService passwordService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		
		REQ_TR004 reqDto = (REQ_TR004) dto;
		String platDate = reqDto.getReqBody().getPlatDate();
		String platTraceno = reqDto.getReqBody().getPlatTraceno();
		// 交易机构
		String txBrno = reqDto.getReqSysHead().getBranchId();
		// 柜员号
		String txTel = reqDto.getReqSysHead().getUserId();
		
		String macDataStr = JsonUtil.toJson(reqDto.getReqBody());
		byte[] macBytes = macDataStr.getBytes();
		passwordService.verifyMac(myLog, macBytes, reqDto.getReqSysHead().getMacValue());
		myLog.info(logger, "村镇通存商行MAC校验成功，渠道日期" + platDate +  
				"渠道流水号" + platTraceno);
		
//		RcvTraceQueryModel model = rcvTraceService.getRcvTraceByKey(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno(),
//				Integer.parseInt(platDate), Integer.parseInt(platTraceno));
		
		//调用核心冲正接口
		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30014000101.setReqSysHead(reqSysHead);	
		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();
		reqBody_30014000101.setChannelSeqNo(CIP.SYSTEM_ID+platDate+String.format("%08d",Integer.parseInt(platTraceno)));
//		reqBody_30014000101.setReference(model.getHostTraceno());
		reqBody_30014000101.setReversalReason("村镇【"+txBrno+"】柜面通发起冲正");
		reqBody_30014000101.setEventType("");
		
		String code="",msg="";
		try {
			ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101, ESB_REP_30014000101.class);
			code = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetCode();
			msg = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetMsg();
		} catch (Exception e) {
			code="error";
			msg = e.getMessage();
			System.out.println("村镇【"+txBrno+"】柜面通冲正失败:"+e);
		}
		
		
		logger.info("村镇【"+txBrno+"】柜面通冲正反馈码【"+code+"】，反馈信息【"+msg+"】");
		
		RcvTraceUpdModel record = new RcvTraceUpdModel(myLog, Integer.parseInt(platDate), reqDto.getSysTime(),Integer.parseInt(platTraceno));
		record.setHostState(code.equals("000000")?"5":(code.equals("@@@@")?"7":"6"));
		record.setRetCode(code);
		record.setRetMsg(msg);
		rcvTraceService.rcvTraceUpd(record);
		
		REP_TR004 repDto = new REP_TR004();
		repDto.getRepBody().setSts(code.equals("000000")?"1":"2");

		return repDto;
	}

}
