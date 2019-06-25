package com.fxbank.tpp.bocm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30011000104 
* @Description: 模拟核心记账
* @author YePuLiang
* @date 2019年4月25日 上午10:29:54 
*  
*/
public class REQ_30011000104 extends REQ_BASE {
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30011000104(){
		super.txDesc = "模拟核心记账";
	}
	


	public REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}



	public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}



	public REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}



	public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}



	public REQ_BODY getReqBody() {
		return reqBody;
	}



	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}



	public class REQ_BODY {

		@JSONField(name = "BASE_ACCT_NO")
		private String baseAcctNo;//账号/卡号
		@JSONField(name = "ACCT_NAME")
		private String acctName;//账户名称
		@JSONField(name = "TRAN_TYPE")
		private String tranType;//交易类型
		@JSONField(name = "TRAN_CCY")
		private String tranCcy;//交易币种
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;//交易金额
		@JSONField(name = "WITHDRAWAL_TYPE")
		private String withdrawalType;//支取方式
		@JSONField(name = "PASSWORD")
		private String password;//密码
		@JSONField(name = "OTH_BASE_ACCT_NO")
		private String othBaseAcctNo;//对方账号/卡号
		@JSONField(name = "OTH_BASE_ACCT_NAME")
		private String othBaseAcctName;//对方户名
		@JSONField(name = "OTH_BANK_NAME")
		private String othBankName;//对方银行名称
		@JSONField(name = "NARRATIVE")
		private String narrative;//摘要
		@JSONField(name = "CHANNEL_TYPE")
		private String channelType;//记账渠道类型
		@JSONField(name = "SETTLEMENT_DATE")
		private String settlementDate;//清算日期
		@JSONField(name = "COLLATE_FLAG")
		private String collateFlag;//对账标识
		@JSONField(name = "CHARGE_METHOD")
		private String chargeMethod;//手续费收取方式
		@JSONField(name = "SEND_BANK_CODE")
		private String sendBankCode;//发起行行号
		@JSONField(name = "BANK_CODE")
		private String bankCode;//我方银行行号
		@JSONField(name = "OTH_BANK_CODE")
		private String othBankCode;//对方银行行号
		@JSONField(name = "DIRECTION")
		private String direction;//来往标识
		
	public String  getBaseAcctNo(){
			return baseAcctNo;
		}
		public void setBaseAcctNo(String baseAcctNo){
			this.baseAcctNo = baseAcctNo;
		}
		public String  getAcctName(){
			return acctName;
		}
		public void setAcctName(String acctName){
			this.acctName = acctName;
		}
		public String  getTranType(){
			return tranType;
		}
		public void setTranType(String tranType){
			this.tranType = tranType;
		}
		public String  getTranCcy(){
			return tranCcy;
		}
		public void setTranCcy(String tranCcy){
			this.tranCcy = tranCcy;
		}
		public String  getTranAmt(){
			return tranAmt;
		}
		public void setTranAmt(String tranAmt){
			this.tranAmt = tranAmt;
		}
		public String  getWithdrawalType(){
			return withdrawalType;
		}
		public void setWithdrawalType(String withdrawalType){
			this.withdrawalType = withdrawalType;
		}
		public String  getPassword(){
			return password;
		}
		public void setPassword(String password){
			this.password = password;
		}
		public String  getOthBaseAcctNo(){
			return othBaseAcctNo;
		}
		public void setOthBaseAcctNo(String othBaseAcctNo){
			this.othBaseAcctNo = othBaseAcctNo;
		}
		public String  getOthBaseAcctName(){
			return othBaseAcctName;
		}
		public void setOthBaseAcctName(String othBaseAcctName){
			this.othBaseAcctName = othBaseAcctName;
		}
		public String  getOthBankName(){
			return othBankName;
		}
		public void setOthBankName(String othBankName){
			this.othBankName = othBankName;
		}
		public String  getNarrative(){
			return narrative;
		}
		public void setNarrative(String narrative){
			this.narrative = narrative;
		}
		public String  getChannelType(){
			return channelType;
		}
		public void setChannelType(String channelType){
			this.channelType = channelType;
		}
		public String  getSettlementDate(){
			return settlementDate;
		}
		public void setSettlementDate(String settlementDate){
			this.settlementDate = settlementDate;
		}
		public String  getCollateFlag(){
			return collateFlag;
		}
		public void setCollateFlag(String collateFlag){
			this.collateFlag = collateFlag;
		}
		public String  getChargeMethod(){
			return chargeMethod;
		}
		public void setChargeMethod(String chargeMethod){
			this.chargeMethod = chargeMethod;
		}
		public String  getSendBankCode(){
			return sendBankCode;
		}
		public void setSendBankCode(String sendBankCode){
			this.sendBankCode = sendBankCode;
		}
		public String  getBankCode(){
			return bankCode;
		}
		public void setBankCode(String bankCode){
			this.bankCode = bankCode;
		}
		public String  getOthBankCode(){
			return othBankCode;
		}
		public void setOthBankCode(String othBankCode){
			this.othBankCode = othBankCode;
		}
		public String  getDirection(){
			return direction;
		}
		public void setDirection(String direction){
			this.direction = direction;
		}
		
	}
}
