package com.atguigu.gmall.manage.service.Timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * @author river
 * @title: MyTask
 * @projectName gmall0105
 * @description: TODO
 * @date 2020/4/3016:22
 */
public class MyTask extends TimerTask {
    @Override
    public void run() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            System.out.println("任务开始时间:" + dateFormat.format(new Date()));
            Thread.sleep(5000);
            System.out.println("任务结束时间:" + dateFormat.format(new Date()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
