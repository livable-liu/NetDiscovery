package com.cv4j.netdiscovery.core;

import com.alibaba.fastjson.JSON;
import com.cv4j.netdiscovery.core.domain.SpiderEntity;
import com.cv4j.netdiscovery.core.domain.response.SpiderResponse;
import com.cv4j.netdiscovery.core.domain.response.SpidersResponse;
import com.cv4j.netdiscovery.core.domain.response.SpiderStatusResponse;
import com.cv4j.netdiscovery.core.queue.DefaultQueue;
import com.cv4j.netdiscovery.core.queue.Queue;
import com.cv4j.proxy.ProxyPool;
import com.cv4j.proxy.domain.Proxy;
import com.safframework.tony.common.collection.NoEmptyHashMap;
import com.safframework.tony.common.utils.Preconditions;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 可以管理多个Spider的容器
 * Created by tony on 2018/1/2.
 */
@Slf4j
public class SpiderEngine {

    private Map<String,Spider> spiders = new NoEmptyHashMap<>();

    @Getter
    private Queue queue;

    private HttpServer server;

    private SpiderEngine() {
    }

    private SpiderEngine(Queue queue) {

        this.queue = queue;
    }

    public static SpiderEngine create() {

        return new SpiderEngine();
    }

    public static SpiderEngine create(Queue queue) {

        return new SpiderEngine(queue);
    }

    public SpiderEngine proxyList(List<Proxy> proxies) {

        ProxyPool.addProxyList(proxies);
        return this;
    }

    /**
     * 添加爬虫到SpiderEngine，由SpiderEngine来管理
     * @param spider
     * @return
     */
    public SpiderEngine addSpider(Spider spider) {

        if (spider!=null) {

            if (!spiders.containsKey(spider.getName())) {
                spiders.put(spider.getName(),spider);
            }
        }
        return this;
    }

    /**
     * 在SpiderEngine中创建一个爬虫
     * @param name
     * @return Spider
     */
    public Spider createSpider(String name) {

        if (!spiders.containsKey(name)) {

            Spider spider = Spider.create(this.getQueue()).name(name);
            spiders.put(name,spider);
            return spider;
        }

        return null;
    }

