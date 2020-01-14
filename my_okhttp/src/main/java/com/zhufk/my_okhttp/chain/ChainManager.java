package com.zhufk.my_okhttp.chain;

import com.zhufk.my_okhttp.Call2;
import com.zhufk.my_okhttp.RealCall2;
import com.zhufk.my_okhttp.Request2;
import com.zhufk.my_okhttp.Response2;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName ChainManager
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/10 16:05
 * @Version 1.0
 */
public class ChainManager implements Chain2 {
    private final List<Interceptor2> interceptors;
    private final int index;
    private final Request2 request;
    private final RealCall2 call;

    public ChainManager(List<Interceptor2> interceptors, int index, Request2 request, RealCall2 call) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
        this.call = call;
    }

    public RealCall2 getCall(){
        return call;
    }

    @Override
    public Request2 getRequest2() {
        return request;
    }

    @Override
    public Response2 getResponse2(Request2 request2) throws IOException {
        if (index >= interceptors.size()) throw new AssertionError();
        ChainManager next = new ChainManager(interceptors, index + 1, request2, call);
        Interceptor2 interceptor2 = interceptors.get(index);
        Response2 response2 = interceptor2.doNext(next);
        return response2;
    }
}
