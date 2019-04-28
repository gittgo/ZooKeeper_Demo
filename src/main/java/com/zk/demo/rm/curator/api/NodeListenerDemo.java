package com.zk.demo.rm.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.TimeUnit;

/**
 * Curator客户端框架 监听节点
 *
 * @author Gerry
 * @date 2019/01/06 17:24
 */
public class NodeListenerDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",5000,
                5000, retryPolicy);
        // 启动客户端
        client.start();


        final NodeCache cache = new NodeCache(client,"/b");
        // 开始监听
        cache.start();
        // 触发事件为创建节点和更新节点，在删除节点的时候并不触发此操作。
        cache.getListenable().addListener(new NodeCacheListener() {

            @Override
            public void nodeChanged() throws Exception {
                byte[] newData = cache.getCurrentData().getData();
                System.out.println("现在的节点数据是:"+new String(newData));
            }
        });

        // 为了看见控制台输出，我们不能让main线程死得太早
        TimeUnit.MILLISECONDS.sleep(100000);

    }

}

