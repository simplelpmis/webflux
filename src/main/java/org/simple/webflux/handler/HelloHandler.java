package org.simple.webflux.handler;


import org.simple.webflux.common.RemoteProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @Date 2018/5/31      @Author Simba
 * @Description:
 */
@Component
public class HelloHandler {
    private static final Logger logger = LoggerFactory.getLogger(HelloHandler.class);

    /**
     * ServerResponse 是对响应的封装，可以设置响应状态、响应头、响应正文。
     * 比如 ok 代表的是 200 响应码、MediaType 枚举是代表这文本内容类型、返回的是 String 的对象。
     * 这里用 Mono 作为返回对象，是因为返回包含了一个 ServerResponse 对象，而不是多个元素
     */
    public Mono<ServerResponse> hello(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello, World Flux!"));
    }

    public Mono<ServerResponse> getTime(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).
                body(Mono.just("Now is " + new SimpleDateFormat("HH:mm:ss").format(new Date())), String.class);
    }

    public Mono<ServerResponse> getDate(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).
                body(Mono.just("Today is " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())), String.class);
    }

    public Mono<ServerResponse> sendTimePerSec(ServerRequest serverRequest) {
        // 每秒推送一次
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                Flux.interval(Duration.ofSeconds(1)).
                        map(l -> " hello jason, " + new SimpleDateFormat("HH:mm:ss").format(new Date())),
                String.class);
    }

    public Mono<ServerResponse> invokeRemote(ServerRequest serverRequest) {
        String originPath = serverRequest.path();
        if (StringUtils.isEmpty(originPath)) {
            return Mono.empty();
        }
        Mono<byte[]> req = serverRequest.bodyToMono(byte[].class);
        return Mono.create(monoSink ->
                req.subscribe(bytes -> {
                    logger.info("bloke length is {}", bytes.length);
                    CallBackBytes callback = new CallBackBytes(monoSink, originPath, bytes);
                    RemoteProxy.callRemote(callback, bytes.toString());
                })
        );
    }
}
