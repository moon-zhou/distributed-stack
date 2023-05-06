package org.moonzhou.distributedlock.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * org.apache.zookeeper.ZooKeeper 工具类
 * @author moon-zhou
 */
public class ZooKeeperUtil {

    /**
     * 创建持久节点
     *
     * @param zooKeeper
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static String createPersistentNode(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {

        //判断节点是否存在,如果不存在则直接创建，否则先删除再创建
        Stat stat = ZooKeeperUtil.exists(zooKeeper, path);
        if (null != stat) {
            System.out.println(path + " exists,delete it first!");
            ZooKeeperUtil.deleteNode(zooKeeper, path, stat.getVersion());
        } else {
            System.out.println("节点不存在直接创建新节点");
        }
        String resultPath = zooKeeper.create(path, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("node path =" + resultPath);
        return resultPath;
    }

    /**
     * 创建持久有序节点
     *
     * @param zooKeeper
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static String createPersistentSequentialNode(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        //有序节点不必判断节点是否存在，直接创建（可以用来创建全局id）
        String resultPath = zooKeeper.create(path, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("node path =" + resultPath);
        return resultPath;
    }

    /**
     * 创建临时节点
     *
     * @param zooKeeper
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static String createEphemaralNode(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        String resultPath = zooKeeper.create(path, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("node path =" + resultPath);
        return resultPath;
    }

    /**
     * 创建临时有序节点
     *
     * @param zooKeeper
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static String createEphemaraSequetiallNode(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        String resultPath = zooKeeper.create(path, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("node path =" + resultPath);
        return resultPath;
    }

    /**
     * 判断节点是否存在
     *
     * @param zooKeeper
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static Stat exists(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path, false);
        System.out.println("node exists =" + stat);
        return stat;
    }

    /**
     * 删除节点
     *
     * @param zooKeeper
     * @param path
     * @param version
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void deleteNode(ZooKeeper zooKeeper, String path, int version) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, version);
        System.out.printf("delete node sucess,nodepath = %s\r\n", path);
    }

    /**
     * 根据节点查询节点内容
     *
     * @param zooKeeper
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static String getData(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        String data = null;
        Stat stat = ZooKeeperUtil.exists(zooKeeper, path);
        byte[] bytes = zooKeeper.getData(path, null, stat);
        data = new String(bytes);
        System.out.printf("节点{%s} 对应的数据内容为{%s}\r\n", path, data);
        return data;
    }

    public static String getData(ZooKeeper zooKeeper, String path, Stat stat) throws KeeperException, InterruptedException {
        String data = null;
        byte[] bytes = zooKeeper.getData(path, null, stat);
        data = new String(bytes);
        System.out.printf("节点{%s} 对应的数据内容为{%s}\r\n", path, data);
        return data;
    }

    /**
     * 更新节点内容
     *
     * @param zooKeeper
     * @param path
     * @param value
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static Stat updateNode(ZooKeeper zooKeeper, String path, byte[] value) throws KeeperException, InterruptedException {
        Stat stat = ZooKeeperUtil.exists(zooKeeper, path);
        stat = zooKeeper.setData(path, value, stat.getVersion());
        System.out.printf("节点{%s} 修改后的内容为{%s}\r\n", path, new String(value));
        return stat;
    }
}