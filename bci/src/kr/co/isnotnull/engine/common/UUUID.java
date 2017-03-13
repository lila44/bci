package kr.co.isnotnull.engine.common;


import java.util.UUID;


public class UUUID{

	/**
	 * UUID를 생성한다.<br><br>
	 * 
	 * @return UUID
	 *********************************************************************************/
	public static String getUUID(){
		
		return UUID.randomUUID().toString();
	}
}



