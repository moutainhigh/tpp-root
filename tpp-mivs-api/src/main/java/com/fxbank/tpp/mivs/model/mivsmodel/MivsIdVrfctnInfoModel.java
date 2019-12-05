package com.fxbank.tpp.mivs.model.mivsmodel;

import java.io.Serializable;

/**
 * @Description: 手机号核查业务表
 * @Author: 王鹏
 * @Date: 2019/5/21 16:22
 */
public class MivsIdVrfctnInfoModel implements Serializable {
    private static final long serialVersionUID = -8434434666794145877L;
    private Integer plat_date; //平台日期
    private Integer plat_trace; //平台流水
    private Integer plat_time; //平台时间
    private String system_id; //发起方系统编码
    private String tran_date; //交易日期
    private String seq_no; //渠道流水
    private String tran_time; //交易时间
    private String user_id; //柜员号
    private String branch_id; //机构号
    private String mivs_sts; //业务处理状态；00-已发送，01-已收到回执，02-已收到911回执 处理失败，03-已收到业务回执 处理失败，04-已收到业务回执 处理成功
    private String msg_id; //报文标识号
    private String cre_dt_tm; //报文发送时间
    private String instg_drct_pty; //发起直接参与机构
    private String drct_pty_nm; //发起直接参与机构行名
    private String instg_pty; //发起参与机构
    private String pty_nm; //发起参与机构行名
    private String instd_drct_pty; //接收直接参与机构
    private String instd_pty; //接收参与机构
    private String rcv_msg_id; //应答报文标识号
    private String rcv_cre_dt_tm; //应答报文发送时间
    private String mob_nb; //手机号
    private String nm; //姓名
    private String id_tp; //证件类型
    private String id; //证件号码
    private String uni_soc_cdt_cd; //统一社会信用代码
    private String biz_reg_nb; //工商注册号
    private String op_nm; //操作员姓名
    private String rslt; //手机号核查结果
    private String mob_crr; //手机运营商
    private String loc_mob_nb; //手机号归属地代码
    private String loc_nm_mob_nb; //手机号归属地名称
    private String cd_tp; //客户类型
    private String sts; //手机号码状态
    private String proc_sts; //申请报文拒绝状态
    private String proc_cd; //申请报文拒绝码
    private String rjct_inf; //申请报文拒绝信息
    private String remark1; //备用字段1
    private String remark2; //备用字段2
    private String remark3; //备用字段3

    private Integer start_dt;
    private Integer end_dt;

    private String sys_ind;
    private String orig_dlv_msgid;
    private String orig_rcv_msgid;
    private String cntt;
    private String contact_nm;
    private String contact_nb;
    private String pty_id;//拒绝业务的参与机构行号
    private String pty_prc_cd; //参与机构业务拒绝码
    private String prc_dt; //处理日期（终态日期）
    private String netg_rnd; //轧差场次

    public Integer getPlat_date() {
        return plat_date;
    }

    public void setPlat_date(Integer plat_date) {
        this.plat_date = plat_date;
    }

    public Integer getPlat_trace() {
        return plat_trace;
    }

    public void setPlat_trace(Integer plat_trace) {
        this.plat_trace = plat_trace;
    }

    public Integer getPlat_time() {
        return plat_time;
    }

    public void setPlat_time(Integer plat_time) {
        this.plat_time = plat_time;
    }

    public String getSystem_id() {
        return system_id;
    }

    public void setSystem_id(String system_id) {
        this.system_id = system_id;
    }

