package com.cv4j.netdiscovery.example;

import com.cv4j.netdiscovery.core.Spider;
import com.cv4j.netdiscovery.selenium.Browser;
import com.cv4j.netdiscovery.selenium.downloader.SeleniumDownloader;
import com.cv4j.netdiscovery.selenium.pool.WebDriverPool;
import com.cv4j.netdiscovery.selenium.pool.WebDriverPoolConfig;

/**
 * Created by tony on 2018/6/12.
 */
public class TestPhantomjs {

    public static void main(String[] args) {

        WebDriverPoolConfig config = new WebDriverPoolConfig("/usr/local/phantomjs-2.1.1-macosx/bin/phantomjs", Browser.PHANTOMJS);

        WebDriverPool.init(config);

        TestSeleniumAction testSeleniumAction = new TestSeleniumAction();
        SeleniumDownloader seleniumDownloader = new SeleniumDownloader(testSeleniumAction);

        Spider.create()
                .name("testseleinum")
                .url("https://www.jianshu.com/u/4f2c483c12d8")
                .downloader(seleniumDownloader)
                .run();
    }
}
