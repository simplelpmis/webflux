package org.simple.webflux.controller;


import org.simple.webflux.common.CallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.MonoSink;

/**
 * @Date 2018/6/1      @Author Simba
 * @Description:
 */
public class CallBackString implements CallBack {

    private static final Logger logger = LoggerFactory.getLogger(CallBackString.class);

    MonoSink monoSink;

    public CallBackString(MonoSink monoSink) {
        this.monoSink = monoSink;
    }

    @Override
    public void process(String result) {
        logger.info("process begin");
        monoSink.success(result);
    }
}
