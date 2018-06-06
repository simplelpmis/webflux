package org.simple.webflux.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

public class HiControllerTest {

    @Test
    public void hi() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8081");
        Mono<String> resp = webClient
                .get().uri("/hi")
                .retrieve() // 异步地获取response信息
                .bodyToMono(String.class);  // 将response body解析为字符串
        resp.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);  // 由于是异步的，我们将测试线程sleep 1秒确保拿到response
    }

    @Test
    public void times() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8081");
        webClient
                .get().uri("/times")
                .accept(MediaType.TEXT_EVENT_STREAM)    // Content-Type: text/event-stream，即SSE
                .retrieve()
                .bodyToFlux(String.class)
                .log()  // log()代替doOnNext(System.out::println)来查看每个元素；
                .take(10)   // times是一个无限流，这里取前10个，会导致流被取消；
                .blockLast();
    }
}