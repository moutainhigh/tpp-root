package com.fxbank.tpp.mivs.trade.mivs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.mivs.dto.mivs.MIVS_801_001_01;
import com.fxbank.tpp.mivs.model.CCMS_990_001_02;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsSysStsNtfctnModel;
import com.fxbank.tpp.mivs.model.response.MIVS_801_001_01_SysStsNtfctn;
import com.fxbank.tpp.mivs.service.IForwardToPmtsService;
import com.fxbank.tpp.mivs.service.IMivsSysStsNtfctnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:  企业信息联网核查业务受理时间通知报文 mivs.801.001.01
 * @Author: 王鹏
 * @Date: 2019/5/5 8:12
 */
@Service("MIVS_801_001_01")
public class SysStsNtfctn extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(RtrTxPmtVrfctn.class);

    @Resource
    private MyJedis myJedis;

    @Resource
    private LogPool logPool;

    @Reference(version = "1.0.0")
    private IForwardToPmtsService pmtsService;

    @Reference(version = "1.0.0")
    private IMivsSysStsNtfctnService mivsSysStsNtfctnService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();
        MIVS_801_001_01 mivs801 = (MIVS_801_001_01) dto;
        MIVS_801_001_01_SysStsNtfctn.MsgHdr msgHdr = mivs801.getSysStsNtfctn().getMsgHdr();
        MIVS_801_001_01_SysStsNtfctn.SysStsInf sysStsInf = mivs801.getSysStsNtfctn().getSysStsInf();
        myLog.info(logger, "收到人行企业信息联网核查业务受理时间通知报文");
        MivsSysStsNtfctnModel sysStsNtfctnModel = new MivsSysStsNtfctnModel();
        sysStsNtfctnModel.setPlat_date(mivs801.getSysDate());
        sysStsNtfctnModel.setPlat_trace(mivs801.getSysTraceno());
        sysStsNtfctnModel.setPlat_time(mivs801.getSysTime());
        sysStsNtfctnModel.setMivs_sts("06");
        sysStsNtfctnModel.setMsg_id(msgHdr.getMsgId());
        sysStsNtfctnModel.setCre_dt_tm(msgHdr.getCreDtTm());
        sysStsNtfctnModel.setInstg_drct_pty(msgHdr.getInstgPty().getInstgDrctPty());
        sysStsNtfctnModel.setInstg_pty(msgHdr.getInstgPty().getInstgPty());
        sysStsNtfctnModel.setInstd_drct_pty(msgHdr.getInstdPty().getInstdDrctPty());
        sysStsNtfctnModel.setInstd_pty(msgHdr.getInstdPty().getInstdPty());
        sysStsNtfctnModel.setCur_sys_dt(sysStsInf.getCurSysDt());
        sysStsNtfctnModel.setNxt_sys_dt(sysStsInf.getNxtSysDt());
        sysStsNtfctnModel.setSys_ind(sysStsInf.getSvcInf().getSysInd());
        sysStsNtfctnModel.setSvc_ind(sysStsInf.getSvcInf().getSvcInd());
        sysStsNtfctnModel.setNxt_sys_cl_tm(sysStsInf.getSvcInf().getNxtSysClTm());
        sysStsNtfctnModel.setNxt_sys_op_tm(sysStsInf.getSvcInf().getNxtSysOpTm());

        //信息落地
        mivsSysStsNtfctnService.insertInfo(sysStsNtfctnModel);

        //返回990报文
        CCMS_990_001_02 ccms990 = new CCMS_990_001_02();
        ccms990.getComConf().getConfInf().setOrigSndr(mivs801.getHead().getOrigSender());
        ccms990.getComConf().getConfInf().setOrigSndDt(mivs801.getHead().getOrigSendDate().toString());
        ccms990.getComConf().getConfInf().setMT(mivs801.getHead().getMesgType());
        ccms990.getComConf().getConfInf().setMsgId(mivs801.getHead().getMesgID());
        ccms990.getComConf().getConfInf().setMsgRefId(mivs801.getHead().getMesgRefID());
        ccms990.getComConf().getConfInf().setMsgPrcCd("PR01");
        ccms990 = (CCMS_990_001_02) pmtsService.sendToPmtsNoWait(ccms990);

        return mivs801;
    }
}