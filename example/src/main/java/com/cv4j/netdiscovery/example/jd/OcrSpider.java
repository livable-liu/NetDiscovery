package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.Spider;
import com.cv4j.netdiscovery.selenium.Browser;
import com.cv4j.netdiscovery.selenium.action.SeleniumAction;
import com.cv4j.netdiscovery.selenium.downloader.SeleniumDownloader;
import com.cv4j.netdiscovery.selenium.pool.WebDriverPool;
import com.cv4j.netdiscovery.selenium.pool.WebDriverPoolConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2018/6/12.
 */
public class OcrSpider {

    public static void main(String[] args) throws IOException {

        WebDriverPoolConfig config = new WebDriverPoolConfig("example/chromedriver.exe",Browser.CHROME); //设置浏览器的驱动程序和浏览器的类型，浏览器的驱动程序要跟操作系统匹配。
        WebDriverPool.init(config); // 需要先使用init，才能使用WebDriverPool

        List<SeleniumAction> actions = new ArrayList<>();
        actions.add(new BrowserAction());
//        actions.add(new SearchAction());
        actions.add(new SortAction());

        String url = "https://zhcn.109876543210.com/";

        SeleniumDownloader seleniumDownloader = new SeleniumDownloader(actions);
        Spider.create()
                .name("jd")
                .url(url)
                .downloader(seleniumDownloader)
                .parser(new FileParser())
                .pipeline(new FilePipeline())
                .run();
    }
}
