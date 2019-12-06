package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.Spider;
import com.cv4j.netdiscovery.selenium.Browser;
import com.cv4j.netdiscovery.selenium.action.SeleniumAction;
import com.cv4j.netdiscovery.selenium.downloader.SeleniumDownloader;
import com.cv4j.netdiscovery.selenium.pool.WebDriverPool;
import com.cv4j.netdiscovery.selenium.pool.WebDriverPoolConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by tony on 2018/6/12.
 */
public class JDSpiderDown {

    public static void main(String[] args) throws IOException, InterruptedException {

        WebDriverPoolConfig config = new WebDriverPoolConfig("example/chromedriver.exe",Browser.CHROME);
        WebDriverPool.init(config);

//        List<SeleniumAction> actions = new ArrayList<>();
//        actions.add(new BrowserAction());
//        actions.add(new SearchAction());
//        actions.add(new SortAction());

//        String url = "https://www.bmw.com.cn/content/dam/bmw/marketCN/bmw_com_cn/model-finder/index.html";
//
//        SeleniumDownloader seleniumDownloader = new SeleniumDownloader(actions);
//        Spider.create()
//                .name("jd")
//                .url(url)
//                .downloader(seleniumDownloader)
//                .parser(new PriceParser())
//                .pipeline(new PricePipeline())
//                .run();
        String url = "https://www.bmw.com.cn/zh/all-models/x-series/X1/2019/specsheet.html";
        List<SeleniumAction> actions = new ArrayList<>();

        SeleniumDownloader seleniumDownloader = new SeleniumDownloader(actions);
        Spider.create()
                .name("jd")
                .url(url)
                .downloader(seleniumDownloader)
                .parser(new PriceParser())
                .pipeline(new PricePipeline())
                .run();
    }
}
