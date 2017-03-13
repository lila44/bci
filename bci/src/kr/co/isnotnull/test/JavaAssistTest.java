package kr.co.isnotnull.test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

class HelloWorld {
	public void sayHello() {
		System.out.println("Hello ");
	}
}

class Inject1 {
 
    public void injectAround(String targetClass, String targetMethod) {
        try {
        	ClassPool cp = ClassPool.getDefault();
            CtClass ctClazz = cp.get(targetClass);
            CtMethod method1 = ctClazz.getDeclaredMethod(targetMethod);
            method1.insertBefore("{ System.out.println(\"Code injected before method\"); }");
            method1.insertAfter("{ System.out.println(\"Code injected after method\"); }");
            ctClazz.toClass(ClassLoader.getSystemClassLoader(), null);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}
public class JavaAssistTest {

	public static void main(String[] args) throws Exception {
		
		new Inject1().injectAround("kr.co.isnotnull.biz.sample.service.SampleService", "insertSample");
		new kr.co.isnotnull.biz.sample.service.SampleService().insertSample(null);
		
//		new Inject1().injectAround("kr.co.isnotnull.test.HelloWorld", "sayHello");
//		new kr.co.isnotnull.test.HelloWorld().sayHello();
	}
}
