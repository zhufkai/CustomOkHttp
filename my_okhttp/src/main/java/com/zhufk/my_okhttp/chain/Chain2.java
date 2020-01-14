package com.zhufk.my_okhttp.chain;

import com.zhufk.my_okhttp.Request2;
import com.zhufk.my_okhttp.Response2;

import java.io.IOException;

/**
 * @ClassName Chain2
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/10 15:58
 * @Version 1.0
 */
public interface Chain2 {
    Request2 getRequest2();

    Response2 getResponse2(Request2 request2) throws IOException;
}