    public String getTran_date() {
        return tran_date;
    }

    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }

    public String getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(String seq_no) {
        this.seq_no = seq_no;
    }

    public String getTran_time() {
        return tran_time;
    }

    public void setTran_time(String tran_time) {
        this.tran_time = tran_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getMivs_sts() {
        return mivs_sts;
    }

    public void setMivs_sts(String mivs_sts) {
        this.mivs_sts = mivs_sts;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getCre_dt_tm() {
        return cre_dt_tm;
    }

    public void setCre_dt_tm(String cre_dt_tm) {
        this.cre_dt_tm = cre_dt_tm;
    }

    public String getInstg_drct_pty() {
        return instg_drct_pty;
    }

    public void setInstg_drct_pty(String instg_drct_pty) {
        this.instg_drct_pty = instg_drct_pty;
    }

    public String getDrct_pty_nm() {
        return drct_pty_nm;
    }

    public void setDrct_pty_nm(String drct_pty_nm) {
        this.drct_pty_nm = drct_pty_nm;
    }

    public String getInstg_pty() {
        return instg_pty;
    }

    public void setInstg_pty(String instg_pty) {
        this.instg_pty = instg_pty;
    }

    public String getPty_nm() {
        return pty_nm;
    }

    public void setPty_nm(String pty_nm) {
        this.pty_nm = pty_nm;
    }

    public String getInstd_drct_pty() {
        return instd_drct_pty;
    }

    public void setInstd_drct_pty(String instd_drct_pty) {
        this.instd_drct_pty = instd_drct_pty;
    }

    public String getInstd_pty() {
        return instd_pty;
    }

    public void setInstd_pty(String instd_pty) {
        this.instd_pty = instd_pty;
    }

    public String getRcv_msg_id() {
        return rcv_msg_id;
    }

    public void setRcv_msg_id(String rcv_msg_id) {
        this.rcv_msg_id = rcv_msg_id;
    }

    public String getRcv_cre_dt_tm() {
        return rcv_cre_dt_tm;
    }

    public void setRcv_cre_dt_tm(String rcv_cre_dt_tm) {
        this.rcv_cre_dt_tm = rcv_cre_dt_tm;
    }

    public String getMob_nb() {
        return mob_nb;
    }

    public void setMob_nb(String mob_nb) {
        this.mob_nb = mob_nb;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getId_tp() {
        return id_tp;
    }

    public void setId_tp(String id_tp) {
        this.id_tp = id_tp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUni_soc_cdt_cd() {
        return uni_soc_cdt_cd;
    }

    public void setUni_soc_cdt_cd(String uni_soc_cdt_cd) {
        this.uni_soc_cdt_cd = uni_soc_cdt_cd;
    }

    public String getBiz_reg_nb() {
        return biz_reg_nb;
    }

    public void setBiz_reg_nb(String biz_reg_nb) {
        this.biz_reg_nb = biz_reg_nb;
    }

    public String getOp_nm() {
        return op_nm;
    }

    public void setOp_nm(String op_nm) {
        this.op_nm = op_nm;
    }

    public String getRslt() {
        return rslt;
    }

    public void setRslt(String rslt) {
        this.rslt = rslt;
    }

    public String getMob_crr() {
        return mob_crr;
    }

    public void setMob_crr(String mob_crr) {
        this.mob_crr = mob_crr;
    }

    public String getLoc_mob_nb() {
        return loc_mob_nb;
    }

    public void setLoc_mob_nb(String loc_mob_nb) {
        this.loc_mob_nb = loc_mob_nb;
    }

    public String getLoc_nm_mob_nb() {
        return loc_nm_mob_nb;
    }

    public void setLoc_nm_mob_nb(String loc_nm_mob_nb) {
        this.loc_nm_mob_nb = loc_nm_mob_nb;
    }

    public String getCd_tp() {
        return cd_tp;
    }

    public void setCd_tp(String cd_tp) {
        this.cd_tp = cd_tp;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public String getProc_sts() {
        return proc_sts;
    }

    public void setProc_sts(String proc_sts) {
        this.proc_sts = proc_sts;
    }

    public String getProc_cd() {
        return proc_cd;
    }

    public void setProc_cd(String proc_cd) {
        this.proc_cd = proc_cd;
    }

    public String getRjct_inf() {
        return rjct_inf;
    }

    public void setRjct_inf(String rjct_inf) {
        this.rjct_inf = rjct_inf;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public Integer getStart_dt() {
        return start_dt;
    }

    public void setStart_dt(Integer start_dt) {
        this.start_dt = start_dt;
    }

    public Integer getEnd_dt() {
        return end_dt;
    }

    public void setEnd_dt(Integer end_dt) {
        this.end_dt = end_dt;
    }

    public String getSys_ind() {
        return sys_ind;
    }

    public void setSys_ind(String sys_ind) {
        this.sys_ind = sys_ind;
    }

    public String getOrig_dlv_msgid() {
        return orig_dlv_msgid;
    }

    public void setOrig_dlv_msgid(String orig_dlv_msgid) {
        this.orig_dlv_msgid = orig_dlv_msgid;
    }

    public String getOrig_rcv_msgid() {
        return orig_rcv_msgid;
    }

    public void setOrig_rcv_msgid(String orig_rcv_msgid) {
        this.orig_rcv_msgid = orig_rcv_msgid;
    }

    public String getCntt() {
        return cntt;
    }

    public void setCntt(String cntt) {
        this.cntt = cntt;
    }

    public String getContact_nm() {
        return contact_nm;
    }

    public void setContact_nm(String contact_nm) {
        this.contact_nm = contact_nm;
    }

    public String getContact_nb() {
        return contact_nb;
    }

    public void setContact_nb(String contact_nb) {
        this.contact_nb = contact_nb;
    }

    public String getPty_id() {
        return pty_id;
    }

    public void setPty_id(String pty_id) {
        this.pty_id = pty_id;
    }

    public String getPty_prc_cd() {
        return pty_prc_cd;
    }

    public void setPty_prc_cd(String pty_prc_cd) {
        this.pty_prc_cd = pty_prc_cd;
    }

    public String getPrc_dt() {
        return prc_dt;
    }

    public void setPrc_dt(String prc_dt) {
        this.prc_dt = prc_dt;
    }

    public String getNetg_rnd() {
        return netg_rnd;
    }

    public void setNetg_rnd(String netg_rnd) {
        this.netg_rnd = netg_rnd;
    }

    @Override
    public String toString() {
        return "MivsIdVrfctnInfoModel{" +
                "plat_date=" + plat_date +
                ", plat_trace=" + plat_trace +
                ", plat_time=" + plat_time +
                ", system_id='" + system_id + '\'' +
                ", tran_date='" + tran_date + '\'' +
                ", seq_no='" + seq_no + '\'' +
                ", tran_time='" + tran_time + '\'' +
                ", user_id='" + user_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", mivs_sts='" + mivs_sts + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", cre_dt_tm='" + cre_dt_tm + '\'' +
                ", instg_drct_pty='" + instg_drct_pty + '\'' +
                ", drct_pty_nm='" + drct_pty_nm + '\'' +
                ", instg_pty='" + instg_pty + '\'' +
                ", pty_nm='" + pty_nm + '\'' +
                ", instd_drct_pty='" + instd_drct_pty + '\'' +
                ", instd_pty='" + instd_pty + '\'' +
                ", mob_nb='" + mob_nb + '\'' +
                ", nm='" + nm + '\'' +
                ", id_tp='" + id_tp + '\'' +
                ", id='" + id + '\'' +
                ", uni_soc_cdt_cd='" + uni_soc_cdt_cd + '\'' +
                ", biz_reg_nb='" + biz_reg_nb + '\'' +
                ", op_nm='" + op_nm + '\'' +
                ", rslt='" + rslt + '\'' +
                ", mob_crr='" + mob_crr + '\'' +
                ", loc_mob_nb='" + loc_mob_nb + '\'' +
                ", loc_nm_mob_nb='" + loc_nm_mob_nb + '\'' +
                ", cd_tp='" + cd_tp + '\'' +
                ", sts='" + sts + '\'' +
                ", proc_sts='" + proc_sts + '\'' +
                ", proc_cd='" + proc_cd + '\'' +
                ", rjct_inf='" + rjct_inf + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark3='" + remark3 + '\'' +
                ", start_dt=" + start_dt +
                ", end_dt=" + end_dt +
                ", sys_ind='" + sys_ind + '\'' +
                ", orig_dlv_msgid='" + orig_dlv_msgid + '\'' +
                ", orig_rcv_msgid='" + orig_rcv_msgid + '\'' +
                ", cntt='" + cntt + '\'' +
                ", contact_nm='" + contact_nm + '\'' +
                ", contact_nb='" + contact_nb + '\'' +
                ", pty_id='" + pty_id + '\'' +
                ", pty_prc_cd='" + pty_prc_cd + '\'' +
                ", prc_dt='" + prc_dt + '\'' +
                ", netg_rnd='" + netg_rnd + '\'' +
                '}';
    }
}
