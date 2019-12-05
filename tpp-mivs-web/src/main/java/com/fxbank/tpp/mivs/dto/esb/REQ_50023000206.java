package com.fxbank.tpp.mivs.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/**
 * @Description: 纳税信息核查结果明细查询请求
 * @Author: 王鹏
 * @Date: 2019/7/11 7:14
 */
public class REQ_50023000206 extends REQ_BASE {
    @JSONField(name = "APP_HEAD")
    private REQ_APP_HEAD reqAppHead;

    @JSONField(name = "SYS_HEAD")
    private REQ_SYS_HEAD reqSysHead;

    @JSONField(name = "BODY")
    private REQ_50023000206.REQ_BODY reqBody;

    public REQ_50023000206(){
        super.txDesc = "纳税信息核查结果明细查询";
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

    public REQ_50023000206.REQ_BODY getReqBody() {
        return reqBody;
    }

    public void setReqBody(REQ_50023000206.REQ_BODY reqBody) {
        this.reqBody = reqBody;
    }

    public class REQ_BODY {

        @JSONField(name = "ORIG_CHK_DATE")
        private String origTranDate;    // 核查开始时间

        @JSONField(name = "ORIG_CHK_SEQ_NO")
        private String origSeqNo; //核查结束时间

        @JSONField(name = "ORIG_APPLY_MSG_ID")
        private String orgnlDlvrgMsgId; //原申请报文标识号  原 mivs.320.001.01或mivs.322.001.01 的报文标识号

        @JSONField(name = "ORIG_ANSWER_MSG_ID")
        private String orgnlRcvgMsgId; //原申请报文标识号  原 mivs.321.001.01或mivs.323.001.01 的报文标识号

        @JSONField(name = "ORIG_INSTG_PTY")
        private String origInstgPty;

        public String  getOrigTranDate() {
            return origTranDate;
        }

        public void setOrigTranDate(String  origTranDate) {
            this.origTranDate = origTranDate;
        }

        public String  getOrigSeqNo() {
            return origSeqNo;
        }

        public void setOrigSeqNo(String  origSeqNo) {
            this.origSeqNo = origSeqNo;
        }

        public String getOrgnlDlvrgMsgId() {
            return orgnlDlvrgMsgId;
        }

        public void setOrgnlDlvrgMsgId(String orgnlDlvrgMsgId) {
            this.orgnlDlvrgMsgId = orgnlDlvrgMsgId;
        }

        public String getOrgnlRcvgMsgId() {
            return orgnlRcvgMsgId;
        }

        public void setOrgnlRcvgMsgId(String orgnlRcvgMsgId) {
            this.orgnlRcvgMsgId = orgnlRcvgMsgId;
        }

        public String getOrigInstgPty() {
            return origInstgPty;
        }

        public void setOrigInstgPty(String origInstgPty) {
            this.origInstgPty = origInstgPty;
        }
    }
}