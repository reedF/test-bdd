package com.reed.bdd.page;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaiduSearchSteps {

	@Autowired
	private BaiduSearch baiduSearch;

	@Given("在搜索框输入关键字<key>点击搜索")
	public void search(@Named("key") String key) {
		baiduSearch.search(key);
	}

	//@BeforeScenario
	@Given("打开百度搜索页")
	public void open() {
		baiduSearch.open();
	}

	@When("在搜索结果列表中查找是否有“百度百科”")
	public void check() {
		if (baiduSearch.isSuccessed()) {
			System.out.println(">>>>>>>>>done");
		}
	}

	@Then("搜索成功")
	public void done() {
		assertThat(baiduSearch.isSuccessed(), Matchers.equalTo(Boolean.TRUE));
	}
}