package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.Spider;
import com.cv4j.netdiscovery.selenium.Browser;
import com.cv4j.netdiscovery.selenium.action.SeleniumAction;
import com.cv4j.netdiscovery.selenium.downloader.SeleniumDownloader;
import com.cv4j.netdiscovery.selenium.pool.WebDriverPool;
import com.cv4j.netdiscovery.selenium.pool.WebDriverPoolConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2018/6/12.
 */
public class JDSpider {

    public static void main(String[] args) throws IOException {

        WebDriverPoolConfig config = new WebDriverPoolConfig("example/chromedriver.exe",Browser.CHROME); //设置浏览器的驱动程序和浏览器的类型，浏览器的驱动程序要跟操作系统匹配。
        WebDriverPool.init(config); // 需要先使用init，才能使用WebDriverPool

        List<SeleniumAction> actions = new ArrayList<>();
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
        String url = "https://www.bmw.com.cn/content/dam/bmw/marketCN/bmw_com_cn/model-finder/index.html";
        //
        BufferedReader br = new BufferedReader(new FileReader("D:/tmp/bmw.csv"));
        String str = br.readLine();

        while (str != null) {
            String[] arr = str.split(",");
            String id = arr[0];

            SeleniumDownloader seleniumDownloader = new SeleniumDownloader(actions);
            Spider.create()
                    .name("jd")
                    .url(url.replace("@serial@", arr0[0]).replace("@pattern@", arr[2]))
                    .downloader(seleniumDownloader)
                    .parser(new PriceParser())
                    .pipeline(new PricePipeline())
                    .run();
            str = br.readLine();
        }
    }
}
