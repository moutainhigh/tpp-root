package com.fxbank.tpp.bocm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: SndTraceUpdModel 
* @Description: 往账更新模型
* @author YePuLiang
* @date 2019年04月15日 下午1:38:29 
*  
*/
public class BocmSndTraceUpdModel extends ModelBase implements Serializable{

	private static final long serialVersionUID = -4406865550814118889L;

	public BocmSndTraceUpdModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	//交易渠道
	private String sourceType; 
	//交易机构
	private String txBranch; 
	//现转标志
	private String txInd; 
	//通存通兑标志
	private String dcFlag; 
	//交易金额
	private String txAmt;
    //付款行人行行号
    private String sndBankno;
    //收款人行行号
    private String rcvBankno;
    //手续费
    private BigDecimal fee;
    //代理手续费手续方式
    private String proxyFlag;
    //代理手续费
    private BigDecimal proxyFee;
    //交行对账文件客户手续费手续方式
    private String bocmFeeFlag;
    //交行记账文件客户手续费
    private BigDecimal bocmFee;
    //账户余额
    private BigDecimal actBal;
	//付款人账户
	private String payerAcno; 
	//付款人户名
	private String payerName; 
	//收款人账户
	private String payeeAcno; 
	//收款人户名
	private String payeeName; 
	//村镇机构
	private String bocmBranch; 
	//核心记账状态
	private String hostState; 
	//交易柜员
	private String txTel; 
	//复核员
	private String chkTel; 
	//授权员
	private String authTel; 
	//打印次数
	private String print; 
	//摘要
	private String info; 
	//渠道日期
    private Integer platDate; 
    //渠道流水
    private Integer platTrace;
    //交易时间
    private Integer platTime;
    //核心日期
    private Integer hostDate;
    //核心流水
    private String hostTraceno;
    //交行日期
    private Integer bocmDate;
    //交行时间
    private Integer bocmTime;
    //交行流水
    private String bocmTraceno;
    //交行返回相应码
    private String bocmRepcd;
    //交行返回相应信息
    private String bocmRepmsg;
    //对账标志
    private String checkFlag;
    //交行记账状态
    private String bocmState;
    //核心反馈响应码
    private String retCode;
    //核心反馈响应信息
    private String retMsg;
    //核心记账机构
    private String hostBranch;

	public String getHostBranch() {
		return hostBranch;
	}

	public void setHostBranch(String hostBranch) {
		this.hostBranch = hostBranch;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTxBranch() {
		return txBranch;
	}

	public void setTxBranch(String txBranch) {
		this.txBranch = txBranch;
	}

	public String getTxInd() {
		return txInd;
	}

	public void setTxInd(String txInd) {
		this.txInd = txInd;
	}

	public String getDcFlag() {
		return dcFlag;
	}

	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}

	public String getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}

	public String getPayerAcno() {
		return payerAcno;
	}

	public void setPayerAcno(String payerAcno) {
		this.payerAcno = payerAcno;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayeeAcno() {
		return payeeAcno;
	}

	public void setPayeeAcno(String payeeAcno) {
		this.payeeAcno = payeeAcno;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getBocmBranch() {
		return bocmBranch;
	}

	public void setBocmBranch(String bocmBranch) {
		this.bocmBranch = bocmBranch;
	}

	public String getHostState() {
		return hostState;
	}

	public void setHostState(String hostState) {
		this.hostState = hostState;
	}

	public String getTxTel() {
		return txTel;
	}

	public void setTxTel(String txTel) {
		this.txTel = txTel;
	}

	public String getChkTel() {
		return chkTel;
	}

	public void setChkTel(String chkTel) {
		this.chkTel = chkTel;
	}

	public String getAuthTel() {
		return authTel;
	}

	public void setAuthTel(String authTel) {
		this.authTel = authTel;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getPlatDate() {
		return platDate;
	}

	public void setPlatDate(Integer platDate) {
		this.platDate = platDate;
	}

	public Integer getPlatTrace() {
		return platTrace;
	}

	public void setPlatTrace(Integer platTrace) {
		this.platTrace = platTrace;
	}

	public Integer getPlatTime() {
		return platTime;
	}

	public void setPlatTime(Integer platTime) {
		this.platTime = platTime;
	}

	public Integer getHostDate() {
		return hostDate;
	}

	public void setHostDate(Integer hostDate) {
		this.hostDate = hostDate;
	}

	public String getHostTraceno() {
		return hostTraceno;
	}

	public void setHostTraceno(String hostTraceno) {
		this.hostTraceno = hostTraceno;
	}

	public Integer getBocmDate() {
		return bocmDate;
	}

	public void setBocmDate(Integer bocmDate) {
		this.bocmDate = bocmDate;
	}

	public String getBocmTraceno() {
		return bocmTraceno;
	}

	public void setBocmTraceno(String bocmTraceno) {
		this.bocmTraceno = bocmTraceno;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getBocmState() {
		return bocmState;
	}

	public void setBocmState(String bocmState) {
		this.bocmState = bocmState;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public Integer getBocmTime() {
		return bocmTime;
	}

	public void setBocmTime(Integer bocmTime) {
		this.bocmTime = bocmTime;
	}

	public BigDecimal getActBal() {
		return actBal;
	}

	public void setActBal(BigDecimal actBal) {
		this.actBal = actBal;
	}

	public String getSndBankno() {
		return sndBankno;
	}

	public void setSndBankno(String sndBankno) {
		this.sndBankno = sndBankno;
	}

	public String getRcvBankno() {
		return rcvBankno;
	}

	public void setRcvBankno(String rcvBankno) {
		this.rcvBankno = rcvBankno;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getBocmRepcd() {
		return bocmRepcd;
	}

	public void setBocmRepcd(String bocmRepcd) {
		this.bocmRepcd = bocmRepcd;
	}

	public String getBocmRepmsg() {
		return bocmRepmsg;
	}

	public void setBocmRepmsg(String bocmRepmsg) {
		this.bocmRepmsg = bocmRepmsg;
	}

	public String getProxyFlag() {
		return proxyFlag;
	}

	public void setProxyFlag(String proxyFlag) {
		this.proxyFlag = proxyFlag;
	}

	public BigDecimal getProxyFee() {
		return proxyFee;
	}

	public void setProxyFee(BigDecimal proxyFee) {
		this.proxyFee = proxyFee;
	}

	public String getBocmFeeFlag() {
		return bocmFeeFlag;
	}

	public void setBocmFeeFlag(String bocmFeeFlag) {
		this.bocmFeeFlag = bocmFeeFlag;
	}

	public BigDecimal getBocmFee() {
		return bocmFee;
	}

	public void setBocmFee(BigDecimal bocmFee) {
		this.bocmFee = bocmFee;
	}


	
	
}