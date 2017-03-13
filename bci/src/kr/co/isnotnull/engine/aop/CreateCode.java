package kr.co.isnotnull.engine.aop;

import kr.co.isnotnull.engine.action.AbstractAction;
import kr.co.isnotnull.engine.skill.Constant;


public class CreateCode {
	
	// Before
	public void before(){
		
		kr.co.isnotnull.engine.action.AbstractAction action = (kr.co.isnotnull.engine.action.AbstractAction)com.opensymphony.xwork2.ActionContext.getContext().getActionInvocation().getAction();
		com.ibatis.sqlmap.client.SqlMapClient client = null;
		//String path = \"/resource/config/isnotnull/engine/datasource/datasource.xml\";
		String path = "/resource/config/isnotnull/engine/datasource/datasource.xml";
		try {
			client = com.ibatis.sqlmap.client.SqlMapClientBuilder.buildSqlMapClient(com.ibatis.common.resources.Resources.getResourceAsReader(path));
		} 
		catch (java.io.IOException e) {e.printStackTrace();}
		try {
			client.startTransaction();
			action.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_TRANSACTION,client);
		} 
		catch (java.lang.Exception e) {
			try {
				client.endTransaction();
			} 
			catch (java.lang.Exception e1) {
				e1.printStackTrace();
			}
			finally{
				e.printStackTrace();
				action.results.addResult(kr.co.isnotnull.engine.skill.Constant.PREFIX_RETURN_RESULT, kr.co.isnotnull.engine.skill.Constant.PREFIX_RETURN_MESSAGE);
				action.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_MESSAGE_CODE, "message.error");
				action.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_MESSAGE_TYPE, "historyback");
			}
		}
	}

	
	// After
	public void after(){
		
		kr.co.isnotnull.engine.action.AbstractAction action1 = (kr.co.isnotnull.engine.action.AbstractAction)com.opensymphony.xwork2.ActionContext.getContext().getActionInvocation().getAction();
		com.ibatis.sqlmap.client.SqlMapClient client2 = (com.ibatis.sqlmap.client.SqlMapClient)action1.params.getObject(kr.co.isnotnull.engine.skill.Constant.PREFIX_TRANSACTION);
		try {
			Object exception = action1.params.getObject(kr.co.isnotnull.engine.skill.Constant.PREFIX_EXCEPTION);
			if (null != exception) {
				((Exception)exception).printStackTrace();
				throw (Exception) exception;
			}
			
			client2.commitTransaction();
		} 
		catch (java.lang.Exception e) {
			try {
				client2.endTransaction();
			} 
			catch (java.lang.Exception e1) {
				e1.printStackTrace();
			}
			finally{
				e.printStackTrace();
				action1.results.addResult(kr.co.isnotnull.engine.skill.Constant.PREFIX_RETURN_RESULT, kr.co.isnotnull.engine.skill.Constant.PREFIX_RETURN_MESSAGE);
				action1.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_MESSAGE_CODE, "message.error");
				action1.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_MESSAGE_TYPE, "historyback");
			}
		} 
		finally {
			action1.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_TRANSACTION,null);
		}
		
	}
	
}


