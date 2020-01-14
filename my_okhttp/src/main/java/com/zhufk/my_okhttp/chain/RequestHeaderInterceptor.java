package com.zhufk.my_okhttp.chain;

import com.zhufk.my_okhttp.Response2;
import com.zhufk.my_okhttp.SocketRequestServer;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName RequestHeaderInterceptor
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/10 16:59
 * @Version 1.0
 */
public class RequestHeaderInterceptor implements Interceptor2{

    @Override
    public Response2 doNext(Chain2 chain2) throws IOException {
        ChainManager chainManager = (ChainManager) chain2;
        Map<String, String> headerList = chainManager.getRequest2().getmHeaderList();
        headerList.put("Host",new SocketRequestServer().getHost(chainManager.getRequest2()));

        if ("POST".equalsIgnoreCase(chainManager.getRequest2().getRequestMethod())){
            //Content-Type: text/html
            headerList.put("Content-Type","text/html");
        }else{

        }
        return null;
    }
}
