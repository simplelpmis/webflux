package org.simple.webflux.handler;


import org.simple.webflux.common.CallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.MonoSink;

/**
 * @Date 2018/6/1      @Author Simba
 * @Description:
 */
public class CallBackBytes implements CallBack {

    private static final Logger logger = LoggerFactory.getLogger(CallBackBytes.class);

    private MonoSink monoSink;
    private String path;
    private byte[] bytes;

    public CallBackBytes(MonoSink monoSink, String path, byte[] bytes) {
        this.monoSink = monoSink;
        this.path = path;
        this.bytes = bytes;
    }

    @Override
    public void process(String result) {
        logger.info("process begin");
        monoSink.success(result);
    }
}
