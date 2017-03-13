package kr.co.isnotnull.engine.aop;



public class Signature { 
	
	
	public String name     = null; // 클래스 키
	public String field    = null; // 클래스 필드
	public Object instance = null; // 인스턴스
	
	
	public Object getInstance() {
		return instance;
	}
	public void setInstance(Object instance) {
		this.instance = instance;
	}
	public String getName() {
		return name;
	}
	public String getField() {
		return field;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setField(String field) {
		this.field = field;
	}
}