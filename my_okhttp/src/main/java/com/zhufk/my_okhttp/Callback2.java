package com.zhufk.my_okhttp;

import java.io.IOException;

/**
 * @ClassName Callback2
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/10 9:25
 * @Version 1.0
 */
public interface Callback2 {
    void onFailure(Call2 call, IOException e);
    void onResponse(Call2 call, Response2 response) throws IOException;
}
