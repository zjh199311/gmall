package com.atguigu.gmall.manage.service.Timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author river
 * @title: ScheduleTimerTest
 * @projectName gmall0105
 * @description: TODO
 * @date 2020/5/312:42
 */
public class ScheduleTimerTest extends TimerTask {
    @Override
    public void run() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            System.out.println("任务开始时间:" + dateFormat.format(new Date()));;
            Thread.sleep(2000);
            System.out.println("任务结束时间:"+ dateFormat.format(new Date()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //3000代表在当前时间基础上延迟3s后在执行任务
    public static void main(String[] args) {
        ScheduleTimerTest scheduleTimerTest = new ScheduleTimerTest();
        Calendar calendar = Calendar.getInstance();
        Date calendarTime = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(scheduleTimerTest,3000,4000);
    }
}
