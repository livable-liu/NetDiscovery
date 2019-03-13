package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.domain.Page;
import com.cv4j.netdiscovery.core.parser.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by tony on 2018/6/12.
 */
public class FileParser implements Parser{

    @Override
    public void process(Page page) {

        String pageHtml = page.getHtml().toString();
        Document document = Jsoup.parse(pageHtml);

//        Elements elements = document.select("a[class=m]");
        Elements elements = document.select("table[id=ocr_list] a");
        if (elements.size() > 0) {
            String url = elements.get(0).attr("href");
            System.out.println(url);
        }

    }
}
