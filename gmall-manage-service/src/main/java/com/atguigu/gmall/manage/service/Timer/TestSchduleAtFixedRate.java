package com.atguigu.gmall.manage.service.Timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author river
 * @title: TestSchduleAtFixedRate
 * @projectName gmall0105
 * @description: TODO
 * @date 2020/5/313:35
 */
public class TestSchduleAtFixedRate extends TimerTask{
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

    public static void main(String[] args) {
        TestSchduleAtFixedRate testSchduleAtFixedRate = new TestSchduleAtFixedRate();
        Calendar calendar = Calendar.getInstance();
        Date calendarTime = calendar.getTime();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(testSchduleAtFixedRate,calendarTime,3000);
    }


}
