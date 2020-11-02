package com.example.mydialog.select.two;

import android.graphics.Point;
/**
 * 
 * @description
 * @author puyantao
 * @date 2020/11/2 15:23
 */
public class ShowChar {
	
	public char chardata ;
	
	public Boolean Selected =false;
	
	public Point TopLeftPosition = null;
	public Point TopRightPosition = null;
	public Point BottomLeftPosition = null;
	public Point BottomRightPosition = null;
	
	public float charWidth = 0;
	public int Index = 0;
	
	@Override
	public String toString() {
		return "ShowChar [chardata=" + chardata + ", Selected=" + Selected + ", TopLeftPosition=" + TopLeftPosition
				+ ", TopRightPosition=" + TopRightPosition + ", BottomLeftPosition=" + BottomLeftPosition
				+ ", BottomRightPosition=" + BottomRightPosition + ", charWidth=" + charWidth + ", Index=" + Index
				+ "]";
	}

	

	
	
	

}
