package com.reed.bdd.page;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reed.bdd.common.Timeout;

/**
 * 测试环境百度搜索 URL:
 */
@Component
public class BaiduSearch extends AbstractPage {

	@Autowired
	public BaiduSearch(WebDriverProvider driverProvider) {
		super(driverProvider);
		url = "";
	}

	public void open() {
		WebDriver driver = getDriverProvider().get();
		System.out.println("--current url: " + driver.getCurrentUrl());
		goTo();
	}

	public void search(String key) {
		findElement(By.id("kw")).clear();
		findElement(By.id("kw")).sendKeys(key);
		findElement(By.id("su")).click();
	}
	

	public boolean isSuccessed() {
		return findElement(By.xpath("//*/h3")).getText().indexOf("百度百科") >= 0;
	}

}
