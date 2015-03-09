package com.reed.bdd.page;

import java.util.concurrent.TimeUnit;

import com.reed.bdd.common.Timeout;

import org.jbehave.web.selenium.FluentWebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;

/**
 * 测试页基类
 */
public abstract class AbstractPage extends FluentWebDriverPage {
	@Value("${url}")
	private String domainUrl;
	/** 测试页面URL */
	protected String url;

	protected By pageLoaded; // 此页面的定位元素，用来确认此页面已经加载完成

	public AbstractPage(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	/**
	 * 切换到新打开的页面
	 */
	public void switchToLatest() {
		WebDriver driver = getDriverProvider().get();
		String parentWindow = driver.getWindowHandle();
		for (String curWindow : driver.getWindowHandles()) {
			if (!curWindow.equals(parentWindow)) {
				driver.switchTo().window(curWindow);
				break;
			}
		}
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public String getFullUrl() {
		return domainUrl + url;
	}

	public void goTo() {
		if (getDriverProvider().get().getCurrentUrl().startsWith(getFullUrl())) {
			return;
		}

		// 设置 webdriver 隐形等待时间
		this.getDriverProvider().get().manage().timeouts()
				.implicitlyWait(Timeout.LONG, TimeUnit.SECONDS);

		get(getFullUrl());
	}

	public void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sleepInSeconds(int seconds) {
		sleep(1000 * seconds);
	}

	public void waitUntilElementClickable(By by) {
		waitUntilElementClickable(by, Timeout.LONG);
	}

	public void waitUntilElementClickable(By by, int waitSeconds) {
		WebDriverWait wait = new WebDriverWait(this.getDriverProvider().get(),
				waitSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	public void waitPageLoaded() {
		waitUntilElementClickable(pageLoaded);
	}
}
