package org.java.spring.aop.learn1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {
	public void testHelloWorld() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("helloworld.xml");
		IHelloWorldService helloWorldService = 
				ctx.getBean("helloWorldService", IHelloWorldService.class);
		helloWorldService.sayHello();
	}
}
