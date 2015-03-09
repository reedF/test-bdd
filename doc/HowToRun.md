1. 以Selenium remote web driver方式调用Chrome执行
mvn integration-test -Dspring.profiles.active=remote -DREMOTE_WEBDRIVER_URL=http://macIp:4444/wd/hub
或 mvn integration-test -Dspring.profiles.active=remote

2. 以本机Chrome作为web driver执行
mvn integration-test -Dspring.profiles.active=chrome -Dbrowser=chrome -Dwebdriver.chrome.driver=/usr/local/chromedriver

3. 以Firefox作为web driver执行
mvn integration-test -Dspring.profiles.active=firefox
或 mvn integration-test

4. 只运行某个story
mvn integration-test -DstoryFilter=${storyName}

5. 按filter过滤执行
mvn integration-test -Dmeta.filter="-category my"
mvn integration-test -Dmeta.filter="+category my"

6. 只运行一个scenario
Maven命令：给Scenario加一个唯一的meta，如：
mvn integration-test -Dmeta.filter="+metaAbc"
在IDE中运行AnnotationVstories时，其只执行Meta中包含@debug的Story和Scenario

7. path环境变量里无法找到对应的浏览器可执行文件时，需要增加webdriver.chrome.bin这个系统参数
如：-Dwebdriver.chrome.bin="C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"

8. 关于webdriver：
Chrome webdirver下载地址: http://chromedriver.storage.googleapis.com/index.html
Mac系统下载2.9版本，2.10配合Chrome37.0.2062.120使用时有问题