package kr.co.isnotnull.engine.aop;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kr.co.isnotnull.engine.common.UFile;
import kr.co.isnotnull.engine.skill.Constant;

public class TransactionAop implements ServletContextListener {
	
	/**
	 * 서버 종료 시 프로세스
	 * 
	 * @param context 컨텍스트
	 ********************************************************************************************/
	public void contextDestroyed(ServletContextEvent context) { }

	/**
	 * 서버 기동 시 프로세스
	 * 
	 * @param context 컨텍스트
	 ********************************************************************************************/
	public void contextInitialized(ServletContextEvent context) {
		
		try { 
			Map extendService = extendService(context               );
			Map extendAction  = extendAction (context, extendService);			

			context.getServletContext().setAttribute(Constant.PREFIX_ACTION_POOL, extendAction );
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    } 
	}
	
	/**
	 * 트랜잭션 처리가 되어야 하는 메소드를 찾아 서비스풀에 등록한다.
	 * 
	 * @param context 컨텍스트
	 * @exception     예외
	 * @return        트랜잭션 처리가 된 클래스 맵
	 ********************************************************************************************/
	private Map extendService(ServletContextEvent context) throws Exception {

		Map       serverPool = new HashMap();
		ClassPool classPool  = ClassPool.getDefault();
		
		List classList = UFile.getClassListing(context.getServletContext().getRealPath(File.separator), UFile.PREFIX_EXTENSION_CLASS);
		for(int idx1=0; idx1<classList.size(); idx1++){
			
			
			
			boolean  isExtend = false;
			Class    oldClass = (Class)classList.get(idx1);
			Method[] methods  = oldClass.getDeclaredMethods();
			
			classPool.insertClassPath(new ClassClassPath(oldClass));
			
			String  unique         = UUID.randomUUID().toString();
        	CtClass expansionClass = classPool.makeClass(oldClass.getName() + unique);
    	    CtClass superClass     = classPool.get(oldClass.getName());
    	    expansionClass.setSuperclass(superClass);

	    	
	    	for (int idx2=0; idx2<methods.length; idx2++) {
	    		
	    		Method method = methods[idx2];
	            Transaction t = method.getAnnotation(Transaction.class);
	            if( null != t ){

	            	System.out.println("transaction initialize method : " + oldClass.getName() + "." + method.getName());
	            	if( true == t.isTransaction() ){
	            		
	            		isExtend = true;
	            		
	            		StringBuffer expansionParams = new StringBuffer();
	            		StringBuffer superParams     = new StringBuffer();
	            		Class []     parameterTypes  = method.getParameterTypes();
	            		for(int idx3=0; idx3<parameterTypes.length; idx3++){
	            			
	            			Class paramType = parameterTypes[idx3];
	            			
	            			if(0 < idx3){
	            				expansionParams.append(",");
	            				superParams.append(",");
	            			}
	            			
	            			expansionParams.append(paramType.getName() + " x" + idx3);
	            			superParams.append("x" + idx3);
	            		}

	            	    StringBuffer method_1 = new StringBuffer();

	            	    method_1.append("\n public #p4# #p1#(#p2#){    ");
	            	    method_1.append("\n     " + getBeforeProcess()  );
	            	    method_1.append("\n 	#p5# super.#p1#(#p3#); ");
	            	    method_1.append("\n     " +  getAfterProcess()  );
	            	    method_1.append("\n 	#p6#;                  ");
	            	    method_1.append("\n }                          ");
	            	    
	            	    String variable = method_1.toString();
	            	           variable = variable.replaceAll("#p1#", method.getName()                );
	            	           variable = variable.replaceAll("#p2#", expansionParams.toString()      );
	            	           variable = variable.replaceAll("#p3#", superParams.toString()          );
	            	           variable = variable.replaceAll("#p4#", method.getReturnType().getName());
	            	           
	            	    if( false == "void".equals(method.getReturnType().getName()) ){
	            	    	variable = variable.replaceAll("#p5#", method.getReturnType().getName() + " x_return_type =");
	            	    	variable = variable.replaceAll("#p6#", "return x_return_type");
	            	    }
	            	    else{
	            	    	variable = variable.replaceAll("#p5#", "");
	            	    	variable = variable.replaceAll("#p6#", "");
	            	    }
	            	           
	            	    //System.out.println(variable);      
	            	           
	            	    CtMethod method1 = CtNewMethod.make(variable, expansionClass); 
	            	    expansionClass.addMethod(method1); 
	            	}
	            }
	        }
	    	
	    	if(true == isExtend){
	    		serverPool.put(oldClass.getName(), expansionClass.toClass().newInstance());
	    	}
		}

    	return serverPool;
	}
	
