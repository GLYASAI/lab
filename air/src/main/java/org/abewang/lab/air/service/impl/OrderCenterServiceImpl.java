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
        System.out.println("给售后中心发送消息...");
        return true;
    }
}
