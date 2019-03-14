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

        WebDriverPoolConfig config = new WebDriverPoolConfig("example/chromedriver",Browser.CHROME); //设置浏览器的驱动程序和浏览器的类型，浏览器的驱动程序要跟操作系统匹配。
        WebDriverPool.init(config); // 需要先使用init，才能使用WebDriverPool

        List<SeleniumAction> actions = new ArrayList<>();
//        actions.add(new BrowserAction());
        actions.add(new SearchAction());
//        actions.add(new SortAction());

        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=http%3A%2F%2Fwww.dianping.com%2Fshop%2F125170031&rsv_pq=8d186780000170d1&rsv_t=97cfBd6O3TUh0C6%2Fw1%2F9HV%2FYY0x4ZyF5ct5Pde%2FAUosvP6k4XCNWwTtcz5Y&rqlang=cn&rsv_enter=1&rsv_n=2&rsv_sug3=1";

        String path = "/home/livable/tmp/detail_url/";
        File files = new File(path);
        String[] fileNames = {files.getName()};
        if (files.isDirectory()) {
            fileNames = files.list();
        }



        for (int i = 0; i < fileNames.length; i ++) {
            if (!fileNames[i].endsWith(".txt")) {
                continue;
            }
            BufferedReader br = new BufferedReader(new FileReader(path + fileNames[i]));
            String str = br.readLine();

            while (str != null) {
                SeleniumDownloader seleniumDownloader = new SeleniumDownloader(actions);
                Spider.create()
                        .name("jd")
                        .url(url.replace("125170031", str.substring(str.lastIndexOf("/") + 1)))
                        .downloader(seleniumDownloader)
                        .parser(new PriceParser())
                        .pipeline(new PricePipeline())
                        .run();
                str = br.readLine();
            }
        }
    }
}
