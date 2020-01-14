package com.zhufk.my_okhttp;

import com.zhufk.my_okhttp.chain.ChainManager;
import com.zhufk.my_okhttp.chain.Interceptor2;
import com.zhufk.my_okhttp.chain.ReRequestInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RealCall2
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/10 9:56
 * @Version 1.0
 */
public class RealCall2 implements Call2 {
    private boolean executed;

    private OkHttpClient2 client2;
    private Request2 request2;

    public RealCall2(OkHttpClient2 client2, Request2 request2) {
        this.client2 = client2;
        this.request2 = request2;
    }

    public OkHttpClient2 getClient2(){
        return client2;
    }

    @Override
    public void enqueue(Callback2 responseCallback) {
        synchronized (this) {
            if (executed) {
                executed = true;
                throw new IllegalStateException("Already Executed");
            }
        }

        client2.dispatcher().enqueue(new AsyncCall2(responseCallback));
    }

    final class AsyncCall2 implements Runnable {

        private Callback2 callback2;

        public Request2 getRequest() {
            return RealCall2.this.request2;
        }

        public AsyncCall2(Callback2 callback2) {
            this.callback2 = callback2;
        }

        @Override
        public void run() {
            boolean signalledCallback = false;
            try {
                Response2 response = getResponseWithInterceptorChain();
                if (client2.isCanceled()) {
                    signalledCallback = true;
                    callback2.onFailure(RealCall2.this, new IOException("Canceled"));
                } else {
                    signalledCallback = true;
                    callback2.onResponse(RealCall2.this, response);
                }
            } catch (Exception e) {
                if (signalledCallback) {
                    System.out.println("用户使用出错...");
                } else {
                    callback2.onFailure(RealCall2.this, new IOException("OKHTTP getResponseWithInterceptorChain 错误...e:" + e.toString()));
                }
            } finally {
                client2.dispatcher().finish(this);
            }
        }

        private Response2 getResponseWithInterceptorChain() throws IOException {
            List<Interceptor2> list = new ArrayList<>();
            list.add(new ReRequestInterceptor());
            ChainManager manager = new ChainManager(list,0,request2,RealCall2.this);
            return manager.getResponse2(request2);
        }
    }


}
