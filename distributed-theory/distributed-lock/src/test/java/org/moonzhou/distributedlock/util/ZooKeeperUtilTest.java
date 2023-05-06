package org.moonzhou.distributedlock.util;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.moonzhou.distributedlock.zk.ZooKeeperUtil;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeperUtilTest {

    private static String CONNECT_SERVER = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static int SESSION_TIMEOUT = 4000;

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(CONNECT_SERVER, SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("event===" + event);
                    if (Event.KeeperState.SyncConnected.equals(event.getState())) {
                        System.out.println("syncConnect success");
                    }
                }
            });

            System.out.println(zooKeeper.getState());
            Thread.sleep(1000);
            System.out.println("after sleep 1 second");
            System.out.println(zooKeeper.getState());
            // zooKeeper.create("/rootdemo","0".getBytes(),)
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private ZooKeeper zooKeeper;

    @Before
    public void zooKeeperConnect() {
        final CountDownLatch zkSemaphore = new CountDownLatch(1);
        try {
            zooKeeper = new ZooKeeper(CONNECT_SERVER, SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        zkSemaphore.countDown();
                        System.out.println("connected success!");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateNode_Ephemeral() {
        String ephemaralNodePath = "/root_ephemaral";
        try {
            String nodePath = ZooKeeperUtil.createEphemaralNode(zooKeeper, ephemaralNodePath);
            //System.in.read();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateNode_EphemeralSequential() {
        String ephemaralNodePath = "/root_ephemaral_sequentail_";
        try {
            ZooKeeperUtil.createEphemaraSequetiallNode(zooKeeper, ephemaralNodePath);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateNode_persistent() {
        String persistentPath = "/root_persistent";
        try {
            ZooKeeperUtil.createPersistentNode(zooKeeper, persistentPath);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateNode_PersistentSequential() {
        String nodePath = "/root_persistent_squential_";
        try {
            String persistentSequentialNode = ZooKeeperUtil.createPersistentSequentialNode(zooKeeper, nodePath);
            Stat exists = ZooKeeperUtil.exists(zooKeeper, persistentSequentialNode);
            ZooKeeperUtil.deleteNode(zooKeeper, persistentSequentialNode, exists.getVersion());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateNode() {
        String nodePath = "/root_ephemaraSequetiallNode";
        try {
            //1.创建节点
            String sequetiallNode = ZooKeeperUtil.createEphemaraSequetiallNode(zooKeeper, nodePath);

            //2.获取创建的节点原有数据
            String data = ZooKeeperUtil.getData(zooKeeper, sequetiallNode);
            System.out.println("原有数据：" + data);

            //3.修改数据
            Stat stat2 = ZooKeeperUtil.updateNode(zooKeeper, sequetiallNode, "updated value".getBytes());
            String data1 = ZooKeeperUtil.getData(zooKeeper, sequetiallNode, stat2);
            System.out.println("修改后的数据：" + data1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}