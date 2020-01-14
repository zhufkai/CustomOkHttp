package com.zhufk.my_okhttp.chain;

import com.zhufk.my_okhttp.OkHttpClient2;
import com.zhufk.my_okhttp.RealCall2;
import com.zhufk.my_okhttp.Response2;

import java.io.IOException;

/**
 * @ClassName ReRequestInterceptor
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/10 16:03
 * @Version 1.0
 */
public class ReRequestInterceptor implements Interceptor2 {
    @Override
    public Response2 doNext(Chain2 chain2) throws IOException {

        ChainManager chainManager = (ChainManager) chain2;

        RealCall2 call2 = chainManager.getCall();
        OkHttpClient2 client2 = call2.getClient2();

        IOException ioException = null;

        if (client2.getCount() != 0) {
            for (int i = 0; i < client2.getCount(); i++) {
                try {
                    Response2 response = chain2.getResponse2(chainManager.getRequest2());
                    return response;
                } catch (IOException e) {
                    ioException = e;
                }
            }
        }

        throw ioException;
    }
}
