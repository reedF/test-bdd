package com.reed.bdd.page;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试登录页面 URL: /login/{uid}
 */
@Component
public class Login extends AbstractPage {

	public final String UID_COOKIE_NAME = "__BDD_UID";

	@Autowired
	public Login(WebDriverProvider driverProvider) {
		super(driverProvider);
		url = "/login/";
	}

	public synchronized void login(String uid) {
		WebDriver driver = getDriverProvider().get();
		if (driver.getCurrentUrl().startsWith(getDomainUrl())
				&& uid.equals(driver.manage().getCookieNamed(UID_COOKIE_NAME)
						.getValue())) {
			return;
		}
		System.out.println("--current url need login: "
				+ driver.getCurrentUrl());
		get(getFullUrl() + uid);
		driver.manage().addCookie(new Cookie(UID_COOKIE_NAME, uid));
	}

}
