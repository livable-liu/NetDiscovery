package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.domain.Page;
import com.cv4j.netdiscovery.core.parser.Parser;
import com.google.common.collect.Lists;
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
public class PriceParser implements Parser{

    @Override
    public void process(Page page) {

        String pageHtml = page.getHtml().toString();
        Document document = Jsoup.parse(pageHtml);

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

    }
}