	/**
	 * 트랜잭션 메소드를 호출해야 하는 필드를 찾아 서비스풀에 등록한다.
	 * 
	 * @param     context       컨텍스트
	 * @param     extendService 트랜잭션 메소드 서비스 풀
	 * @exception               예외
	 * @return                  트랜잭션 메소드를 호출해야 하는 필드 맵
	 ********************************************************************************************/
	private Map extendAction(ServletContextEvent context, Map extendService) throws Exception {

		Map       actionPool = new HashMap();
		ClassPool classPool  = ClassPool.getDefault();
		List      classList  = UFile.getClassListing(context.getServletContext().getRealPath(File.separator), UFile.PREFIX_EXTENSION_CLASS);
		
		for(int idx1=0; idx1<classList.size(); idx1++){
			
			Class   actionClass  = (Class)classList.get(idx1);
			Field[] actionFields = actionClass.getDeclaredFields();
			List    actionList   = null;
			
			for(int idx2=0; idx2<actionFields.length; idx2++){
				Field  field = actionFields[idx2];
				if( true == extendService.containsKey(field.getType().getName()) ){
					
					System.out.println("transaction initialize field  : " + actionClass.getName() + "." + field.getName());

					if( null == actionList ){
						actionList = new ArrayList();
					}
					
					Signature signature = new Signature();
					signature.setName (actionClass.getName());
					signature.setField(field.getName());
					signature.setInstance(extendService.get(field.getType().getName()));
					
					actionList.add(signature);
				}
			}
			
			if( null != actionList ){
				actionPool.put(actionClass.getName(), actionList);
			}
		}
		

    	return actionPool;
	}
	
	/**
	 * 트랜잭션 시작 코드
	 *
	 * @return 트랜잭션 시작 코드
	 ********************************************************************************************/
	private String getBeforeProcess(){
		// System.out.println(\"transaction before\"); 
		//return "kr.co.isnotnull.engine.action.AbstractAction action = (kr.co.isnotnull.engine.action.AbstractAction)com.opensymphony.xwork2.ActionContext.getContext().getActionInvocation().getAction();com.ibatis.sqlmap.client.SqlMapClient client = null;String path = \"/resource/config/isnotnull/engine/datasource/datasource.xml\";try {client = com.ibatis.sqlmap.client.SqlMapClientBuilder.buildSqlMapClient(com.ibatis.common.resources.Resources.getResourceAsReader(path));} catch (java.io.IOException e) {e.printStackTrace();}try {client.startTransaction();action.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_TRANSACTION,client);} catch (java.lang.Exception e) {try {client.endTransaction();} catch (java.lang.Exception e1) {e1.printStackTrace();}}";
		return "kr.co.isnotnull.engine.action.AbstractAction action = (kr.co.isnotnull.engine.action.AbstractAction)com.opensymphony.xwork2.ActionContext.getContext().getActionInvocation().getAction(); com.ibatis.sqlmap.client.SqlMapClient client = null; String path = \"/resource/config/isnotnull/engine/datasource/datasource.xml\"; try { client = com.ibatis.sqlmap.client.SqlMapClientBuilder.buildSqlMapClient(com.ibatis.common.resources.Resources.getResourceAsReader(path)); }  catch (java.io.IOException e) {e.printStackTrace();} try { client.startTransaction(); action.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_TRANSACTION,client); }  catch (java.lang.Exception e) { try { client.endTransaction(); }  catch (java.lang.Exception e1) { e1.printStackTrace(); } finally{ e.printStackTrace(); action.results.addResult(kr.co.isnotnull.engine.skill.Constant.PREFIX_RETURN_RESULT, kr.co.isnotnull.engine.skill.Constant.PREFIX_RETURN_MESSAGE); action.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_MESSAGE_CODE, \"message.error\"); action.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_MESSAGE_TYPE, \"historyback\"); } }";
	}
	
	/**
	 * 트랜잭션 완료 코드
	 *
	 * @return 트랜잭션 완료 코드
	 ********************************************************************************************/
	private String getAfterProcess(){
		// System.out.println(\"transaction after\"); 
		//return "kr.co.isnotnull.engine.action.AbstractAction action1 = (kr.co.isnotnull.engine.action.AbstractAction)com.opensymphony.xwork2.ActionContext.getContext().getActionInvocation().getAction();com.ibatis.sqlmap.client.SqlMapClient client2 = (com.ibatis.sqlmap.client.SqlMapClient)action1.params.getObject(kr.co.isnotnull.engine.skill.Constant.PREFIX_TRANSACTION);try {Object exception = action1.params.getObject(kr.co.isnotnull.engine.skill.Constant.PREFIX_EXCEPTION);if (null != exception) {throw (Exception) exception;}client2.commitTransaction();} catch (java.lang.Exception e) {try {client2.endTransaction();} catch (java.lang.Exception e1) {e1.printStackTrace();}e.printStackTrace();} finally {action1.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_TRANSACTION,null);}";
		return "kr.co.isnotnull.engine.action.AbstractAction action1 = (kr.co.isnotnull.engine.action.AbstractAction)com.opensymphony.xwork2.ActionContext.getContext().getActionInvocation().getAction(); com.ibatis.sqlmap.client.SqlMapClient client2 = (com.ibatis.sqlmap.client.SqlMapClient)action1.params.getObject(kr.co.isnotnull.engine.skill.Constant.PREFIX_TRANSACTION); try { Object exception = action1.params.getObject(kr.co.isnotnull.engine.skill.Constant.PREFIX_EXCEPTION); if (null != exception) { throw (Exception) exception; }  client2.commitTransaction(); }  catch (java.lang.Exception e) { try { client2.endTransaction(); }  catch (java.lang.Exception e1) { e1.printStackTrace(); } finally{ e.printStackTrace(); action1.results.addResult(kr.co.isnotnull.engine.skill.Constant.PREFIX_RETURN_RESULT, kr.co.isnotnull.engine.skill.Constant.PREFIX_RETURN_MESSAGE); action1.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_MESSAGE_CODE, \"message.error\"); action1.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_MESSAGE_TYPE, \"historyback\"); } }  finally { action1.params.addParam(kr.co.isnotnull.engine.skill.Constant.PREFIX_TRANSACTION,null); }";
	}
}




