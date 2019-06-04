package com.fxbank.tpp.bocm.trade.bocm;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fxbank.cip.base.pkg.fixed.FixedUtil;
import com.fxbank.tpp.bocm.dto.bocm.REP_20001;
import com.fxbank.tpp.bocm.model.REQ_20001;

/**
*
* @ClassName: WD_20001_TEST 
* @Description: 他代本  本行IC卡付款转账
* @author YePuLiang
* @date 2019年6月3日 下午2:21:19 
*
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WD_20001_TEST extends BASE_TEST {
	
	private REQ_20001 req;
	
	@Before
	public void init(){
		req = new REQ_20001();
		super.initReqHeader("20001", req);
	}
	
	@Test
	public void ok() throws Exception {
		req.setTxnAmt(100.09d);
		req.setPin("0123456789ABCDEF");
		req.setOprFlg("0");
		req.setTxnMod("0");
		req.setRecBnk("301651000015");
		req.setRactTp("2");
//		req.setrActNo("6222600530011742438");
		
		req.setPactNo("623166001016830991");
		req.setPayNam("测试");
//		req.setSecMag("622126010001048643=4912567019123456");

		req.setSeqNo("001");
		req.setARQC("1111");
		req.setICAID("A0000003330101");
		req.setICOutDate("20251231");
		req.setICData("11111");
		
//		req.setRecNam("钱晓勋");
//		req.setSecMag("6222600530011742438=4912120343981195");
//		req.setThdMag("996222600530011742438=1561560500050002116013000000010000000000==0981195343");
		
//		String repData = super.comm(req.creaFixPack());
//		REP_10001 rep = new REP_10001();
//		rep.chanFixPack(repData);
//		assertEquals(rep.getHeader().gettMsgTyp(), "N");
//		assertEquals(rep.getHeader().gettRspCd(), "FX0000");
		
		String repData = super.comm(FixedUtil.toFixed(req,"UTF-8"));
		REP_20001 rep = new REP_20001();
		rep = (REP_20001)new FixedUtil(repData,"UTF-8").toBean(rep.getClass());		
		assertEquals(rep.getTmsgTyp(), "N");
		assertEquals(rep.getTrspCd(), "FX0000");
	}
	
}
