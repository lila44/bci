package kr.co.isnotnull.test;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kr.co.isnotnull.biz.sample.service.SampleService;

public class AopTest implements ServletContextListener {


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		try { 
			
			ClassPool cp = ClassPool.getDefault();
	    	cp.appendClassPath("D:/projects/dic/workspace/multimedia_framework/WebContent/WEB-INF/classes/");
	    	
	    	CtClass  ctClazz = cp.get(SampleService.class.getName());
	    	CtMethod method1 = ctClazz.getDeclaredMethod("selectSampleList");

	    	method1.insertBefore("{ System.out.println(1111); }");
            method1.insertAfter ("{ System.out.println(2222); }");

            ctClazz.writeFile("D:/projects/dic/workspace/multimedia_framework/WebContent/WEB-INF/classes/");
	    	
            
            
            
//            ClassPool pool = ClassPool.getDefault();
//
//			// CLASSPATH
//			pool.appendClassPath("D:/projects/dic/workspace/multimedia_framework/WebContent/WEB-INF/classes/");
//			pool.appendClassPath("D:/projects/dic/workspace/multimedia_framework/WebContent/WEB-INF/lib/ibatis-2.3.4.726.jar");
//			pool.appendClassPath("D:/projects/dic/workspace/multimedia_framework/WebContent/WEB-INF/lib/struts2-core-2.3.15.jar");
//			pool.appendClassPath("D:/projects/schedule/apache-tomcat-6.0.20/lib/servlet-api.jar");
//			
//	    	CtClass    clazz  = pool.get(SampleService.class.getName());
//	    	CtMethod   method = clazz.getDeclaredMethod("insertSample");
//
//	    	method.insertBefore( "{ System.out.println(\"before\"); }" );
//	    	method.insertAfter ( "{ System.out.println(\"after\" ); }" );
//
//	    	Class c = clazz.toClass(SampleService.class.getClassLoader().getSystemClassLoader(), SampleService.class.getProtectionDomain());
//            clazz.debugWriteFile("D:/projects/schedule/etc/util/jad/");
//            
//	    	SampleService s = (SampleService)c.newInstance();
//	    	s.insertSample(null);
            
            
            
          //method.insertBefore( getBeforeProcess() );
	    	//method.insertAfter ( getAfterProcess () );
            
//	    	SampleService member = new SampleService(); 
//	    	System.out.println(member.getClass().getAnnotation(Transaction.class).isTransaction()); 
//	    	System.out.println(member.getClass().getAnnotation(Transaction.class).isRegistered());
//	    	
//	    	java.lang.reflect.Method[] methods = SampleService.class.getMethods();
//	    	
//	    	for (int i=0; i<methods.length; i++) {
//	    		
//	    		java.lang.reflect.Method m = methods[i];
//	            System.out.println(m.getName());
//	            Transaction t = m.getAnnotation(Transaction.class);
//	            if( null != t ){
//	            	System.out.println(t.isTransaction());
//		            System.out.println(t.isRegistered());
//	            }
//	        }
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    } 
	    
	    System.out.println("init AOP");
	}
}
