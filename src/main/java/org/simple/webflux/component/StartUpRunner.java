package org.simple.webflux.component;


import org.simple.webflux.controller.HelloController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @Date 2018/6/2      @Author Simba
 * @Description:
 */
@Component
public class StartUpRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartUpRunner.class);

    @Resource
    private HelloController helloController;

    @Override
    public void run(ApplicationArguments args){
        Mono<String> result = helloController.hi();
        logger.info("init  result = {}", result.block());
    }
}
