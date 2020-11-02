package com.example.mydialog.select.two;

import java.util.List;
/**
 * 
 * @description
 * @author puyantao
 * @date 2020/11/2 15:23
 */
public class ShowLine {
	public List<ShowChar> CharsData = null;

	@Override
	public String toString() {
		return "ShowLine [Linedata=" + getLineData() + "]";
	}
    
	public String getLineData(){
		String linedata = "";	
		if(CharsData==null||CharsData.size()==0) return linedata;
		for(ShowChar c:CharsData){
			linedata = linedata+c.chardata;
		}
		return linedata;
	}
}
