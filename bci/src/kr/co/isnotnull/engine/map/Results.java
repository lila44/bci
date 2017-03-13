package kr.co.isnotnull.engine.map;

import java.util.HashMap;

public class Results extends HashMap {

	/**
	 * 결과 정보를 등록한다.
     *
	 * @param key   파라미터 키
	 * @param value 값
	 ********************************************************************************************/
	public void addResult(String key, Object value){
		
		super.put(key, value);
	}
	
	/**
	 * 오브젝트를 반환한다.
     *
	 * @param key 결과 키
	 * @return    키가 널이라면 null, 가져온 값이 널이라면 null 아니라면 해당 인스턴스를 반환
	 ********************************************************************************************/
	public Object getObject(String key){
		
		if( null == key ){
			return null;
		}
		
		return null == this.get(key) ? null : this.get(key);
	}
	
	/**
	 * 문자를 반환한다.
     *
	 * @param key 결과 키
	 * @return    키가 널이라면 null, 가져온 값이 널이라면 null 아니라면 해당 인스턴스를 반환
	 ********************************************************************************************/
	public String getString(String key){
		
		if( null == key ){
			return null;
		}
		
		return null == this.get(key) ? null : (String)this.get(key);
	}
	
	/**
	 * 정수를 반환한다.
     *
	 * @param key 결과 키
	 * @return    키가 널이라면 -1, 가져온 값이 널이라면 -1 아니라면 해당 정수를 반환
	 ********************************************************************************************/
	public int getInt(String key){
		
		if( null == key ){
			return -1;
		}
		
		return null == this.get(key) ? -1 : (Integer)this.get(key);
	}
	
	/**
	 * 논리형를 반환한다.
     *
	 * @param key 결과 키
	 * @return    키가 널이라면 null, 가져온 값이 널이라면 null 아니라면 해당 인스턴스를 반환
	 ********************************************************************************************/
	public boolean getBoolean(String key){
		
		if( null == key ){
			return false;
		}
		
		return null == this.get(key) ? false : Boolean.parseBoolean((String)this.get(key));
	}
}
