package greencloud.maven;

import org.junit.*;
import static org.junit.Assert.*;

public class TestHelloMaven
{
	@Test
	public void testSayHello() {

		HelloMaven hm = new HelloMaven();
		String str = hm.sayHello("maven");
		assertEquals(str, "hello:maven");
	}
}