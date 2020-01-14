package com.zhufk.my_okhttp.chain;

import com.zhufk.my_okhttp.Response2;

import java.io.IOException;

/**
 * @ClassName Interceptor2
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/10 15:57
 * @Version 1.0
 */
public interface Interceptor2 {
    Response2 doNext(Chain2 chain2) throws IOException;
}
