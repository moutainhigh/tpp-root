package com.fxbank.tpp.mivs.trade.esb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.tpp.esb.service.IForwardToESBService;
import com.fxbank.tpp.mivs.dto.esb.REP_50023000209;
import com.fxbank.tpp.mivs.dto.esb.REQ_50023000209;
import com.fxbank.tpp.mivs.exception.MivsTradeExecuteException;
import com.fxbank.tpp.mivs.model.mivsmodel.MivsRegVrfctnInfoModel;
import com.fxbank.tpp.mivs.service.IMivsRegVrfctnInfoService;
import com.fxbank.tpp.mivs.sync.SyncCom;
import com.fxbank.tpp.mivs.trade.mivs.ComConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 登记信息核查结果汇总查询
 * @Author: 王鹏
 * @Date: 2019/7/2 15:38
 */
@Service("REQ_50023000209")
public class RegVrfctnSelect extends TradeBase implements TradeExecutionStrategy {

    private static Logger logger = LoggerFactory.getLogger(ComConf.class);

    @Resource
    private LogPool logPool;

    @Resource
    private SyncCom syncCom;

    @Reference(version = "1.0.0")
    private IForwardToESBService forwardToESBService;

    @Reference(version = "1.0.0")
    private IMivsRegVrfctnInfoService mivsRegVrfctnInfoService;

    @Override
    public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
        MyLog myLog = logPool.get();

        REQ_50023000209 req = (REQ_50023000209) dto;//接收ESB请求报文
        REQ_50023000209.REQ_BODY reqBody = req.getReqBody();

        //查询数据落库
        MivsRegVrfctnInfoModel regVrfctnInfoModel =  new MivsRegVrfctnInfoModel();
        regVrfctnInfoModel.setStart_dt(reqBody.getStartDt());
        regVrfctnInfoModel.setEnd_dt(reqBody.getEndDt());
        regVrfctnInfoModel.setBranch_id(reqBody.getOrigBranchId());
        regVrfctnInfoModel.setUser_id(reqBody.getOrigUserId());
        regVrfctnInfoModel.setOrig_dlv_msgid(reqBody.getOrgnlDlvrgMsgId());
        regVrfctnInfoModel.setEnt_nm(reqBody.getEntNm());
        regVrfctnInfoModel.setUni_soc_cdt_cd(reqBody.getUniSocCdtCd());
        regVrfctnInfoModel.setNm_of_lgl_prsn(reqBody.getNmOfLglPrsn());
        regVrfctnInfoModel.setId_of_lgl_prsn(reqBody.getIdOfLglPrsn());
        regVrfctnInfoModel.setTra_nm(reqBody.getTraNm());
        regVrfctnInfoModel.setNm(reqBody.getNm());
        regVrfctnInfoModel.setId(reqBody.getId());

        List<MivsRegVrfctnInfoModel> regVrfctnInfoModels = mivsRegVrfctnInfoService.selectResult(regVrfctnInfoModel); //查询数据库业务数据
        myLog.info(logger,"查询结果为：" + regVrfctnInfoModels.toString());
        if(regVrfctnInfoModels == null || regVrfctnInfoModels.isEmpty()) {
            MivsTradeExecuteException e = new MivsTradeExecuteException(MivsTradeExecuteException.MIVS_E_10003, "无查询记录");
            throw e;
        }

        REP_50023000209 rep = new REP_50023000209();
        if(regVrfctnInfoModels != null && !regVrfctnInfoModels.isEmpty()) {
            List<REP_50023000209.resultList> resultArrayList = new ArrayList<REP_50023000209.resultList>();
            int i = 0;
            for (MivsRegVrfctnInfoModel infoModel : regVrfctnInfoModels) {
                REP_50023000209.resultList resultList = new REP_50023000209.resultList();
                resultList.setOrigTranDate(infoModel.getTran_date());
                resultList.setOrigSeqNo(infoModel.getSeq_no());
                resultList.setOrigTranTime(infoModel.getTran_time());
                resultList.setOrgnlDlvrgMsgId(infoModel.getOrig_dlv_msgid());
//                resultList.setRslt(infoModel.getRslt());
                resultList.setProcSts(infoModel.getProc_sts());
                resultList.setProcCd(infoModel.getProc_cd());
                resultList.setRjctinf(infoModel.getRjct_inf());
                resultList.setRemarks1(infoModel.getRemark1());
                resultList.setRemarks2(infoModel.getRemark2());
                resultList.setRemarks3(infoModel.getRemark3());
                resultArrayList.add(resultList);
                myLog.info(logger, "ResultList的" + ++i + "值为：" + resultList.toString());
            }
            rep.getRepBody().setResultList(resultArrayList);
        }
        return rep;
    }
}
