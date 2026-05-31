package com.moonzhou.springbooteasyexcel.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.moonzhou.springbooteasyexcel.entity.User;

import java.util.List;

public class UserDataListener implements ReadListener<User> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private List<User> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    
    private final List<User> userList;

    public UserDataListener(List<User> userList) {
        this.userList = userList;
    }

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(User user, AnalysisContext context) {
        System.out.println("解析到一条数据:" + user);
        cachedDataList.add(user);
        userList.add(user);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        System.out.println("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        System.out.println(cachedDataList.size() + "条数据，开始存储数据库！");
        // 这里可以调用存储数据库的方法
        System.out.println("存储数据库成功！");
    }
}