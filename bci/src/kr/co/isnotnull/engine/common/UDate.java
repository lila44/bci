package kr.co.isnotnull.engine.common;


import java.text.SimpleDateFormat;
import java.util.Date;


public class UDate {

	
	/**
	 * 현재 날짜를 반환한다.<br><br>
	 * 
	 * @param  _format 해당 날짜 포멧
	 * @return         해당 날짜
	 *********************************************************************************/
	public static String getDate(String _format) {
				
		return new SimpleDateFormat(_format).format(new Date());
	}
	
	/**
	 * 날짜로 변환해서 반환한다.<br><br>
	 * 
	 * @param  _format 해당 날짜 포멧
	 * @param  _time   해당 날짜 시간
	 * @return         해당 날짜 시간
	 *********************************************************************************/
	public static String getConvertTime(String _format, long _time) {
				
		return new SimpleDateFormat(_format).format(new Date(_time));
	}
	
}



