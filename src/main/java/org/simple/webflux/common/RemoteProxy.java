package org.simple.webflux.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date 2018/6/1      @Author Simba
 * @Description:
 */
public class RemoteProxy {
    private static final Logger logger = LoggerFactory.getLogger(RemoteProxy.class);

    public static void callRemote(CallBack callBack, String request) {
        new Thread(() -> callBack.process(request)).start();
        logger.info("proxy finished with {}", request);
    }
}
