package kr.co.isnotnull.engine.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target   (ElementType.METHOD) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface Transaction { 
	
	/** 트랜 잭션 여부
	 * @return 트랜잭션 레이어 이면 true 아니라면 false
	 ********************************************************************************************/
    public boolean isTransaction() default false;    
}
