package com.atguigu.gmall.threadpool;

import com.alibaba.dubbo.common.threadpool.support.fixed.FixedThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author river
 * @title: MyTask
 * @projectName gmall0105-2020
 * @description: TODO
 * @date 2020/6/1617:16
 */
public class MyTask implements Runnable {

    private  int taskNum;

    public  MyTask(int num){
        this.taskNum = num;
    }

    @Override
    public void run() {
        System.out.println("正在执行task"+ taskNum);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task"+ taskNum + "执行完毕");
    }


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,
                200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
        for (int i = 0;i<15;i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                    executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }
}
