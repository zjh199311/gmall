package com.atguigu.gmall.manage.service.Timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author river
 * @title: MyTaskA
 * @projectName gmall0105
 * @description: TODO
 * @date 2020/5/312:02
 */
public class MyTaskTestCancel extends TimerTask {
    private  int i;
    public MyTaskTestCancel(int i){
        super();
        this.i = i;
    }
    @Override
    public void run() {
        System.out.println("第"+i+"次没有被cancel取消");
    }

    public static void main(String[] args) {
         int i =0;
        Calendar calendar = Calendar.getInstance();
        Date calendarTime = calendar.getTime();
        while (true){
            i++;
            Timer timer = new Timer();
            MyTaskTestCancel myTaskTestCancel = new MyTaskTestCancel(i);
            timer.schedule(myTaskTestCancel,calendarTime);
            timer.cancel();
        }
    }
}
