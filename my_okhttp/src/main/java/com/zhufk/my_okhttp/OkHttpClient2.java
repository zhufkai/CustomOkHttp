package com.zhufk.my_okhttp;

/**
 * @ClassName OkHttpClient2
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/10 9:04
 * @Version 1.0
 */
public class OkHttpClient2 {

    private int count = 3;

    private boolean isCanceled;

    private Dispatcher2 dispatcher2;

    public OkHttpClient2() {
        this(new Builder());
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public int getCount() {
        return count;
    }

    public OkHttpClient2(Builder builder) {
        this.dispatcher2 = builder.dispatcher2;
        this.isCanceled = builder.isCanceled;
        this.count = builder.count;
    }

    public final static class Builder {

        Dispatcher2 dispatcher2;

        boolean isCanceled = false;

        private int count;

        public Builder() {
            dispatcher2 = new Dispatcher2();
        }

        public Builder dispatcher(Dispatcher2 dispatcher2) {
            this.dispatcher2 = dispatcher2;
            return this;
        }

        public Builder canceled() {
            isCanceled = true;
            return this;
        }

        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        public OkHttpClient2 build() {
            return new OkHttpClient2(this);
        }
    }

    public Call2 newCall(Request2 request) {
        return new RealCall2(this, request);
    }

    public Dispatcher2 dispatcher() {
        return dispatcher2;
    }

}
