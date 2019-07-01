package com.fxbank.tpp.bocm.trade.esb;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.bocm.dto.esb.REP_30061000701;
import com.fxbank.tpp.bocm.dto.esb.REQ_30061000701;
import com.fxbank.tpp.bocm.exception.BocmTradeExecuteException;
import com.fxbank.tpp.bocm.model.BocmSndTraceInitModel;
import com.fxbank.tpp.bocm.model.BocmSndTraceUpdModel;
import com.fxbank.tpp.bocm.model.REP_10000;
import com.fxbank.tpp.bocm.model.REP_20000;
import com.fxbank.tpp.bocm.model.REQ_10000;
import com.fxbank.tpp.bocm.model.REQ_20000;
import com.fxbank.tpp.bocm.service.IBocmSndTraceService;
import com.fxbank.tpp.bocm.service.IForwardToBocmService;
import com.fxbank.tpp.bocm.util.NumberUtil;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30011000104;
import com.fxbank.tpp.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.tpp.esb.service.IForwardToESBService;

import redis.clients.jedis.Jedis;


/** 
* @ClassName: DP_BocmTrsr 
* @Description: 本行卡付款转账 
* @author Duzhenduo
* @date 2019年4月17日 上午9:23:03 
*  
*/
@Service("REQ_30061000701")
public class DP_BocmTra extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(DP_BocmTra.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToBocmService forwardToBocmService;

	@Reference(version = "1.0.0")
	private IBocmSndTraceService bocmSndTraceService;

	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "bocm.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061000701 reqDto = (REQ_30061000701) dto;
		REQ_30061000701.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30061000701 rep = new REP_30061000701();
		
		ESB_REP_30011000104 esbRep_30011000104 = null;
		//核心记账日期
		String hostDate = null;
		//核心记账流水号
		String hostTraceno = null;
		//核心记账返回状态码
		String retCode = null;
		//核心记账返回状态信息
		String retMsg = null;		
		try {
			//1. 核心记账
			myLog.info(logger, "本行卡付款转账核心记账请求");
			esbRep_30011000104 = hostCharge(reqDto);
			hostDate = esbRep_30011000104.getRepSysHead().getRunDate();
			hostTraceno = esbRep_30011000104.getRepBody().getReference();
			retCode = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetCode();
			retMsg = esbRep_30011000104.getRepSysHead().getRet().get(0).getRetMsg();					
		} catch (SysTradeExecuteException e) {
			//接收ESB报文应答超时
			if(SysTradeExecuteException.CIP_E_000004.equals(e.getRspCode())||"ESB_E_000052".equals(e.getRspCode())) {		
				//超时不记录流水直接抛异常，如果记账成功，对账会失败
				myLog.error(logger, "本行卡付款转账，本行核心记账接收ESB报文应答超时，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);	
				SysTradeExecuteException e2 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004,"交易失败:"+e.getRspMsg()+",请核对记账状态,如果记账成功请进行抹账处理");
				throw e2;
			//其他错误
			}else {
				myLog.error(logger, "本行卡付款转账，本行核心记账失败，渠道日期" + reqDto.getSysDate() + 
					"渠道流水号" + reqDto.getSysTraceno(), e);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(e.getRspCode(),"交易失败:本行核心记账失败，"+e.getRspMsg());
				throw e2;
			}
		}
		//2.核心记账成功，插入流水表
		initRecord(reqDto, hostDate, hostTraceno, "1", retCode, retMsg);
		myLog.info(logger, "本行卡付款转账，本行核心记账成功,核心流水号"+hostTraceno+",渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		
		//交行记账流水号
		String bocmTraceNo = null;
		int bocmDate = 0;
		int bocmTime = 0;
		//账户余额
		String actBal = "0";
		//手续费
		String fee = "0";
		//交行响应码
		String bocmRepcd = "";
		//交行响应信息
		String bocmRepmsg = "";		
		//IC_CARD_FLG_T4判断IC卡磁条卡标志   磁条卡2  IC卡5
		String cardTypeName = "";
		//原交易代码，用于异常判断请求磁条卡交易还是IC卡交易
		String oTxnCd = null;
		REP_10000 rep10000 = null;
		REP_20000 rep20000 = null;
		REQ_10000 req10000 = null;
		REQ_20000 req20000 = null;
		//交行记账.通过标识判断调用磁条卡记账还是ic卡记账
		if("2".equals(reqBody.getIcCardFlgT4())){
			oTxnCd = "10000";
			req10000 = new REQ_10000(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
			super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req10000); // 设置报文头中的行号信息
			//更新交易发起行接收行  人行行号  
			updateBanknoRecord(reqDto,req10000.getSbnkNo(),req10000.getRbnkNo());
		}else{
			oTxnCd = "20000";
			req20000 = new REQ_20000(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
			super.setBankno(myLog, reqDto, reqDto.getReqSysHead().getBranchId(), req20000); // 设置报文头中的行号信息
			//更新交易发起行接收行  人行行号  
			updateBanknoRecord(reqDto,req20000.getSbnkNo(),req20000.getRbnkNo());
		}	
		

		//3. 交行记账
		try {		
			if(oTxnCd.equals("10000")) {	
				myLog.info(logger, "发送磁条卡转账通存请求至交行");
				cardTypeName = "磁条卡";
				rep10000 = magCardCharge(reqDto,req10000);
				bocmTraceNo = rep10000.getRlogNo();
				bocmDate = rep10000.getSysDate();
				bocmTime = rep10000.getSysTime();
				fee = rep10000.getFee().toString();
				actBal = rep10000.getActBal().toString();
				bocmRepcd = rep10000.getTrspCd();
				bocmRepmsg = rep10000.getTrspMsg();					
			}else{
				myLog.info(logger, "发送磁条卡转账通存请求至交行");
				cardTypeName = "IC卡";
				rep20000 = iCCardCharge(reqDto,req20000);
				bocmTraceNo = rep20000.getRlogNo();
				bocmDate = rep20000.getSysDate();
				bocmTime = rep20000.getSysTime();
				fee = rep20000.getFee().toString();
				actBal = rep20000.getActBal().toString();
				bocmRepcd = rep20000.getTrspCd();
				bocmRepmsg = rep20000.getTrspMsg();	
			}
			
		} catch (SysTradeExecuteException e) { // 记账交易参考一下方式处理，查询交易不用
			// 如果不是账务类请求，可以不用分类处理应答码，统一当成失败处理即可
			// 如果交易不关心返回的异常类型，直接可以不捕获，直接省略catch，抛出异常即可
			if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000006) // 生成请求失败
					|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000007)
					|| e.getRspCode().equals(SysTradeExecuteException.CIP_E_000008)) {
				//更新交行记账状态为失败
				updateBocmRecord(reqDto, bocmDate,bocmTime,"", "2","","","");
				//生成请求失败，本行核心冲正
				myLog.error(logger, "本行卡付款转账，交行"+cardTypeName+"通存记账生成请求失败，核心冲正，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				//核心记账状态，1-成功，4-冲正成功，5-冲正失败，6-冲正超时
				ESB_REP_30014000101 esbRep_30014000101 = null;
				String hostReversalCode = null;
				String hostReversalMsg = null;
				try {
					esbRep_30014000101 = hostReversal(reqDto,hostTraceno);
					hostReversalCode = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetCode();
					hostReversalMsg = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetMsg();
				}catch(SysTradeExecuteException e1) {
					//如果冲正超时，交行记账失败，对账时返回记账流水，则对账失败
					//接收ESB报文应答超时
					if(SysTradeExecuteException.CIP_E_000004.equals(e1.getRspCode())||"ESB_E_000052".equals(e1.getRspCode())) {
						updateHostRecord(reqDto, "", "", "6", e1.getRspCode(), e1.getRspMsg());
						myLog.error(logger, "本行卡付款转账，本行核心冲正超时，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);							
						SysTradeExecuteException e2 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004,"交易失败:请求交行系统失败,"+e.getRspMsg()+"核心冲正超时，请核对记账状态，如果记账成功请进行抹账处理");
						throw e2;			
					//其他冲正错误
					}else {
						updateHostRecord(reqDto, "", "", "5", e1.getRspCode(), e1.getRspMsg());
						myLog.error(logger, "本行卡付款转账，本行核心冲正失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);					
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10017,"交易失败:请求交行系统失败,"+e.getRspMsg()+"核心冲正失败，请核对记账状态，如果记账成功请进行抹账处理");
						throw e2;
					}
				}
				updateHostRecord(reqDto, hostDate, hostTraceno, "4", hostReversalCode, hostReversalMsg);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:交行记账失败,"+e.getRspMsg());
				myLog.error(logger, "本行卡付款转账，本行核心冲正成功，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(),e2);
				throw e2;
			}else if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009)||e.getRspCode().equals("JH6203")) { // 接收交行返回结果超时
				updateBocmRecord(reqDto,bocmDate,bocmTime,bocmTraceNo,"3","","","");
				myLog.error(logger, "本行卡付款转账，本行"+cardTypeName+"通存记账返回结果超时，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				try {
					myLog.error(logger, "本行卡付款转账，交行"+cardTypeName+"通存记账返回结果超时,请求重发");
					if(oTxnCd.equals("10000")){
						rep10000 = (REP_10000)magCardCharge(reqDto,req10000);
						bocmTraceNo = rep10000.getRlogNo();
						bocmDate = rep10000.getSysDate();
						bocmTime = rep10000.getSysTime();
						fee = rep10000.getFee().toString();
						actBal = rep10000.getActBal().toString();
						bocmRepcd = rep10000.getTrspCd();
						bocmRepmsg = rep10000.getTrspMsg();
					}else{
						rep20000 = (REP_20000)iCCardCharge(reqDto,req20000);
						bocmTraceNo = rep20000.getRlogNo();
						bocmDate = rep20000.getSysDate();
						bocmTime = rep20000.getSysTime();
						fee = rep20000.getFee().toString();
						actBal = rep20000.getActBal().toString();
						bocmRepcd = rep20000.getTrspCd();
						bocmRepmsg = rep20000.getTrspMsg();
					}				
					myLog.info(logger, "本行卡付款转账，交行"+cardTypeName+"通存记账请求重发成功");
				}catch(Exception e1) {
					myLog.error(logger, "本行卡付款转账，交行"+cardTypeName+"通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);
					myLog.error(logger, "重发返回异常，给柜面返回成功，防止短款");
					bocmRepmsg = "请求交行记账超时";
					//如果还是超时返回成功，防止短款
				}
			} else { // 目标系统应答失败
						// 确认是否有冲正操作
				//请求交行系统失败，本行核心冲正
				updateBocmRecord(reqDto,bocmDate,bocmTime,bocmTraceNo,"2","","","");
				myLog.error(logger, "本行卡付款转账，交行磁条卡通存记账应答失败,本行核心冲正,渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e);
				//核心记账状态，1-成功，4-冲正成功，5-冲正失败，6-冲正超时
				ESB_REP_30014000101 esbRep_30014000101 = null;
				String hostReversalCode = null;
				String hostReversalMsg = null;
				try {
					esbRep_30014000101 = hostReversal(reqDto,hostTraceno);
					hostReversalCode = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetCode();
					hostReversalMsg = esbRep_30014000101.getRepSysHead().getRet().get(0).getRetMsg();
				}catch(SysTradeExecuteException e1) {
					//对于冲正失败处理：返回交易失败，对账的时候忽略核心记账的成功状态
					//接收ESB报文应答超时
					if(SysTradeExecuteException.CIP_E_000004.equals(e.getRspCode())||"ESB_E_000052".equals(e.getRspCode())) {
						updateHostRecord(reqDto, "", "", "6", e1.getRspCode(), e1.getRspMsg());
						myLog.error(logger, "本行卡付款转账，本行核心冲正超时，渠道日期" + reqDto.getSysDate() + 
								"渠道流水号" + reqDto.getSysTraceno(), e1);						
						SysTradeExecuteException e2 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000004,"交易失败:交行记账失败,"+e.getRspMsg()+"核心冲正超时，请核对记账状态，如果记账成功请进行抹账处理");
						throw e2;
					//其他冲正错误
					}else {
						updateHostRecord(reqDto, "", "", "5", e1.getRspCode(), e1.getRspMsg());
						myLog.error(logger, "本行卡付款转账，本行核心冲正失败，渠道日期" + reqDto.getSysDate() + 
							"渠道流水号" + reqDto.getSysTraceno(), e1);						
						BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10017,"交易失败:交行记账失败,"+e.getRspMsg()+"核心冲正失败，请核对记账状态，如果记账成功请进行抹账处理");
						throw e2;
					}
				}
				updateHostRecord(reqDto, hostDate, hostTraceno, "4", hostReversalCode, hostReversalMsg);
				BocmTradeExecuteException e2 = new BocmTradeExecuteException(BocmTradeExecuteException.BOCM_E_10002,"交易失败:交行记账失败，"+e.getRspMsg());
				myLog.error(logger, "本行卡付款转账，本行核心冲正成功，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(),e2);
				throw e2;
			}
		} catch (Exception e) { // 其它未知错误，可以当成超时处理
			// 确认是否有冲正操作
			myLog.error(logger, "本行卡付款转账,交行"+cardTypeName+"通存记账其它未知错误,渠道日期" + reqDto.getSysDate() + 
					"渠道流水号" + reqDto.getSysTraceno(), e);
			try {
				myLog.error(logger, "本行卡付款转账，交行"+cardTypeName+"通存记账其它未知错误,请求重发");
				if(oTxnCd.equals("10000")){
					rep10000 = (REP_10000)magCardCharge(reqDto,req10000);
					bocmTraceNo = rep10000.getRlogNo();
					bocmDate = rep10000.getSysDate();
					bocmTime = rep10000.getSysTime();
					fee = rep10000.getFee().toString();
					actBal = rep10000.getActBal().toString();
					bocmRepcd = rep10000.getTrspCd();
					bocmRepmsg = rep10000.getTrspMsg();
				}else{
					rep20000 = (REP_20000)iCCardCharge(reqDto,req20000);
					bocmTraceNo = rep20000.getRlogNo();
					bocmDate = rep20000.getSysDate();
					bocmTime = rep20000.getSysTime();
					fee = rep20000.getFee().toString();
					actBal = rep20000.getActBal().toString();
					bocmRepcd = rep20000.getTrspCd();
					bocmRepmsg = rep20000.getTrspMsg();
				}					
				myLog.info(logger, "交行卡付款转账，交行"+cardTypeName+"通存记账请求重发成功");
			}catch(Exception e1) {
				myLog.error(logger, "本行卡付款转账，交行"+cardTypeName+"通存记账重发报错，渠道日期" + reqDto.getSysDate() + 
						"渠道流水号" + reqDto.getSysTraceno(), e1);
				bocmRepmsg = "通存记账请求重发异常";
				myLog.error(logger, "重发返回异常，给柜面返回成功，防止短款");
				//如果还是超时返回成功，防止短款
			}
		} 	
		if(!"".equals(fee)){
			fee = NumberUtil.removePointToString(Double.parseDouble(fee));
		}
		actBal = NumberUtil.removePointToString(Double.parseDouble(actBal));
		//5.交行记账成功，更新流水表交行记账状态
		updateBocmRecord(reqDto,bocmDate,bocmTime,bocmTraceNo,"1",actBal,bocmRepcd,bocmRepmsg);
		myLog.info(logger, "本行卡付款转账，交行"+cardTypeName+"通存记账成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());		
		return rep;
	}


	/** 
	* @Title: initRecord 
	* @Description: 登记流水表
	* @param @param reqDto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void initRecord(DataTransObject dto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061000701 reqDto = (REQ_30061000701)dto;
		REQ_30061000701.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_SYS_HEAD reqSysHead = reqDto.getReqSysHead();
		BocmSndTraceInitModel record = new BocmSndTraceInitModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setSourceType(reqSysHead.getSourceType());
		record.setTxBranch(reqSysHead.getBranchId());
		record.setTranType("JH10");
		// 通存通兑标志；0通存、1通兑
		record.setDcFlag("0");
		record.setTxAmt(reqBody.getTrsrAmtT3());
		//现转标志；0现金、1转账
		record.setTxInd("1");
		//手续费手续方式
		record.setFeeFlag(reqBody.getRcveWyT());
		//手续费
		String fee = reqBody.getFeeT3();
		if(fee!=null&&!fee.equals("")){
			record.setFee(new BigDecimal(fee));
		}
		record.setHostState(hostState);
		record.setBocmState("0");
		record.setTxTel(reqSysHead.getUserId());
		
		//付款方开户行
		record.setPayerBank(reqBody.getPyrOpnBnkNoT2());
		//付款方账户类型
		record.setPayerActtp(reqBody.getPyrAcctTpT());
		record.setPayerAcno(reqBody.getBnkCardAcctNoT());
		record.setPayerName(reqBody.getBnkCardAcctNaT());
		
		//本行卡付款转账，收款方为交行卡
		//收款方开户行
		record.setPayeeBank(reqBody.getPyeeOpnBnkNoT1());
		//交行卡账户类型定值2
		record.setPayeeActtp(reqBody.getAcctTpT());	
		record.setPayeeAcno(reqBody.getBcmCardAcctNoT());
		record.setPayeeName(reqBody.getBcmCardAcctNaT());		
		record.setChkTel(reqSysHead.getApprUserId());
		record.setAuthTel(reqSysHead.getAuthUserId());
		String IcCardFlag = reqBody.getIcCardFlgT4();
		if("2".equals(IcCardFlag)){
			record.setTxCode("10000");
		}else{
			record.setTxCode("20000");
		}
		if(hostDate!=null&&!"".equals(hostDate)){
			record.setHostDate(Integer.parseInt(hostDate));
		}
		record.setHostTraceno(hostTraceno);
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		bocmSndTraceService.sndTraceInit(record);
	}


	/** 
	* @Title: hostCharge 
	* @Description: 本行核心记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000103    返回类型 
	* @throws 
	*/
	public ESB_REP_30011000104 hostCharge(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061000701 reqDto = (REQ_30061000701)dto;
		REQ_30061000701.REQ_BODY reqBody = reqDto.getReqBody();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;

		txTel = reqDto.getReqSysHead().getUserId();
		txBrno = reqDto.getReqSysHead().getBranchId();
		ESB_REQ_30011000104 esbReq_30011000104 = new ESB_REQ_30011000104(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000104.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		reqSysHead.setSourceBranchNo(reqDto.getReqSysHead().getSourceBranchNo());
		reqSysHead.setSourceType(reqDto.getReqSysHead().getSourceType());
		
		esbReq_30011000104.setReqSysHead(reqSysHead);

		ESB_REQ_30011000104.REQ_BODY reqBody_30011000104 = esbReq_30011000104.getReqBody();
		reqBody_30011000104.setBaseAcctNo(reqBody.getBnkCardAcctNoT());
		reqBody_30011000104.setAcctName(reqBody.getBnkCardAcctNaT());
		reqBody_30011000104.setTranType("JH10");
		reqBody_30011000104.setTranCcy("CNY");
		reqBody_30011000104.setTranAmt(reqBody.getTrsrAmtT3());
		reqBody_30011000104.setWithdrawalType("P");
		reqBody_30011000104.setPassword(reqBody.getPwdT());
		reqBody_30011000104.setOthBaseAcctNo(reqBody.getBcmCardAcctNoT());
		reqBody_30011000104.setOthBaseAcctName(reqBody.getBcmCardAcctNaT());
		reqBody_30011000104.setChannelType("BU");
		reqBody_30011000104.setChargeMethod(reqBody.getRcveWyT());
		reqBody_30011000104.setSendBankCode(reqBody.getPyrOpnBnkNoT2());
		reqBody_30011000104.setBankCode(reqBody.getPyrOpnBnkNoT2());
		reqBody_30011000104.setOthBankCode(reqBody.getPyeeOpnBnkNoT1());
		reqBody_30011000104.setSettlementDate(reqDto.getSysDate()+"");
		reqBody_30011000104.setCollateFlag("Y");
		reqBody_30011000104.setDirection("O");
		
		reqBody_30011000104.setBankCode(reqBody.getPyrOpnBnkNoT2());
		reqBody_30011000104.setOthBankCode(reqBody.getPyeeOpnBnkNoT1());

		ESB_REP_30011000104 esbRep_30011000104 = forwardToESBService.sendToESB(esbReq_30011000104, reqBody_30011000104,
				ESB_REP_30011000104.class);
		return esbRep_30011000104;
	}
	
	/** 
	* @Title: magCardCharge 
	* @Description: 交行磁条卡通存记账
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_10000    返回类型 
	* @throws 
	*/
	public REP_10000 magCardCharge(DataTransObject dto,REQ_10000 req10000) throws SysTradeExecuteException {
		REQ_30061000701 reqDto = (REQ_30061000701)dto;
		REQ_30061000701.REQ_BODY reqBody = reqDto.getReqBody();
		
		//交易金额
		String amt = reqBody.getTrsrAmtT3();
		//交易金额补零
		req10000.setTxnAmt(NumberUtil.addPoint(Double.parseDouble(amt)));
		//手续费收取方式
		req10000.setFeeFlg("0");
		//手续费
		String fee = reqBody.getFeeT3();
		//手续费补零
		req10000.setFee(NumberUtil.addPoint(Double.parseDouble(fee)));
		

		req10000.setOprFlg(reqBody.getInWyT());
		//业务模式，0 现金1 转账（即实时转账）9 其他
		req10000.setTxnMod("1");
		req10000.setPayBnk(reqBody.getPyrOpnBnkNoT2());
		//付款人账户类型,0 银行账号1 贷记卡2 借记卡3其他
		req10000.setPactTp(reqBody.getPyrAcctTpT());
		req10000.setPactNo(reqBody.getBnkCardAcctNoT());
		req10000.setPayNam(reqBody.getBnkCardAcctNaT());
		req10000.setRecBnk(reqBody.getPyeeOpnBnkNoT1());
		req10000.setRactTp(reqBody.getAcctTpT());
		req10000.setRactNo(reqBody.getBcmCardAcctNoT());
		req10000.setRecNam(reqBody.getBcmCardAcctNaT());
		req10000.setCuIdTp(reqBody.getBcmCardAcctCrtfTpT());
		req10000.setCuIdNo(reqBody.getBcmCardAcctCrtfNoT());
		req10000.setAgIdTp(reqBody.getAgentCrtfT());
		req10000.setAgIdNo(reqBody.getCmsnHldrGlblIdT());
		req10000.setSecMag(reqBody.getScdTrkInfoT2());
		req10000.setThdMag(reqBody.getThrTrkInfoT1());
		req10000.setRemark(reqBody.getNoteT2());      
		REP_10000 rep_10000 = forwardToBocmService.sendToBocm(req10000, 
				REP_10000.class);	
		return rep_10000;	
	}
	/** 
	* @Title: iCCardCharge 
	* @Description: 交行IC卡通存记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_20000    返回类型 
	* @throws 
	*/
	public REP_20000 iCCardCharge(DataTransObject dto,REQ_20000 req20000) throws SysTradeExecuteException {
		REQ_30061000701 reqDto = (REQ_30061000701)dto;
		REQ_30061000701.REQ_BODY reqBody = reqDto.getReqBody();
		//交易金额
		String amt = reqBody.getTrsrAmtT3();
		//交易金额补零
		req20000.setTxnAmt(NumberUtil.addPoint(Double.parseDouble(amt)));
		//手续费收取方式
		req20000.setFeeFlg("0");
		//手续费
		String fee = reqBody.getFeeT3();
		//手续费补零
		req20000.setFee(NumberUtil.addPoint(Double.parseDouble(fee)));
		req20000.setOprFlg(reqBody.getInWyT());
		//业务模式，0 现金1 转账（即实时转账）9 其他
		req20000.setTxnMod("1");
		req20000.setPayBnk(reqBody.getPyrOpnBnkNoT2());
		//付款人账户类型,0 银行账号1 贷记卡2 借记卡3其他
		req20000.setPactTp(reqBody.getPyrAcctTpT());
		req20000.setPactNo(reqBody.getBnkCardAcctNoT());
		req20000.setPayNam(reqBody.getBnkCardAcctNaT());
		req20000.setRecBnk(reqBody.getPyeeOpnBnkNoT1());
		req20000.setRactTp(reqBody.getAcctTpT());
		req20000.setRactNo(reqBody.getBcmCardAcctNoT());
		req20000.setRecNam(reqBody.getBcmCardAcctNaT());
		req20000.setCuIdTp(reqBody.getBcmCardAcctCrtfTpT());
		req20000.setCuIdNo(reqBody.getBcmCardAcctCrtfNoT());
		req20000.setAgIdTp(reqBody.getAgentCrtfT());
		req20000.setAgIdNo(reqBody.getCmsnHldrGlblIdT());
		req20000.setSeqNo(reqBody.getIcCardSeqNoT1());
		req20000.setARQC(reqBody.getIcCard91T());
		req20000.setICAID(reqBody.getIcCard9f09T());
		req20000.setICOutDate(reqBody.getIcCardAvaiDtT());
		req20000.setICData(reqBody.getIcCardF55T());
		req20000.setRemark(reqBody.getNoteT2());     
		REP_20000 rep_20000 = forwardToBocmService.sendToBocm(req20000, 
				REP_20000.class);
		return rep_20000;
	}
	/** 
	* @Title: hostReversal 
	* @Description: 本行核心冲正
	* @param @param reqDto
	* @param @param hostSeqno
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30014000101    返回类型 
	* @throws 
	*/
	public ESB_REP_30014000101 hostReversal(DataTransObject dto,String hostSeqno)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061000701 reqDto = (REQ_30061000701)dto;
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		txTel = reqDto.getReqSysHead().getUserId();
		txBrno = reqDto.getReqSysHead().getBranchId();
		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		
		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		reqSysHead.setSourceBranchNo(reqDto.getReqSysHead().getSourceBranchNo());
		reqSysHead.setSourceType(reqDto.getReqSysHead().getSourceType());
		
		esbReq_30014000101.setReqSysHead(reqSysHead);

		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();
		esbReq_30014000101.setReqSysHead(reqSysHead);	
		reqBody_30014000101.setChannelSeqNo(esbReq_30014000101.getReqSysHead().getSeqNo());
		reqBody_30014000101.setReversalReason("交行记账失败,本行核心冲正");
		reqBody_30014000101.setEventType("");

		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}

	
	/** 
	* @Title: updateHostRecord 
	* @Description: 更新核心记账状态 
	*/
	public BocmSndTraceUpdModel updateHostRecord(DataTransObject dto, String hostDate, String hostTraceno,
			String hostState, String retCode, String retMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		if(!"".equals(hostDate)) {
			record.setHostDate(Integer.parseInt(hostDate));
		}
		record.setHostState(hostState);
		if(hostTraceno!=null&&!"".equals(hostTraceno)){
			record.setHostTraceno(hostTraceno);
		}
		record.setRetCode(retCode);
		record.setRetMsg(retMsg);
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}
	
	/** 
	* @Title: updateBocmRecord 
	* @Description: 更新交行记账状态 
	*/
	public BocmSndTraceUpdModel updateBocmRecord(DataTransObject dto,
			int bocmDate,int bocmTime,String bocmTraceno, 
			String bocmState,String actBal,String bocmRepcd,String bocmRepmsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		record.setBocmState(bocmState);
		record.setBocmDate(bocmDate);
		record.setBocmTime(bocmTime);
		record.setBocmTraceno(bocmTraceno);
		record.setBocmRepcd(bocmRepcd);
		record.setBocmRepmsg(bocmRepmsg);
		if(actBal!=null&&!actBal.equals("")){
			record.setActBal(new BigDecimal(actBal));
		}	
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}
	
	/** 
	* @Title: updateBocmRecord 
	* @Description: 更新交行记账人行行号记录
	*/
	public BocmSndTraceUpdModel updateBanknoRecord(DataTransObject dto,
			String sndBankno, String rcvBankno) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		BocmSndTraceUpdModel record = new BocmSndTraceUpdModel(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		//发起行
		record.setSndBankno(sndBankno);
		//接收行
		record.setRcvBankno(rcvBankno);
		bocmSndTraceService.sndTraceUpd(record);
		return record;
	}
}
