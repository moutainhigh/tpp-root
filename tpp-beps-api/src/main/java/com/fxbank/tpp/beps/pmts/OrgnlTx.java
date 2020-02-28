package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cip.base.anno.EsbSimuAnno;

import java.io.Serializable;

/**
 * @author : 周勇沩
 * @description: 原业务主键报文
 * @Date : 2020/2/27 15:29
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"InstgIndrctPty", "InstdIndrctPty", "OrgnlTxId", "OrgnlTxTpCd"})
public class OrgnlTx implements Serializable {

    private static final long serialVersionUID = 923672394179013622L;
    @EsbSimuAnno.EsbField(type = "String", value = "313131000016")
    private String InstgIndrctPty;
    @EsbSimuAnno.EsbField(type = "String", value = "313131000016")
    private String InstdIndrctPty;
    @EsbSimuAnno.EsbField(type = "Date", value = "yyyyMMddhhmmss")
    private String OrgnlTxId;
    @EsbSimuAnno.EsbField(type = "String", len = 2)
    private String OrgnlTxTpCd;

    public String getInstgIndrctPty() {
        return InstgIndrctPty;
    }

    public void setInstgIndrctPty(String instgIndrctPty) {
        InstgIndrctPty = instgIndrctPty;
    }

    public String getInstdIndrctPty() {
        return InstdIndrctPty;
    }

    public void setInstdIndrctPty(String instdIndrctPty) {
        InstdIndrctPty = instdIndrctPty;
    }

    public String getOrgnlTxId() {
        return OrgnlTxId;
    }

    public void setOrgnlTxId(String orgnlTxId) {
        OrgnlTxId = orgnlTxId;
    }

    public String getOrgnlTxTpCd() {
        return OrgnlTxTpCd;
    }

    public void setOrgnlTxTpCd(String orgnlTxTpCd) {
        OrgnlTxTpCd = orgnlTxTpCd;
    }
}