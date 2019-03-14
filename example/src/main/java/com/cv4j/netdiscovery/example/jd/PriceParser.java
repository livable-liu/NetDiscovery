package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.domain.Page;
import com.cv4j.netdiscovery.core.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2018/6/12.
 */
public class PriceParser implements Parser{

    @Override
    public void process(Page page) {

        String pageHtml = page.getHtml().toString();
        Document document = Jsoup.parse(pageHtml);

//        Elements elements = document.select("a[class=m]");
        Elements elements = document.select("div[class=breadcrumb] a");
        Elements baiduSubmit = document.select("input[id=bd_snap_su]");
        if (elements.size() > 0 && baiduSubmit.size() > 0) {
//            String url = elements.get(0).attr("href");
//            page.getResultItems().put("url", url);
            String city = elements.get(0).attr("href").toLowerCase().replace("//www.dianping.com/", "");
            city = city.substring(0, city.indexOf("/"));
            String url = page.getUrl();
            String district = elements.get(elements.size() - 2).text();
            String location = elements.get(elements.size() - 1).text();
            Elements spanElem = document.select("div[class=breadcrumb] span");
            String name = spanElem.get(spanElem.size() - 1).text();
            Elements spanElem1 = document.select("span[id=reviewCount]");
            String comment = spanElem1.get(0).text();
            Elements spanElem2 = document.select("span[id=avgPriceTitle]");
            String avg = spanElem2.get(0).text();
            Elements spanElem3 = document.select("span[id=address]");
            String address = spanElem3 != null && spanElem3.size() > 0 ? spanElem3.get(0).text() : "";
            Elements pElem = document.select("p[class=expand-info tel]");
            String tel = pElem.get(0).text();
            page.getResultItems().put("city", city);
            int index = url.indexOf("com%2Fshop%2F");
            page.getResultItems().put("url", url.substring(index + "com%2Fshop%2F".length(), url.indexOf("&", index)));
            page.getResultItems().put("district",district);
            page.getResultItems().put("location",location);
            page.getResultItems().put("name",name);
            page.getResultItems().put("comment",comment.replaceAll("[^0-9]", ""));
            page.getResultItems().put("avg",avg.replaceAll("[^0-9]", ""));
            page.getResultItems().put("address",address);
            page.getResultItems().put("tel",tel);
        }

    }
}
