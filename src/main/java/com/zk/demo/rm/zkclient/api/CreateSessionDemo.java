package com.zk.demo.rm.zkclient.api;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * ZkClient客户端框架 创建Session
 *
 * @author Gerry
 * @date 2019/01/06 14:25
 */
public class CreateSessionDemo {
    public static void main(String[] args) {
        // SerializableSerializer为序列化器，这样一来，我们在节点存放/读取数据时，就不需要
        // 手动将数据对象转换成自己数组 或 手动将字节数组转换为数据对象了
        ZkClient zkClient = new ZkClient("localhost:2181", 10000, 10000, new SerializableSerializer());
    }
}
