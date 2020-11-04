import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.Arrays;

public class Tes {
    @Test
    public void test1() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3,10);
        CuratorFramework client =  CuratorFrameworkFactory.newClient("127.0.0.1:2181",3000,1000,retryPolicy);
        client.start();
        TreeCache sss = new TreeCache(client, "/vx");
        sss.start();
        System.out.println(sss.getCurrentData("/vx"));
        sss.getListenable().addListener(new TreeCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent event) throws Exception {
                if(event.getType() == TreeCacheEvent.Type.NODE_ADDED){
                    System.out.println(event.getData().getPath() + "节点添加"+new String(event.getData().getData()));
                }else if (event.getType() == TreeCacheEvent.Type.NODE_REMOVED){
                    System.out.println(event.getData().getPath() + "节点移除"+new String(event.getData().getData()));
                }else if(event.getType() == TreeCacheEvent.Type.NODE_UPDATED){
                    System.out.println(event.getData().getPath() + "节点修改"+new String(event.getData().getData()));
                }else if(event.getType() == TreeCacheEvent.Type.INITIALIZED){
                    System.out.println("初始化完成2121");
                }else if(event.getType() ==TreeCacheEvent.Type.CONNECTION_SUSPENDED){
                    System.out.println("连接过时121");
                }else if(event.getType() ==TreeCacheEvent.Type.CONNECTION_RECONNECTED){
                    System.out.println("重新连接");
                }else if(event.getType() ==TreeCacheEvent.Type.CONNECTION_LOST){
                    System.out.println("连接过时一段时间2212122");
                }
            }
        });
        System.in.read();

    }
}
