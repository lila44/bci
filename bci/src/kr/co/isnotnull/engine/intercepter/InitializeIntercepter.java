package kr.co.isnotnull.engine.intercepter;

import kr.co.isnotnull.engine.action.AbstractAction;
import kr.co.isnotnull.engine.skill.BasicsSkillInitializer;
import kr.co.isnotnull.engine.skill.Constant;
import kr.co.isnotnull.engine.skill.ExpertSkillInitializer;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class InitializeIntercepter extends AbstractInterceptor {
	
	/**
	 * <br> ACTION 실행 전 전처리를 한다.
	 * <br>  - 실행을 기점 으로 실행전은 전처리 실행후는 후처리
	 * <br><br>
	 * 
	 * @param invocation 실행 정보
	 * @exception        예외발생
	 * @return           invokeActionOnly(해당 ACTION 실행하고 하위 프로세스 실행하지 않음)로 인해 null 처리
	 ********************************************************************************************/
	public String intercept(ActionInvocation invocation) throws Exception {

        // 기본 기능 초기화
		BasicsSkillInitializer.initialize(invocation);
		
		// 특수 기능 초기화 
		ExpertSkillInitializer.initialize(invocation);

		
		// 결과 정보 반환
		return ((AbstractAction)invocation.getAction()).results.getString(Constant.PREFIX_RETURN_RESULT);
	}

}
