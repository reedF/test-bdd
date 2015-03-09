package com.reed.bdd.page;

import org.jbehave.core.annotations.BeforeScenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 前置登录抽象类,其他需登录才可测试的step可直接继承本类
 */
public abstract class LoginStep {
	@Value("${uid}")
	private String uid;
	@Autowired
	private Login login;

	@BeforeScenario
	public void login() {
		login.login(uid);
		System.out.println("LoginStep:BeforeScenario:27");
	}

}
