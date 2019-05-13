/**   
* @Title: REQ_10103.java 
* @Package com.fxbank.tpp.bocm.dto.bocm 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年5月6日 上午10:07:41 
* @version V1.0   
*/
package com.fxbank.tpp.bocm.dto.bocm;

/** 
* @ClassName: REQ_10103 
* @Description: 对账文件获取
* @author YePuLiang
* @date 2019年5月6日 上午10:07:41 
*  
*/
public class REQ_10103 extends REQ_BASE {
	
	private String filNam;
	
    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        sb.append(String.format("%-28s", this.filNam==null?"":this.filNam));
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
        StringBuffer sb = new StringBuffer(pack);
        int i = 0;
        super.getHeader().chanFixPack(sb.substring(0, i=i+60));
        this.filNam = sb.substring(i, i=i+28).trim();
    }

	public String getFilNam() {
		return filNam;
	}

	public void setFilNam(String filNam) {
		this.filNam = filNam;
	}
	
	

}
