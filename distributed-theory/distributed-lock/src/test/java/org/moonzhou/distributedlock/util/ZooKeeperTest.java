package org.moonzhou.distributedlock.util;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

/**
 * zk基本测试操作<br>
 *     使用的是Apache zk client
 *     为方便每次执行都能成功，测试数据在验证完时都需要删除，因此使用配置和方法命名来控制测试方法的执行顺序
 *
 * @author moon-zhou
 * @Date: 2020/1/16 19:56
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZooKeeperTest {

    // 单机配置
    //    private static String CONNECT_SERVER = "127.0.0.1:2181";
    /**
     * 此demo使用的集群，所以有多个ip和端口
     */
    private static String CONNECT_SERVER = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static int SESSION_TIMEOUT = 3000;
    private static int CONNECTION_TIMEOUT = 3000;

    /**
     * 测试节点名称
     * 必须是根节点开始
     */
    private static final String TEST_NODE = "/idea";

    /**
     * 根节点
     */
    private static final String ROOT = "/";

    /**
     * 类变量加载一次客户端
     */
    /*private static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper(CONNECT_SERVER, SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    // 收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
                    System.out.println(watchedEvent.getType() + "---" + watchedEvent.getPath());
                    try {
                        zooKeeper.getChildren("/", true);
                    } catch (Exception e) {
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private ZooKeeper zooKeeper;

    /**
     * Before会在每次test方法执行前执行，因此每一次运行结束，都需要通过After关闭客户端
     *
     * @throws Exception
     */
    @Before
    public void init() throws Exception {
        System.out.println("init zk client......");

        zooKeeper = new ZooKeeper(CONNECT_SERVER, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                //收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
                System.out.println("回调函数：" + watchedEvent.getType() + "---" + watchedEvent.getPath());
                try {
                    List<String> children = zooKeeper.getChildren("/", true);
                    children.forEach(System.out::println);
                } catch (Exception e) {
                }
            }
        });
    }

    @After
    public void closeClient() throws InterruptedException {
        System.out.println("close client......");

        zooKeeper.close();
    }

    /**
     * 数据的增删改查
     */

    /**
     * 创建数据节点到zk中
     *
     * @throws Exception
     */
    @Test
    public void test001_Create() throws Exception {
        //参数1，要创建的节点的路径 参数2：节点数据 参数3：节点的权限 参数4：节点的类型
        String node = zooKeeper.create(TEST_NODE, "hellozk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //上传的数据可以是任何类型，但都要转成byte[]
    }

    /**
     * 获取子节点
     *
     * @throws Exception
     */
    @Test
    public void test002_GetChildren() throws Exception {
        List<String> children = zooKeeper.getChildren(ROOT, true);
        children.forEach(System.out::println);
    }

    /**
     * 获取znode数据，初始创建时设置
     *     需要前置判断节点是否存在
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void test003_GetData() throws KeeperException, InterruptedException {

        Stat stat = zooKeeper.exists(TEST_NODE, false);
        if (null != stat) {

            byte[] data = zooKeeper.getData(TEST_NODE,false, null);

            System.out.println(new String(data));

            // 非内容字符串，而是对象内存地址
            System.out.println(String.valueOf(data));
        } else {
            nodeNotExitsOutPrint();
        }
    }

    /**
     * 获取变更后的数据
     *     需要前置判断节点是否存在
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void test004_SetData() throws KeeperException, InterruptedException {

        Stat stat = zooKeeper.exists(TEST_NODE, false);
        if (null != stat) {

            zooKeeper.setData("/idea", "hellomoon".getBytes(), -1);
            byte[] data = zooKeeper.getData("/idea", false, null);
            System.out.println(new String(data));
        } else {
            nodeNotExitsOutPrint();
        }
    }

    /**
     * 删除节点
     * 需要前置判断节点是否存在
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void test005_DeleteZnode() throws KeeperException, InterruptedException {

        Stat stat = zooKeeper.exists(TEST_NODE, false);
        if (null != stat) {
            System.out.println("node exist and then delete it.");
            //参数2：指定要删除的版本，-1表示删除所有版本
            zooKeeper.delete(TEST_NODE, -1);
        } else {
            nodeNotExitsOutPrint();
        }
    }

    /**
     * 节点不存在通用输出文案
     */
    private void nodeNotExitsOutPrint() {
        System.out.println(TEST_NODE + "节点不存在");
    }
}