    /**
     * 对各个爬虫的状态进行监测，并返回json格式。
     * 如果要使用此方法，须放在run()之前
     * @param port
     */
    public void httpd(int port) {

        server = Vertx.vertx().createHttpServer();

        Router router = Router.router(Vertx.vertx());
        router.route().handler(BodyHandler.create());

        if (Preconditions.isNotBlank(spiders)) {

            for (Map.Entry<String,Spider> entry:spiders.entrySet()) {

                final Spider spider = entry.getValue();

                router.route("/netdiscovery/spider/"+spider.getName()).handler(routingContext -> {

                    // 所有的请求都会调用这个处理器处理
                    HttpServerResponse response = routingContext.response();
                    response.putHeader("content-type", "application/json");

                    SpiderEntity entity = new SpiderEntity();
                    entity.setSpiderName(spider.getName());
                    entity.setSpiderStatus(spider.getSpiderStatus());
                    entity.setLeftRequestSize(spider.getQueue().getLeftRequests(spider.getName()));
                    entity.setTotalRequestSize(spider.getQueue().getTotalRequests(spider.getName()));
                    entity.setQueueType(spider.getQueue().getClass().getSimpleName());
                    entity.setDownloaderType(spider.getDownloader().getClass().getSimpleName());

                    SpiderResponse spiderResponse = new SpiderResponse();
                    spiderResponse.setCode(200);
                    spiderResponse.setMessage("success");
                    spiderResponse.setData(entity);

                    // 写入响应并结束处理
                    response.end(JSON.toJSONString(spiderResponse));
                });

                router.post("/netdiscovery/spider/"+spider.getName()+"/status").handler(routingContext -> {

                    // 所有的请求都会调用这个处理器处理
                    HttpServerResponse response = routingContext.response();
                    response.putHeader("content-type", "application/json");

                    JsonObject json = routingContext.getBodyAsJson();

                    SpiderStatusResponse spiderStatusResponse = null;

                    if (json!=null) {

                        int status = json.getInteger("status");

                        spiderStatusResponse = new SpiderStatusResponse();

                        switch (status) {

                            case Spider.SPIDER_STATUS_PAUSE: {
                                spider.pause();
                                spiderStatusResponse.setData(String.format("SpiderEngine pause Spider %s success",spider.getName()));
                                break;
                            }

                            case Spider.SPIDER_STATUS_RESUME: {
                                spider.resume();
                                spiderStatusResponse.setData(String.format("SpiderEngine resume Spider %s success",spider.getName()));
                                break;
                            }

                            case Spider.SPIDER_STATUS_STOPPED: {
                                spider.forceStop();
                                spiderStatusResponse.setData(String.format("SpiderEngine stop Spider %s success",spider.getName()));
                                break;
                            }

                            default:
                                break;
                        }
                    }

                    spiderStatusResponse.setCode(200);
                    spiderStatusResponse.setMessage("success");

                    // 写入响应并结束处理
                    response.end(JSON.toJSONString(spiderStatusResponse));
                });
            }

            router.route("/netdiscovery/spiders/").handler(routingContext -> {

                // 所有的请求都会调用这个处理器处理
                HttpServerResponse response = routingContext.response();
                response.putHeader("content-type", "application/json");

                List<SpiderEntity> list = new ArrayList<>();

                Spider spider = null;
                SpiderEntity entity = null;

                for (Map.Entry<String,Spider> entry:spiders.entrySet()) {

                    spider = entry.getValue();

                    entity = new SpiderEntity();
                    entity.setSpiderName(spider.getName());
                    entity.setSpiderStatus(spider.getSpiderStatus());
                    entity.setLeftRequestSize(spider.getQueue().getLeftRequests(spider.getName()));
                    entity.setTotalRequestSize(spider.getQueue().getTotalRequests(spider.getName()));
                    entity.setQueueType(spider.getQueue().getClass().getSimpleName());
                    entity.setDownloaderType(spider.getDownloader().getClass().getSimpleName());
                    list.add(entity);
                }

                SpidersResponse spidersResponse = new SpidersResponse();
                spidersResponse.setCode(200);
                spidersResponse.setMessage("success");
                spidersResponse.setData(list);

                // 写入响应并结束处理
                response.end(JSON.toJSONString(spidersResponse));
            });
        }

        server.requestHandler(router::accept).listen(port);
    }

    /**
     * 关闭HttpServer
     */
    public void closeServer() {

        if (server!=null) {

            server.close();
        }
    }

    /**
     * 启动SpiderEngine中所有的spider，让每个爬虫并行运行
     */
    public void run() {

        if (Preconditions.isNotBlank(spiders)) {

            Flowable.fromIterable(spiders.values())
                    .flatMap(new Function<Spider, Publisher<?>>() {
                        @Override
                        public Publisher<?> apply(Spider spider) throws Exception {

                            return Flowable.just(spider)
                                    .subscribeOn(Schedulers.io())
                                    .map(new Function<Spider, Void>() {

                                        @Override
                                        public Void apply(Spider spider) throws Exception {

                                            spider.run();

                                            return null;
                                        }
                                    });
                        }
                    })
                    .subscribe();
        }
    }

    /**
     * 停止某个爬虫程序
     * @param name
     */
    public void stopSpider(String name) {

        Spider spider = spiders.get(name);

        if (spider!=null) {

            spider.stop();
        }
    }

    /**
     * 停止所有的爬虫程序
     */
    public void stopSpiders() {

        if (Preconditions.isNotBlank(spiders)) {

            spiders.forEach((s,spider)->spider.stop());
        }
    }
}
