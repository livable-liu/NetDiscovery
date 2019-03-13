package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.Spider;
import com.cv4j.netdiscovery.core.domain.Request;
import com.cv4j.netdiscovery.core.downloader.file.FileDownloadAfterRequest;
import com.cv4j.netdiscovery.core.downloader.file.FileDownloader;
import com.cv4j.netdiscovery.selenium.Browser;
import com.cv4j.netdiscovery.selenium.pool.WebDriverPool;
import com.cv4j.netdiscovery.selenium.pool.WebDriverPoolConfig;
import io.vertx.core.impl.ConcurrentHashSet;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class TaobaoSpider {
    public static final ConcurrentHashSet<String> urlList = new ConcurrentHashSet<>();

    private static HashMap<String, Integer> count(String path) throws IOException {


        HashMap<String, Integer> urlCount = new HashMap<>();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(path), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String str = br.readLine();
        while (str != null) {
            String[] array = str.split("###");
            if (array.length != 2 || array[0].length() <= 1) {
                str = br.readLine();
                continue;
            }
            Integer count = urlCount.get(array[1]);
            if (count != null) {
                count ++;
                urlCount.put(array[1], count);
            }
            else {
                urlCount.put(array[1], 1);
            }
            str = br.readLine();
        }
        return urlCount;
    }

    public static void main(String[] args) throws IOException {

        WebDriverPoolConfig config = new WebDriverPoolConfig("example/chromedriver", Browser.CHROME); //设置浏览器的驱动程序和浏览器的类型，浏览器的驱动程序要跟操作系统匹配。
        WebDriverPool.init(config); // 需要先使用init，才能使用WebDriverPool

//        List<SeleniumAction> actions = new ArrayList<>();
//        actions.add(new BrowserAction());
//        actions.add(new SearchAction());
//        actions.add(new SortAction());

        File folder = new File("/home/livable/Downloads/Chanel/");
        String[] fileNames = folder.list();
        HashSet<String> downloaded = new HashSet<>();
        for (String name : fileNames) {
            downloaded.add(name);
        }

//        BufferedReader br=new BufferedReader(new FileReader("D:/taobao_img.txt"));
        InputStreamReader isr = new InputStreamReader(new FileInputStream("/home/livable/tmp/taobao_img.txt"), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String str = br.readLine();
        HashMap<String, Integer> urls = count("/home/livable/tmp/taobao_img.txt");

        while (str != null) {
            String[] array = str.split("###");
            Request request = new Request(array[0]);

            if (array.length != 2 || array[0].length() <= 1) {
                str = br.readLine();
                continue;
            }

            if (urls.get(array[1]) == null || urls.get(array[1]) <= 1) {
                str = br.readLine();
                continue;
            }

            if (!downloaded.contains(array[1].replace("*", "").replace("\\","").replace("/",""))) {
                request.afterRequest(new FileDownloadAfterRequest("/home/livable/Downloads/Chanel/" + array[1].replace("*", "").replace("\\", "").replace("/",""), array[0].substring(array[0].lastIndexOf("/") + 1))); // 在使用FileDownloader时，可以使用AfterRequest或者Pipeline对文件进行保存等处理。这里使用FileDownloadAfterRequest
            }
            else {
                str = br.readLine();
                continue;
            }

            Spider.create().name("tony")
                    .request(request)
                    .downloader(new FileDownloader()) // 文件的下载需要使用FileDownloader
                    .run();
            try {
                Thread.sleep((int) (500 + Math.random() * (2000 - 500 + 1)));
            }
            catch (Exception e) {
            }
            str = br.readLine();
        }

//        String url = "https://search.jd.com/";
//        String url = "http://auction.jd.com/paimai_list.html?t=1&t=1&searchParam=lv&parentCateId=12667&childrenCateId=12682&limit=40&page=";
//        String url = "https://paimai.taobao.com/pmp_list/3_53856004___1_1.htm";
//        Request request = new Request(url);
//        request.httpMethod(HttpMethod.GET);
//        request.addCookie("swfstore=42156; miid=415629401187031831; cna=nHdhFLDtaHUCAXVngRbDxqdp; t=13923fa312d2852fbb2fa5b2beafc937; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; tracknick=xiaoshaxing13; lgc=xiaoshaxing13; tg=0; enc=RYPOMypaaXOvUHTZYQJ%2BkBzCbnHAa5X%2BFWx8U%2FiqK2IB1H30seQi6ZL8iEh7IX9c1pEknweVN%2FstSKAkgJdaQQ%3D%3D; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; v=0; cookie2=1f5cef7129ec5a17db444ce8c39a84be; _tb_token_=7e99b13170e35; UM_distinctid=167d3f8fda663f-04508a374f9d18-6313363-1fa400-167d3f8fda755b; CNZZDATA1253345903=810239323-1545444312-%7C1545460578; unb=82042271; sg=31b; _l_g_=Ug%3D%3D; cookie1=VyUE%2Fck%2Bf7tRPd5CYBgW4tVutXon4pkShweCAEjSDw4%3D; dnk=xiaoshaxing13; _nk_=xiaoshaxing13; cookie17=W8t0vkqoAi8%3D; mt=ci=76_1&np=; skt=3003676104f9609d; csg=2b169a37; uc3=vt3=F8dByRMDDXf3wZExglI%3D&id2=W8t0vkqoAi8%3D&nk2=G4mgLCB0QqTxdP6R0A%3D%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; existShop=MTU0NTQ2NTc0MQ%3D%3D; _cc_=U%2BGCWk%2F7og%3D%3D; l=Auvrui4RKM4T91cETvNrx56i-wTVc/-C; uc1=cookie14=UoTYM8KHn%2BvVyg%3D%3D&lng=zh_CN&cookie16=URm48syIJ1yk0MX2J7mAAEhTuw%3D%3D&existShop=false&cookie21=W5iHLLyFe3xm&tag=8&cookie15=VT5L2FSpMGV7TQ%3D%3D&pas=0; isg=BBcXO_RZB3Na9IP_tvIwdDLZpouh9OqiodJsWWlEauZBmDfacSoPDqn6_ngjcMM2; whl=-1%260%260%261545465849111");
//        request.ua("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
//        for (int i = 1; i <= 100; i ++) {
//            actions.clear();
////            actions.add(new BrowserAction(i));
//            Spider.create()
//                    .name("jd")
//                    .request(request)
//                    .downloader(seleniumDownloader)
//                    .parser(new PriceParser())
//                    .pipeline(new PricePipeline())
//                    .run();
//        }
    }
}
