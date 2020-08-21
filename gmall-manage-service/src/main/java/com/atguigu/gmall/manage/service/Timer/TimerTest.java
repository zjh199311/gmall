package com.atguigu.gmall.manage.service.Timer;

import com.atguigu.gmall.manage.service.Timer.MyTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * @author river
 * @title: TimerTest
 * @projectName gmall0105
 * @description: TODO
 * @date 2020/4/3014:01
 */
public class TimerTest {

    public static void main(String[] args) {
        /*ActionListener listener = new TimerPrinter();
        Timer timer = new Timer(3000, listener);
        timer.start();
        JOptionPane.showConfirmDialog(null,"停止");
        System.exit(0);*/

        System.out.println("系统当前时间为:"+ new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,10);
        Date runCalendarTime = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = dateFormat.format(runCalendarTime);
        System.out.println("定时任务执行的时间为:"+format);
        MyTask myTask = new MyTask();
        Timer timer = new Timer();
        timer.schedule(myTask,runCalendarTime,3000);
    }
}
