package kr.co.isnotnull.engine.action;

import kr.co.isnotnull.engine.map.Params;
import kr.co.isnotnull.engine.map.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class AbstractAction extends ActionSupport {	

	/**
	 * 파라미터를 설정한다.
     *
	 * @param key   파라미터 키
	 * @param value 파라미터 값
	 ********************************************************************************************/
	public void addParam(String key, Object value){
		
		params.addParam(key, value);
	}

	/**
	 * 결과를 설정한다.
     *
	 * @param key   결과 키
	 * @param value 결과 값
	 ********************************************************************************************/
	public void addResult(String key, Object value){
		
		results.addResult(key, value);
	}
	
	/**
	 * Message 정보를 반환한다.
     *
	 * @param key Message 키
	 ********************************************************************************************/
	public String getMessage(String key){

		return super.getText(key);
	}
	
	/**
	 * Config 정보를 반환한다.
     *
	 * @param key Config 키
	 ********************************************************************************************/
	public String getConfig(String key){

		return super.getText(key);
	}
	
	/**
	 * 해당 액션을 반환한다.
	 * 
	 * @return 해당 액션
	 ********************************************************************************************/
	public static AbstractAction getAction(){

		return (AbstractAction)ActionContext.getContext().getActionInvocation().getAction();
	}
	
	/**
	 * 아무런 행위를 하지 않고 이동만 함
	 * 
	 * @param params 사용자 정보
	 ********************************************************************************************/
	public void dispatch(){

	}
	
    
    // PARAMS, RESULTS 초기화
	public Params  params   = new Params();  /** 사용자 정보 **/
	public Results results  = new Results(); /** 결과 정보 **/
}
