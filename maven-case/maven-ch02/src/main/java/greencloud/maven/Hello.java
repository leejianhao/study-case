package greencloud.maven;

import greencloud.maven.HelloMaven;
public class Hello
{
	public String say(String name) {
		HelloMaven hm = new HelloMaven();
		return hm.sayHello(name);
	}
}