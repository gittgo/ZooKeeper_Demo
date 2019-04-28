package com.zk.demo.stat;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @ProjectName: zk-demo
 * @Auther: GERRY
 * @Date: 2019/1/7 20:13
 * @Description:
 */
public class CreateZkSession implements Watcher {
    private static Logger logger = LoggerFactory.getLogger(CreateZkSession.class);
    private static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) throws InterruptedException {

        try {
            // 创建新的会话
            ZooKeeper zkc = new ZooKeeper("localhost:2181",5000,new CreateZkSession());
            //latch.await();
            // 连接sessionId和密码
            /*long sessionId = zkc.getSessionId();
            byte[] sessionPasswd = zkc.getSessionPasswd();

            ZooKeeper zkc1 = new ZooKeeper("localhost:2181",5000,new CreateZkSession(), 0x111, sessionPasswd);
            logger.warn("====继续执行====");*/

            // 创建节点
            // OPEN_ACL_UNSAFE world:anyone:rwcda
            zkc.exists("/test",true); // 监听注册一次 使用一次
            zkc.create("/test","test-data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            //TimeUnit.SECONDS.sleep(1);
            // 更改节点数据
            // -1 代表不考虑版本进行修改数据
            zkc.exists("/test",true);
            zkc.setData("/test","test-data-modify".getBytes(), -1);
            //TimeUnit.SECONDS.sleep(1);
            // 删除节点
            zkc.exists("/test",true);
            zkc.delete("/test", -1);
            //TimeUnit.SECONDS.sleep(1);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void process(WatchedEvent event) {
        Event.KeeperState state = event.getState();

        if (state == Event.KeeperState.SyncConnected) {
            Event.EventType type = event.getType();
            if (type == Event.EventType.None) {
                logger.warn("zk连接成功");
                //latch.countDown();
            }

            if (type == Event.EventType.NodeCreated) {
                logger.warn("节点创建");
            }

            if (type == Event.EventType.NodeDeleted) {
                logger.warn("节点删除");
            }

            if (type == Event.EventType.NodeDataChanged) {
                logger.warn("节点数据变化");
            }

        } else if (state == Event.KeeperState.Expired) {
            logger.warn("会话过期");
        } else if (state == Event.KeeperState.AuthFailed) {
            logger.warn("权限不足....");
        } else if (state == Event.KeeperState.Disconnected) {
            logger.warn("连接断开....");
        }
    }
}
