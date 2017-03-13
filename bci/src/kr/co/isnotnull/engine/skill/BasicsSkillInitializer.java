package kr.co.isnotnull.engine.skill;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import kr.co.isnotnull.engine.action.AbstractAction;
import kr.co.isnotnull.engine.aop.Signature;
import kr.co.isnotnull.engine.map.Params;

import org.apache.struts2.StrutsStatics;

import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;


public class BasicsSkillInitializer {

	/**
	 * action-*.xml 에 설정 된 정보를 초기화 한다.
     *
     * @param  invocation ActionInvocation
	 ********************************************************************************************/
	public static void initialize(ActionInvocation invocation) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		
        initializeParams(invocation);              // 사용자 정보 설정
        initializeDependencyInjection(invocation); // DEPENDENCY INJECTION
	}

	
	/**
	 * 사용자 정보를 초기화 한다.
     *
     * @param invocation ActionInvocation
	 ********************************************************************************************/
	private static void initializeParams(ActionInvocation invocation){

		AbstractAction action = (AbstractAction)invocation.getAction();
		action.params.addParam(ActionContext.getContext().getParameters()); // params, results
		action.params.addParam(action.params.getParamOfSessionMap());       // session
	}
	
	/**
	 * DI(Dependency Injection)
     *
     * @param invocation ActionInvocation
	 ********************************************************************************************/
	private static void initializeDependencyInjection(ActionInvocation invocation) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		
		ActionContext  actionContext  = ActionContext.getContext();
		AbstractAction action         = (AbstractAction)invocation.getAction();
		ServletContext context        = (ServletContext)actionContext.get(StrutsStatics.SERVLET_CONTEXT);
		Map            actionPool     = (Map           )context.getAttribute(Constant.PREFIX_ACTION_POOL);
		
		if( null != actionPool && true == actionPool.containsKey(action.getClass().getName())){
        	
        	List actionList = (List)actionPool.get(action.getClass().getName());
        	if( null != actionList ){
        		
        		Iterator iter = actionList.iterator();
            	while( true == iter.hasNext() ){
            		Signature signature = (Signature)iter.next();
                	Field     field     = action.getClass().getField(signature.getField());
                    
                	field.set(action, signature.getInstance());
            	}
        	}
        }
	}
	
	/**
	 * 페이징 쿼리로 변경한다.(현재 사용되지 않음)
     *
     * @param client SqlMapClientImpl
     * @param name   query id
     * @param params param
	 ********************************************************************************************/
	@Deprecated
	private static void initializePagingQuery(SqlMapClientImpl client, String name, Params params) {
		
		MappedStatement statement      = client.getMappedStatement(name);
        StatementScope  statementScope = new StatementScope(new SessionScope());
        
        statement.initRequest(statementScope);
     
        Sql          sql          = statement.getSql();
        String       query        = sql.getSql(statementScope, params);
        ParameterMap parameterMap = sql.getParameterMap(statementScope, params);
        ResultMap    resultMap    = sql.getResultMap(statementScope, params);
	}
}
