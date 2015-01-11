package com.leejianhao.cms.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 只要在Controller上增加这个方法的类，都要进行权限控制
 * @author ljh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthClass {
	/**
	 * 如果value为admin表示这个类只能超管访问
	 * 如果value为login表示这个类中的方法，某些可能为相应的角色可以访问
	 * @return
	 */
	public String value() default "admin";
}
