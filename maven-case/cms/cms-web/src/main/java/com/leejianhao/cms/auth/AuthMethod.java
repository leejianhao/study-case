package com.leejianhao.cms.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用来确定哪些方法由哪些觉得访问
 * 属性base:被所有登陆用户访问
 * 如果为ROLE_PUBLISH表示只能为文章发布人员访问
 * 如果某个方法中没有加入AuthMethod，就表示该方法只能被管理员访问
 * @author ljh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthMethod {
	public String role() default "base";
}
