package kr.co.isnotnull.engine.dao;

import kr.co.isnotnull.engine.action.AbstractAction;
import kr.co.isnotnull.engine.map.Params;
import kr.co.isnotnull.engine.skill.Constant;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.opensymphony.xwork2.ActionContext;

public abstract class AbstractDao {
	
	/**
	 * 사용자  정보 반환
	 * 
	 * @return 사용자 정보
	 ********************************************************************************************/
	private Params getParams(){

		return ((AbstractAction)ActionContext.getContext().getActionInvocation().getAction()).params;
	}
	
	/**
	 * 예외 여부
	 * 
	 * @return 예외가 있다면 true 없다면 false
	 ********************************************************************************************/
	private boolean isException(){

		return null == getParams().getObject(Constant.PREFIX_EXCEPTION) ? false : true;
	}

	/**
	 * 예외를 등록한다.
	 * 
	 * @param e 예외
	 ********************************************************************************************/
	private void addException(Exception e){
		
		Object transaction = getParams().getObject(Constant.PREFIX_TRANSACTION);
		Object exception   = getParams().getObject(Constant.PREFIX_EXCEPTION  );
    	
		if( null == transaction ){
			e.printStackTrace(); /** TODO 해당 예외에 맞게 처리 할 것 **/
		}
		else{
			
			// 처음 발생 된 예외만 등록
	    	if( null == exception ){
	    		getParams().addParam(Constant.PREFIX_EXCEPTION, e);
	    	}
		}
	}
	
	/**
	 * 퍼시스턴스 할 수 있는 인스턴스를 반환한다.
     * 
	 * @return 인스턴스
	 ********************************************************************************************/
    protected SqlMapClientImpl getInstance(){
    	
    	SqlMapClientImpl client      = null;
    	Object           transaction = getParams().getObject(Constant.PREFIX_TRANSACTION);
    	
    	// 세션에 트랜잭션이 있다면 처리
    	if( null != transaction ){
    		return (SqlMapClientImpl)transaction;
    	}

    	// 세션에 트랜잭션이 없다면 새로운 트랜잭션을 만들어 반환
    	try {
    		client = (SqlMapClientImpl) SqlMapClientBuilder.buildSqlMapClient(Resources.getResourceAsReader("/resource/config/isnotnull/engine/datasource/datasource.xml"));
    	}
    	catch(Exception e){ 
    		addException(e);
    	}
    	

    	return client;
    }
	
    /**
	 * 목록 쿼리를 실행한다.
	 * <br> - SQLException 을 하나로 관리 할 수 있도록 래핑 함.
     * 
     * @param name  쿼리명
     * @param param 파라미터 정보
	 * @return      쿼리 결과
	 ********************************************************************************************/
    protected Object queryForList(String name, Params param) {
    	
    	Object o = null;
    	
    	try {    		  		
    		if(true == isException()){ return null; }
    		o = getInstance().queryForList(name, param);
    	}
    	catch(Exception e){
    		addException(e);
    	}
    	
    	return o;
    }
    
    /**
	 * 조회 쿼리를 실행한다.
	 * <br> - SQLException 을 하나로 관리 할 수 있도록 래핑 함.
     * 
     * @param name  쿼리명
     * @param param 파라미터 정보
	 * @return      쿼리 결과
	 ********************************************************************************************/
    protected Object queryForObject(String name, Params param) {
    	
    	Object o = null;
    	
    	try{
    		if(true == isException()){ return null; }
    		o = getInstance().queryForObject(name, param);
    	}
    	catch(Exception e){
    		addException(e);
    	}
    	
    	return o;
    }
    
    /**
	 * 등록 쿼리를 실행한다.
	 * <br> - SQLException 을 하나로 관리 할 수 있도록 래핑 함.
     * 
     * @param name  쿼리명
     * @param param 파라미터 정보
	 * @return      쿼리 결과
	 ********************************************************************************************/
    protected Integer insert(String name, Params param){
    	
    	Object o = null;
    	
    	try{
    		if(true == isException()){ return null; }
    		o = getInstance().insert(name, param);
    	}
    	catch(Exception e){
    		addException(e);
    	}
    	
    	return null == o ? null : (Integer)o;
    }
    
    /**
	 * 수정 쿼리를 실행한다.
	 * <br> - SQLException 을 하나로 관리 할 수 있도록 래핑 함.
     * 
     * @param name  쿼리명
     * @param param 파라미터 정보
	 * @return      쿼리 결과
	 ********************************************************************************************/
    protected Integer update(String name, Params param){
    	
    	Object o = null;
    	
    	try{
    		if(true == isException()){ return null; }
    		o = getInstance().update(name, param);
    	}
    	catch(Exception e){
    		addException(e);
    	}
    	
    	return null == o ? null : (Integer)o;
    }
    
    /**
	 * 삭제 쿼리를 실행한다.
	 * <br> - SQLException 을 하나로 관리 할 수 있도록 래핑 함.
     * 
     * @param name  쿼리명
     * @param param 파라미터 정보
	 * @return      쿼리 결과
	 ********************************************************************************************/
    protected Integer delete(String name, Params param){
    	
    	Object o = null;
    	
    	try{
    		if(true == isException()){ return null; }
    		o = getInstance().delete(name, param);
    	}
    	catch(Exception e){
    		addException(e);
    	}
    	
    	return null == o ? null : (Integer)o;
    }

}
