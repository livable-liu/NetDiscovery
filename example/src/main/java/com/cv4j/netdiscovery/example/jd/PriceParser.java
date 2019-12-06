package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.domain.Page;
import com.cv4j.netdiscovery.core.parser.Parser;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tony on 2018/6/12.
 */
@Slf4j
public class PriceParser implements Parser{

    @Override
    public void process(Page page) {

        String pageHtml = page.getHtml().toString();
        log.error("detail_url=" + page.getUrl());
        Document document = Jsoup.parse(pageHtml);

        //下载车型
        /*
        List<List<String>> result = new ArrayList<>();
        result.add(Lists.newArrayList("id","serial","img","carName","config","energy", "price"));

//        Elements elements = document.select("a[class=m]");
        Elements elements = document.select("div[class=carList]");
        if (elements.size() > 0) {
//            String url = elements.get(0).attr("href");
//            page.getResultItems().put("url", url);
            for (Element element : elements) {
                Element cartList = element;
                Elements models = cartList.select("div[id]");
                for (Element ele : models) {
                    Element model = ele;
                    String id = model.id();
                    Element itemInfo = model.select("div[class=itemInfo]").first();
                    String serial = itemInfo.select("div .carid").first().html();
                    String img = itemInfo.select("img").first().attr("src");
                    String carName = itemInfo.select("div[class=carname]").first().html();
                    Element itemConfig = model.select("div[class=itemConfig]").first();
                    Elements trows = itemConfig.select("div[class=trow]");
                    for (Element trow : trows) {
                        Map<String, String> car = new HashMap<>();
                        Elements spans = trow.select("span");
                        String config = spans.get(0).html(); //配置
                        String energy = spans.get(1).html(); //油耗(升/100公里)
                        String price = spans.get(2).html(); //售价
                        result.add(Lists.newArrayList(id,serial,img,carName,config,energy, price));
                    }
                }
            }
            page.getResultItems().put("result", result);
        }
        */
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String model = document.select("div .indicator-label").first().text();
        //下载车辆配置
        List<List<String>> result = new ArrayList<>();
        Elements elements = document.select("div[class=f-s-Content-title] li");
        if (elements.size() > 0) {
            List<String> list = new ArrayList<>();
            list.add("车型");
            for (int i = 0; i < 9; i ++) {
                String title = elements.get(i).select("div").first().attr("title");
                list.add(title);
//                System.out.println(title);
            }
            result.add(list);
        }
        int size = elements.size();
        elements = document.select("div[class=f-s-Content-data] li");
        Elements classes = document.select("ul .custom-select-options.variant-options li");
        if (elements.size() > 0) {
            int count = elements.size() / size;
            for (int i = 0; i < count; i ++) {
                List<String> list = new ArrayList<>();
                list.add(model);
                list.add(classes.get(i).text());
                for (int j = 0; j < 9; j++) {
                    if (elements.size() >= i * 9 + j) {
                        String title = elements.get(i * 9 + j).select("div .ng-binding").first().text();
                        list.add(title);
                    }
                }
                result.add(list);
            }
        }
        page.getResultItems().put("result", result);
    }
}
