package com.fxbank.tpp.mivs.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "MIVS_TXPMTVFCTN_INFO")
public class MivsTxpmtvfctnInfo {
    /**
     * null
     */
    @Id
    @Column(name = "PLAT_DATE")
    private Long platDate;

    /**
     * null
     */
    @Id
    @Column(name = "PLAT_TRACE")
    private Long platTrace;

    /**
     * null
     */
    @Column(name = "PLAT_TIME")
    private Long platTime;

    /**
     * null
     */
    @Column(name = "SYSTEM_ID")
    private String systemId;

    /**
     * null
     */
    @Column(name = "TRAN_DATE")
    private String tranDate;

    /**
     * null
     */
    @Column(name = "SEQ_NO")
    private String seqNo;

    /**
     * null
     */
    @Column(name = "TRAN_TIME")
    private String tranTime;

    /**
     * null
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * null
     */
    @Column(name = "BRANCH_ID")
    private String branchId;

    /**
     * null
     */
    @Column(name = "MIVS_STS")
    private String mivsSts;

    /**
     * null
     */
    @Column(name = "MSG_ID")
    private String msgId;

    /**
     * null
     */
    @Column(name = "CRE_DT_TM")
    private String creDtTm;

    /**
     * null
     */
    @Column(name = "INSTG_DRCT_PTY")
    private String instgDrctPty;

    /**
     * null
     */
    @Column(name = "DRCT_PTY_NM")
    private String drctPtyNm;

    /**
     * null
     */
    @Column(name = "INSTG_PTY")
    private String instgPty;

    /**
     * null
     */
    @Column(name = "PTY_NM")
    private String ptyNm;

    /**
     * null
     */
    @Column(name = "INSTD_DRCT_PTY")
    private String instdDrctPty;

    /**
     * null
     */
    @Column(name = "INSTD_PTY")
    private String instdPty;

    /**
     * null
     */
    @Column(name = "CO_NM")
    private String coNm;

    /**
     * null
     */
    @Column(name = "UNI_SOC_CDT_CD")
    private String uniSocCdtCd;

    /**
     * null
     */
    @Column(name = "TXPYR_ID_NB")
    private String txpyrIdNb;

    /**
     * null
     */
    @Column(name = "OP_NM")
    private String opNm;

    /**
     * null
     */
    @Column(name = "RSLT")
    private String rslt;

    /**
     * null
     */
    @Column(name = "DATA_RESRC_DT")
    private String dataResrcDt;

    /**
     * null
     */
    @Column(name = "TXPMT_INF_CNT")
    private Short txpmtInfCnt;

    /**
     * null
     */
    @Column(name = "PROC_STS")
    private String procSts;

    /**
     * null
     */
    @Column(name = "PROC_CD")
    private String procCd;

    /**
     * null
     */
    @Column(name = "RJCT_INF")
    private String rjctInf;

    /**
     * null
     */
    @Column(name = "REMARK1")
    private String remark1;

    /**
     * null
     */
    @Column(name = "REMARK2")
    private String remark2;

    /**
     * null
     */
    @Column(name = "REMARK3")
    private String remark3;

    /**
     * null
     * @return PLAT_DATE null
     */
    public Long getPlatDate() {
        return platDate;
    }

    /**
     * null
     * @param platDate null
     */
    public void setPlatDate(Long platDate) {
        this.platDate = platDate;
    }

    /**
     * null
     * @return PLAT_TRACE null
     */
    public Long getPlatTrace() {
        return platTrace;
    }

    /**
     * null
     * @param platTrace null
     */
    public void setPlatTrace(Long platTrace) {
        this.platTrace = platTrace;
    }

    /**
     * null
     * @return PLAT_TIME null
     */
    public Long getPlatTime() {
        return platTime;
    }

    /**
     * null
     * @param platTime null
     */
    public void setPlatTime(Long platTime) {
        this.platTime = platTime;
    }

    /**
     * null
     * @return SYSTEM_ID null
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * null
     * @param systemId null
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * null
     * @return TRAN_DATE null
     */
    public String getTranDate() {
        return tranDate;
    }

    /**
     * null
     * @param tranDate null
     */
    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    /**
     * null
     * @return SEQ_NO null
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * null
     * @param seqNo null
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * null
     * @return TRAN_TIME null
     */
    public String getTranTime() {
        return tranTime;
    }

    /**
     * null
     * @param tranTime null
     */
    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    /**
     * null
     * @return USER_ID null
     */
    public String getUserId() {
        return userId;
    }

    /**
     * null
     * @param userId null
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * null
     * @return BRANCH_ID null
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * null
     * @param branchId null
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    /**
     * null
     * @return MIVS_STS null
     */
    public String getMivsSts() {
        return mivsSts;
    }

    /**
     * null
     * @param mivsSts null
     */
    public void setMivsSts(String mivsSts) {
        this.mivsSts = mivsSts;
    }

    /**
     * null
     * @return MSG_ID null
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * null
     * @param msgId null
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * null
     * @return CRE_DT_TM null
     */
    public String getCreDtTm() {
        return creDtTm;
    }

