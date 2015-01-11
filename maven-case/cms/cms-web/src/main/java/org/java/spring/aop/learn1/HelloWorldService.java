package org.java.spring.aop.learn1;

public class HelloWorldService implements IHelloWorldService {
	 @Override
	 public void sayHello() {
		 System.out.println("============Hello World!");
	 }
 }