Baidu search story

Narrative:
In order to test searching in baidu
As a user
I want to search "bdd"
					 
Scenario:  搜索
Meta:
@debug
Given 打开百度搜索页
Given 在搜索框输入关键字<key>点击搜索
When 在搜索结果列表中查找是否有“百度百科”
Then 搜索成功


Examples:
|uid|key|
|1|bdd|
