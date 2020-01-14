package com.zhufk.my_okhttp;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Dispatcher2
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/10 10:04
 * @Version 1.0
 */
public class Dispatcher2 {

    private int maxRequests = 64;//同时访问的任务最大数量
    private int maxRequestsPerHost = 5;//服务器主机最大访问数

    private ExecutorService executorService;

    private Deque<RealCall2.AsyncCall2> runningAsyncCalls = new ArrayDeque<>();
    private Deque<RealCall2.AsyncCall2> readyAsyncCalls = new ArrayDeque<>();

    public synchronized void enqueue(RealCall2.AsyncCall2 asyncCall2) {
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(asyncCall2) < maxRequestsPerHost) {
            runningAsyncCalls.add(asyncCall2);
            executorService().execute(asyncCall2);
        } else {
            readyAsyncCalls.add(asyncCall2);
        }
    }

    public ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60,
                    TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread thread = new Thread(r);
                            thread.setName("自定义的线程");
                            thread.setDaemon(false);
                            return thread;
                        }
                    });
        }
        return executorService;
    }

    private int runningCallsForHost(RealCall2.AsyncCall2 asyncCall2) {
        int count = 0;
        if (runningAsyncCalls.isEmpty()) {
            return 0;
        }

        SocketRequestServer server = new SocketRequestServer();

        for (RealCall2.AsyncCall2 call : runningAsyncCalls) {
            if (server.getHost(call.getRequest()).equals(server.getHost(asyncCall2.getRequest()))) {
                count++;
            }
        }

        return count;
    }

    public void finish(RealCall2.AsyncCall2 call2) {

        if (!runningAsyncCalls.remove(call2)) throw new AssertionError("Call wasn't in-flight!");

        if (runningAsyncCalls.size() >= maxRequests) return; // Already running max capacity.
        if (readyAsyncCalls.isEmpty()) return; // No ready calls to promote.
        for (Iterator<RealCall2.AsyncCall2> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
            RealCall2.AsyncCall2 call = i.next();

            if (runningCallsForHost(call) < maxRequestsPerHost) {
                i.remove();
                runningAsyncCalls.add(call);
                executorService().execute(call);
            }

            if (runningAsyncCalls.size() >= maxRequests) return; // Reached max capacity.
        }
    }
}
