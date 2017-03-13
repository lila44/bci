package kr.co.isnotnull.engine.skill;

import kr.co.isnotnull.engine.action.AbstractAction;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;


public class ExpertSkillInitializer {

	/**
	 * action-*.xml 에 설정 된 정보를 초기화 한다.
     *
     * @param  invocation ActionInvocation
	 * @throws Exception 
	 ********************************************************************************************/
	public static void initialize(ActionInvocation invocation) throws Exception{

		AbstractAction abstractAction  = (AbstractAction)invocation.getAction();
		
		
		invokeBeforeAction(invocation); // 전처리
		invocation.invokeActionOnly();  // 실행
		invokeAfterAction(invocation);  // 후처리

		// 특수 기능을 처리 하지 않는다면 처리
		if( null == abstractAction.results.getObject(Constant.PREFIX_RETURN_RESULT) ){
			abstractAction.results.addResult(Constant.PREFIX_RETURN_RESULT, Action.SUCCESS );
		}
	}
	
	/**
	 * 전처리
     * <br> - 로그인
     * <br> - 페이징
     * <br><br>
     * 
     * @param invocation ActionInvocation
	 ********************************************************************************************/
	private static void invokeBeforeAction(ActionInvocation invocation){
		
		AbstractAction abstractAction  = (AbstractAction)invocation.getAction();
		
		if( null == abstractAction.results.getObject(Constant.PREFIX_RETURN_RESULT)) { initializeLogin (invocation); } // 로그인 여부 
		if( null == abstractAction.results.getObject(Constant.PREFIX_RETURN_RESULT)) { initializePaging(invocation); } // 페이징 설정
	}
	
	/**
	 * 후처리
     * <br> - 메세지
     * <br> - 전송
     * <br><br>
     * 
     * @param invocation ActionInvocation
	 ********************************************************************************************/
	private static void invokeAfterAction(ActionInvocation invocation){
		
		AbstractAction abstractAction  = (AbstractAction)invocation.getAction();
		
		if( null == abstractAction.results.getObject(Constant.PREFIX_RETURN_RESULT)) { initializeMesssage (invocation); } // 메세지 여부 
		if( null == abstractAction.results.getObject(Constant.PREFIX_RETURN_RESULT)) { initializeSubmit   (invocation); } // 전송 여부  
	}
	
	/**
	 * 페이징 설정
     *
     * @param invocation ActionInvocation
	 ********************************************************************************************/
	private static void initializePaging(ActionInvocation invocation){
		
		AbstractAction abstractAction  = (AbstractAction)invocation.getAction();
		int            pageIndex       = abstractAction.params.getInt(Constant.PREFIX_PAGE_INDEX, 1); 

		abstractAction.params.addParam  (Constant.PREFIX_PAGE_INDEX, pageIndex);
	}
	
	/**
	 * 로그인 여부
     *
     * @param invocation ActionInvocation
	 ********************************************************************************************/
	private static void initializeLogin(ActionInvocation invocation){
		
		AbstractAction abstractAction  = (AbstractAction)invocation.getAction();
		boolean        isRequiredLogin = abstractAction.params.getBoolean       (Constant.PREFIX_REQUIRED_LOGIN, true);
		Object         session         = abstractAction.params.getParamOfSession(Constant.PREFIX_SESSION_USER_ID     );
		Object         submitUrl       = abstractAction.getText(Constant.PREFIX_LOGIN_URL);

		if( false == isRequiredLogin ){
			return;
		}
		
		// 로그인 여부가 TRUE로 되어 있고, 로그인이 안되어 있을 경우 처리
		if( null == session ){
			abstractAction.results.addResult(Constant.PREFIX_RETURN_RESULT, Constant.PREFIX_RETURN_LOGIN );
			abstractAction.params.addParam  (Constant.PREFIX_SUBMIT_URL,    submitUrl                    );
		}
	}
	
	/**
	 * 메세지 여부
     *
     * @param invocation ActionInvocation
	 ********************************************************************************************/
	private static void initializeMesssage(ActionInvocation invocation){

		AbstractAction abstractAction = (AbstractAction)invocation.getAction();
		String         messageUrl     = abstractAction.params.getString(Constant.PREFIX_MESSAGE_URL);
		
		// 메세지가 있을 경우 처리
		if( null != messageUrl ){
			
			abstractAction.results.addResult(Constant.PREFIX_RETURN_RESULT, Constant.PREFIX_RETURN_MESSAGE);
		}
	}
	
	/**
	 * 전송 여부
     *
     * @param invocation ActionInvocation
	 ********************************************************************************************/
	private static void initializeSubmit(ActionInvocation invocation){

		AbstractAction abstractAction = (AbstractAction)invocation.getAction();
		String         submitUrl      = abstractAction.params.getString(Constant.PREFIX_SUBMIT_URL);
		
		// 전송 URL이 있을 경우 처리
		if( null != submitUrl ){
			
			abstractAction.results.addResult(Constant.PREFIX_RETURN_RESULT, Constant.PREFIX_RETURN_SUBMIT);
			abstractAction.params.addParam  (Constant.PREFIX_SUBMIT_URL,    submitUrl                    );
		}
	}
}
