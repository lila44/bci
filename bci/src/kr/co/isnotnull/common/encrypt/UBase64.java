package kr.co.isnotnull.common.encrypt;

import biz.source_code.base64Coder.Base64Coder;

public class UBase64 {

	/**
	 * BASE64 암호화 <br><br>
	 * 
	 * @param  str 문자열
	 * @return     암호화 된 문자열을 반환한다.
	 *********************************************************************************/
	public static String encode(String str){

		return null == str ? null : Base64Coder.encodeString(str);
	}
	
	/**
	 * BASE64 복호화 <br><br>
	 * 
	 * @param  str 문자열
	 * @return     복호화 된 문자열을 반환한다.
	 *********************************************************************************/
	public static String decode(String str){

		return null == str ? null : Base64Coder.decodeString(str);
	}
}
