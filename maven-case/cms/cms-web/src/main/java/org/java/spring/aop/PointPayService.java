package org.java.spring.aop;

public class PointPayService implements IPayService {

	@Override
	public boolean pay(long userId, long money) {
		return false;
	}

}
