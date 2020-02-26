package com.fxbank.tpp.beps.pmts;

import com.fxbank.cip.base.log.MyLog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
* @Description: 客户身份认证回执报文
* @author 周勇沩
* @date 2020/2/21 10:12:54 
*  
*/
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
public class BEPS_353_001_01   extends MODEL_BASE {
	
	private static final long serialVersionUID = -763446990530918663L;
	private static final String MESGTYPE = "beps.353.001.01";
    private static final String XMLNS = "urn:cnaps:std:beps:2010:tech:xsd:beps.353.001.01";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    
    private BEPS_352_001_01_ResFrPtcSn PtcSnReq = new BEPS_352_001_01_ResFrPtcSn();

	public BEPS_353_001_01() {
        super(null, 0, 0, 0);
    }
	
    public BEPS_353_001_01(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.mesgType = MESGTYPE;
        super.XMLNS = XMLNS;
        super.XMLNS_XSI = XMLNS_XSI;
    }
    
    @Override
    public String signData() {
        return PtcSnReq.signData();
    }
}