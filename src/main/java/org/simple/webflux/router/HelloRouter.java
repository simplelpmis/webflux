package org.simple.webflux.router;


import org.simple.webflux.handler.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * @Date 2018/5/31      @Author Simba
 * @Description:
 */
@Configuration
public class HelloRouter {
    /**
     * RouterFunctions 对请求路由处理类，即将请求路由到处理器，这里将一个 GET 请求 /hello 路由到处理器
     * cityHandler 的 helloCity 方法上。跟 Spring MVC 模式下的 HandleMapping 的作用类似。
     * RouterFunctions.route(RequestPredicate, HandlerFunction) 方法，
     * 对应的入参是请求参数和处理函数，如果请求匹配，就调用对应的处理器函数。
     */
    @Bean
    public RouterFunction<ServerResponse> routeHello(HelloHandler helloHandler) {
        return RouterFunctions
                .route(GET("/hello"), helloHandler::hello)
                .andRoute(GET("/date"), helloHandler::getDate)
                .andRoute(GET("/time"), helloHandler::getTime)
                .andRoute(GET("/times"), helloHandler::sendTimePerSec);
    }
}
