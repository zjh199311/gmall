package com.atguigu.gmall.manage.service.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * @author river
 * @title: TimerPrinter
 * @projectName gmall0105
 * @description: TODO
 * @date 2020/4/3013:59
 */
public class TimerPrinter implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println("当前时间是："+ new Date());
        Toolkit.getDefaultToolkit().beep();
    }
}
