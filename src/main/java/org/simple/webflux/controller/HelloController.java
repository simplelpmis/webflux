package org.simple.webflux.controller;


import org.simple.webflux.common.RemoteProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Date 2018/6/1      @Author Simba
 * @Description:
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hi")
    public Mono<String> hi() {
        return Mono.just("hi World");
    }

    @GetMapping("/exchange")
    public Mono<Void> exchange(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        DefaultDataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
        String path = request.getPath().pathWithinApplication().value();
        logger.info("path is {}", path);
        DataBuffer buf = dataBufferFactory.wrap("finish execute exchange".getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buf));
    }

    @GetMapping("/call")
    public Mono<String> tars() {
        return Mono.fromCallable(() -> getCallReult());
    }


    @GetMapping("/callback/{name}")
    public Mono<String> tars(@PathVariable String name) {
        logger.info("name is {}", name);
        return Mono.create(monoSink -> {
            CallBackString callback = new CallBackString(monoSink);
            RemoteProxy.callRemote(callback, name);
        });
    }

    private String getCallReult() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "call success";
    }
}
