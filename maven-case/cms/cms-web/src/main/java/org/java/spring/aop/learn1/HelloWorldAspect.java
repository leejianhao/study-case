package org.java.spring.aop.learn1;

public class HelloWorldAspect {
	public void beforeAdvice() {
		System.out.println("===========before advice");
	}
	
	public void afterFinallyAdvice() {
		System.out.println("===========after finally advice");
	}
}
