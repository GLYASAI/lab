package org.abewang.lab.air.service.impl;

import org.abewang.lab.air.service.OrderCenterService;
import org.springframework.stereotype.Service;

/**
 * @Author Abe
 * @Date 2018/8/23.
 */
@Service
public class OrderCenterServiceImpl implements OrderCenterService {
    @Override
    public boolean sendBookingMsg(String msg) {
        System.out.println("正在给售后中心发送消息...");
        if (System.currentTimeMillis() % 2 == 0) {
            System.out.println("发送成功.");
            return true;
        } else {
            System.out.println("发送失败. 等待重试.");
            return false;
        }
    }
}
