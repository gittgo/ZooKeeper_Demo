package com.zk.demo.rm.curator.api;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;

/**
 * Curator客户端框架 判断节点存在
 *
 * @author Gerry
 * @date 2019/01/06 17:24
 */
public class NodeExistsDemo {

    /** 重试策略:重试间隔时间为1000ms; 最多重试3次; */
    private static RetryPolicy retryPolicy = new RetryNTimes(3, 1000);

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",5000,
                5000, retryPolicy);
        // 启动客户端
        client.start();

        Stat stat = client.checkExists().forPath("/");
        System.out.println(stat == null ? "节点不存在!" : "节点存在!");
    }

}
