package com.zhufk.my_okhttp;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Request2
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/10 9:28
 * @Version 1.0
 */
public class Request2 {
    public static final String GET = "GET";
    public static final String POST = "POST";

    private String url;

    private String requestMethod = GET;

    private Map<String, String> mHeaderList = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Map<String, String> getmHeaderList() {
        return mHeaderList;
    }

    public Request2() {
        this(new Builder());
    }

    public Request2(Builder builder) {
        this.url = builder.url;
        this.mHeaderList = builder.mHeaderList;
        this.requestMethod = builder.requestMethod;
    }

    public final static class Builder {
        private String url;
        private String requestMethod = GET;
        private Map<String, String> mHeaderList = new HashMap<>();

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder get() {
            requestMethod = GET;
            return this;
        }

        public Builder post() {
            requestMethod = POST;
            return this;
        }

        public Builder addRequestHeader(String key, String value) {
            mHeaderList.put(key, value);
            return this;
        }

        public Request2 build() {
            return new Request2(this);
        }
    }
}