    /**
     * null
     * @param creDtTm null
     */
    public void setCreDtTm(String creDtTm) {
        this.creDtTm = creDtTm;
    }

    /**
     * null
     * @return INSTG_DRCT_PTY null
     */
    public String getInstgDrctPty() {
        return instgDrctPty;
    }

    /**
     * null
     * @param instgDrctPty null
     */
    public void setInstgDrctPty(String instgDrctPty) {
        this.instgDrctPty = instgDrctPty;
    }

    /**
     * null
     * @return DRCT_PTY_NM null
     */
    public String getDrctPtyNm() {
        return drctPtyNm;
    }

    /**
     * null
     * @param drctPtyNm null
     */
    public void setDrctPtyNm(String drctPtyNm) {
        this.drctPtyNm = drctPtyNm;
    }

    /**
     * null
     * @return INSTG_PTY null
     */
    public String getInstgPty() {
        return instgPty;
    }

    /**
     * null
     * @param instgPty null
     */
    public void setInstgPty(String instgPty) {
        this.instgPty = instgPty;
    }

    /**
     * null
     * @return PTY_NM null
     */
    public String getPtyNm() {
        return ptyNm;
    }

    /**
     * null
     * @param ptyNm null
     */
    public void setPtyNm(String ptyNm) {
        this.ptyNm = ptyNm;
    }

    /**
     * null
     * @return INSTD_DRCT_PTY null
     */
    public String getInstdDrctPty() {
        return instdDrctPty;
    }

    /**
     * null
     * @param instdDrctPty null
     */
    public void setInstdDrctPty(String instdDrctPty) {
        this.instdDrctPty = instdDrctPty;
    }

    /**
     * null
     * @return INSTD_PTY null
     */
    public String getInstdPty() {
        return instdPty;
    }

    /**
     * null
     * @param instdPty null
     */
    public void setInstdPty(String instdPty) {
        this.instdPty = instdPty;
    }

    /**
     * null
     * @return CO_NM null
     */
    public String getCoNm() {
        return coNm;
    }

    /**
     * null
     * @param coNm null
     */
    public void setCoNm(String coNm) {
        this.coNm = coNm;
    }

    /**
     * null
     * @return UNI_SOC_CDT_CD null
     */
    public String getUniSocCdtCd() {
        return uniSocCdtCd;
    }

    /**
     * null
     * @param uniSocCdtCd null
     */
    public void setUniSocCdtCd(String uniSocCdtCd) {
        this.uniSocCdtCd = uniSocCdtCd;
    }

    /**
     * null
     * @return TXPYR_ID_NB null
     */
    public String getTxpyrIdNb() {
        return txpyrIdNb;
    }

    /**
     * null
     * @param txpyrIdNb null
     */
    public void setTxpyrIdNb(String txpyrIdNb) {
        this.txpyrIdNb = txpyrIdNb;
    }

    /**
     * null
     * @return OP_NM null
     */
    public String getOpNm() {
        return opNm;
    }

    /**
     * null
     * @param opNm null
     */
    public void setOpNm(String opNm) {
        this.opNm = opNm;
    }

    /**
     * null
     * @return RSLT null
     */
    public String getRslt() {
        return rslt;
    }

    /**
     * null
     * @param rslt null
     */
    public void setRslt(String rslt) {
        this.rslt = rslt;
    }

    /**
     * null
     * @return DATA_RESRC_DT null
     */
    public String getDataResrcDt() {
        return dataResrcDt;
    }

    /**
     * null
     * @param dataResrcDt null
     */
    public void setDataResrcDt(String dataResrcDt) {
        this.dataResrcDt = dataResrcDt;
    }

    /**
     * null
     * @return TXPMT_INF_CNT null
     */
    public Short getTxpmtInfCnt() {
        return txpmtInfCnt;
    }

    /**
     * null
     * @param txpmtInfCnt null
     */
    public void setTxpmtInfCnt(Short txpmtInfCnt) {
        this.txpmtInfCnt = txpmtInfCnt;
    }

    /**
     * null
     * @return PROC_STS null
     */
    public String getProcSts() {
        return procSts;
    }

    /**
     * null
     * @param procSts null
     */
    public void setProcSts(String procSts) {
        this.procSts = procSts;
    }

    /**
     * null
     * @return PROC_CD null
     */
    public String getProcCd() {
        return procCd;
    }

    /**
     * null
     * @param procCd null
     */
    public void setProcCd(String procCd) {
        this.procCd = procCd;
    }

    /**
     * null
     * @return RJCT_INF null
     */
    public String getRjctInf() {
        return rjctInf;
    }

    /**
     * null
     * @param rjctInf null
     */
    public void setRjctInf(String rjctInf) {
        this.rjctInf = rjctInf;
    }

    /**
     * null
     * @return REMARK1 null
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * null
     * @param remark1 null
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    /**
     * null
     * @return REMARK2 null
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * null
     * @param remark2 null
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    /**
     * null
     * @return REMARK3 null
     */
    public String getRemark3() {
        return remark3;
    }

    /**
     * null
     * @param remark3 null
     */
    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }
}