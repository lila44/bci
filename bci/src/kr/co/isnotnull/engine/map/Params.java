package kr.co.isnotnull.engine.map;

import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import kr.co.isnotnull.common.encrypt.UBase64;
import kr.co.isnotnull.engine.action.AbstractAction;
import kr.co.isnotnull.engine.skill.Constant;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class Params extends HashMap {
	
	/**
	 * 정보를 등록한다.
     *
	 * @param key   파라미터 키
	 * @param value 값
	 ********************************************************************************************/
	public void addParam(String key, Object value){
		
		super.put(key, value);
	}
	
	/**
	 * 정보를 등록한다.
     *
	 * @param params 값
	 ********************************************************************************************/
	public void addParam(Map params) {
		
		if(null != params){
			
			Set set = params.entrySet();
	    	
	    	Iterator iter = set.iterator();
			while(iter.hasNext()){

				Map.Entry entry = (Map.Entry)iter.next();
				Object    key   = entry.getKey();
				Object    value = entry.getValue();
				
				if( String[].class == value.getClass() ){
					if( true == key.toString().equals(Constant.PREFIX_UPLOAD_FILE_PATH) ){
						super.put(key.toString(), UBase64.decode(((String[])value)[0]));
					}
					else{
						super.put(key.toString(), 1 < ((String[])value).length ? value : ((String[])value)[0]);
					}
				}
				else{
					if( true == key.toString().equals(Constant.PREFIX_UPLOAD_FILE_PATH) ){
						super.put(key.toString(), UBase64.decode(value.toString()));
					}
					else{
						super.put(key.toString(), value);
					}
				}
			}
		}
	}

	/**
	 * 오브젝트를 반환한다.
     *
	 * @param key 파라미터 키
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
	 * @param key 파라미터 키
	 * @return    키가 널이라면 null, 가져온 값이 널이라면 null 아니라면 해당 인스턴스를 반환
	 ********************************************************************************************/
	public String getString(String key){
		
		if( null == key ){
			return null;
		}
		
		return null == this.get(key) ? null : String.valueOf(this.get(key));
	}
	
	/**
	 * 큰 정수를 반환한다.
     *
	 * @param key 파라미터 키
	 * @return    키가 널이라면 -1, 가져온 값이 널이라면 -1 아니라면 해당 정수를 반환
	 ********************************************************************************************/
	public long getLong(String key){
		
		if( null == key ){
			return -1;
		}
		
		return null == this.get(key) ? -1 : (Long)this.get(key);
	}
	
	/**
	 * 정수를 반환한다.
     *
	 * @param key 파라미터 키
	 * @return    키가 널이라면 -1, 가져온 값이 널이라면 -1 아니라면 해당 정수를 반환
	 ********************************************************************************************/
	public int getInt(String key){
		
		if( null == key ){
			return -1;
		}
		
		return null == this.get(key) ? -1 : Integer.parseInt((String)this.get(key));
	}
	
	/**
	 * 정수를 반환한다.
     *
	 * @param key          파라미터 키
	 * @param defaultValue 기본 값
	 * @return             키가 널이라면 기본 값, 아니라면 해당 인스턴스를 반환
	 ********************************************************************************************/
	public int getInt(String key, int defaultValue){
		
		if( null == key ){
			return -1;
		}
		
		return null == this.get(key) ? defaultValue : Integer.parseInt((String)this.get(key));
	}
	
	/**
	 * 논리형를 반환한다.
     *
	 * @param key 파라미터 키
	 * @return    키가 널이라면 null, 가져온 값이 널이라면 null 아니라면 해당 인스턴스를 반환
	 ********************************************************************************************/
	public boolean getBoolean(String key){
		
		if( null == key ){
			return false;
		}
		
		return null == this.get(key) ? false : Boolean.parseBoolean((String)this.get(key));
	}
	
	/**
	 * 논리형를 반환한다.
     *
	 * @param key          파라미터 키
	 * @param defaultValue 기본 값
	 * @return             키가 널이라면 기본 값, 아니라면 해당 인스턴스를 반환
	 ********************************************************************************************/
	public boolean getBoolean(String key, boolean defaultValue){
		
		if( null == key ){
			return false;
		}
		
		return null == this.get(key) ? defaultValue : Boolean.parseBoolean((String)this.get(key));
	}
	
	/**
	 * 정수 배열을 반환한다.
     *
	 * @param key 파라미터 키
	 * @return    키가 널이라면 null, 가져온 값이 널이라면 null 아니라면 해당 정수 배열을 반환
	 ********************************************************************************************/
	public int[] getIntArray(String key){
		
		if( null == key ){
			return null;
		}
		
		return null == this.get(key) ? null : (int [])this.get(key);
	}
	
	/**
	 * 문자 배열을 반환한다.
     *
	 * @param key 파라미터 키
	 * @return    키가 널이라면 null, 가져온 값이 널이라면 null 아니라면 해당 문자 배열을 반환
	 ********************************************************************************************/
	public String[] getStringArray(String key){
		
		if( null == key ){
			return null;
		}
		
		return null == this.get(key) ? null : (String [])this.get(key);
	}
	
	/**
	 * 배열로 변환한다.
     *
	 * @param key 파라미터 키
	 * @return    키가 널이라면 null, 가져온 값이 널이라면 null 아니라면 해당 문자 배열을 반환
	 ********************************************************************************************/
	public void replaceArray(String key){
		
		Object o = this.get(key);
		
		if( null != o ){
			if( false == o.getClass().isArray()){
				this.addParam(key, Arrays.asList(o));
			}
		}
	}
	
	/**
	 * 세션을 반환한다.
     *
	 * @param  value 값
	 * @return       세션 정보
	 ********************************************************************************************/
	public HttpSession getSession(){
		
		return ServletActionContext.getRequest().getSession();
	}
	
	/**
	 * 세션을 반환한다.
     *
	 * @param  value 값
	 * @return       세션 정보
	 ********************************************************************************************/
	public HttpSession getSession(boolean type){
		
		return ServletActionContext.getRequest().getSession(type);
	}
	
	/**
	 * 세션 정보를 설정한다.
     *
	 * @param key   파라미터 키
	 * @param value 값
	 ********************************************************************************************/
	public void addParamOfSession(String key, Object value){
		
		getSession().setAttribute(key, value);
	}
	
	/**
	 * 세션 정보를 반환한다.
     *
	 * @param  value 값
	 * @return       세션 정보
	 ********************************************************************************************/
	public Object getParamOfSession(String key){
		
		return getSession().getAttribute(key);
	}
	
	/**
	 * 세션 정보를 출력한다.
	 ********************************************************************************************/
	public void toStringParamOfSession(){
		
		Enumeration enumeration = getSession().getAttributeNames();
		while( true == enumeration.hasMoreElements() ){
			String key   = (String)enumeration.nextElement();
			Object value = getSession().getAttribute(key);

			System.out.println(key + File.separator + String.valueOf(value));
		}
	}
	
	/**
	 * 세션 정보 목록을 반환한다.
     *
	 * @param  value 값
	 * @return       세션 정보
	 ********************************************************************************************/
	public Map getParamOfSessionMap(){
		
		Map params = new HashMap();
		
		Enumeration enumeration = getSession().getAttributeNames();
		while( true == enumeration.hasMoreElements() ){
			String key   = (String)enumeration.nextElement();
			Object value = getSession().getAttribute(key);
			
			// 시스템 정보 일 경우 제거
			if( true == key.startsWith(Constant.PREFIX_SYSTEM_ATTRIBUTE) ){
				//getSession().removeAttribute(key);
			}
			
			params.put(key, value);
		}

		return 0 == params.size() ? null : params;
	}
	
	/**
	 * 세션 정보 목록을 삭제한다.
	 ********************************************************************************************/
	public void removeSession(){
		
		Enumeration enumeration = getSession().getAttributeNames();
		while( true == enumeration.hasMoreElements() ){
			getSession().removeAttribute((String)enumeration.nextElement());
		}
	}
	
	/**
	 * 세션 정보를 삭제한다.
	 ********************************************************************************************/
	public void removeSession(String key){
		
		getSession().removeAttribute(key);
	}
	
	/**
	 * config.properties 에 정의 된 정보를 반환한다.
	 * 
	 * @param key 환경 정보 키
	 * @return    환경 정보
	 ********************************************************************************************/
	public String getConfig(String key){
		return ((AbstractAction)ActionContext.getContext().getActionInvocation().getAction()).getText(key);
	}
	
	/**
	 * config.properties 에 정의 된 정보를 반환한다.
	 * 
	 * @param key 환경 정보 키
	 * @return    환경 정보
	 ********************************************************************************************/
	public int getConfigInt(String key){
		
		String config = ((AbstractAction)ActionContext.getContext().getActionInvocation().getAction()).getText(key);
		
		return null == config ? -1 : Integer.parseInt(config);
	}
	
	/**
	 * message.properties 에 정의 된 정보를 반환한다.
	 * 
	 * @param key 메세지 정보 키
	 * @return    메세지 정보
	 ********************************************************************************************/
	public String getMessage(String key){
		return ((AbstractAction)ActionContext.getContext().getActionInvocation().getAction()).getText(key);
	}
}

