package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.Spider;
import com.cv4j.netdiscovery.core.SpiderEngine;
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

        String url = "http://www.sogou.com/tx?ie=utf8&pid=&query=http%3A%2F%2Fwww.dianping.com%2Fshop%2F125170031&w=&sut=1531&sst0=1551249110249&lkt=0%2C0%2C0";
        //String url = "http://www.sooua.com/s?w=http%3A%2F%2Fwww.dianping.com%2Fshop%2F69089926&d=1&y=11&f=s";

        String path = "/home/livable/tmp/detail_url/";
        File files = new File(path);
        String[] fileNames = {files.getName()};
        if (files.isDirectory()) {
            fileNames = files.list();
        }

        for (int i = 0; i < fileNames.length; i ++) {
            if (!fileNames[i].endsWith(".log")) {
                continue;
            }
            BufferedReader br = new BufferedReader(new FileReader(path + fileNames[i]));
            String str = br.readLine();

            while (str != null) {
                if (str.indexOf("url=") >= 0) {
                    SeleniumDownloader seleniumDownloader = new SeleniumDownloader(actions);
                    Spider.create()
                            .name("jd")
                            .url(url.replace("125170031", str.substring(str.indexOf("www.dianping.com%2Fshop%2F") + "www.dianping.com%2Fshop%2F".length(), str.indexOf("&", str.indexOf("www.dianping.com%2Fshop%2F") ))))
                            .downloader(seleniumDownloader)
                            .parser(new PriceParser())
                            .pipeline(new PricePipeline())
                            .run();
                }

                str = br.readLine();
            }
        }
    }
}
