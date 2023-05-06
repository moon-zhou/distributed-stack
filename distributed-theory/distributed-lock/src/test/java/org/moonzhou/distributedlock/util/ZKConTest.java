package org.moonzhou.distributedlock.util;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * main测试ZooKeeper client连接<br>
 *     包含watch
 *     latch作为连接的限制条件是没必要的，如果作为必须监听建立成功之后再进行zk操作的话，才有必要
 *
 * @author moon-zhou
 * @Date: 2020/1/19 10:42
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ZKConTest {

    private static String CONNECT_SERVER = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static int SESSION_TIMEOUT = 3000;

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {

        ZooKeeper zooKeeper = null;

        try {
            zooKeeper = new ZooKeeper(CONNECT_SERVER, SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        // 确认已经连接完毕后再进行操作
                        //latch.countDown();
                        System.out.println("已经获得了连接");
                    }
                }
            });

            //连接完成之前先等待
            //latch.await();
            ZooKeeper.States states = zooKeeper.getState();
            System.out.println("状态：" + states);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != zooKeeper) {
                try {
                    System.out.println("关闭连接");
                    zooKeeper.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
