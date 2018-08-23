package org.abewang.lab.air.service.impl;

import org.abewang.lab.air.service.OrderCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author Abe
 * @Date 2018/8/23.
 */
@Service
public class OrderCenterServiceImpl implements OrderCenterService {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderCenterServiceImpl.class);

    @Override
    public boolean sendBookingMsg(String msg) {
        LOGGER.debug("正在给售后中心发送消息...");
        if (System.currentTimeMillis() % 2 == 0) {
            LOGGER.debug("发送成功.");
            return true;
        } else {
            LOGGER.debug("发送失败. 等待重试.");
            return false;
        }
    }
}
