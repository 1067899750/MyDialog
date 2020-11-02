package com.example.mydialog.select.two;

import java.util.List;
/**
 *
 * @description
 * @author puyantao
 * @date 2020/11/2 15:21
 */
public class BreakResult {

	public int ChartNums = 0;
	public Boolean IsFullLine = false;
	public List<ShowChar> showChars = null;

	public Boolean HasData() {
		return showChars != null && showChars.size() > 0;
	}
}
