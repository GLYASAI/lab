package org.abewang.lab.air.service.impl;

import org.abewang.lab.air.domain.AirReservation;
import org.abewang.lab.air.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
@Service
public class BookingServiceImpl implements BookingService {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    private Set<String> pnrs = new HashSet<>();

    @Override
    public boolean saveBooking(AirReservation airReservation) {
        System.out.println("正在保存PNR为" + airReservation.getPnr() + "的Booking...");
        if (pnrs.contains(airReservation.getPnr())) {  // RocketMQ可能会有重复消息, 这里保证消费逻辑的幂等性.
            System.out.println("已存在" + airReservation.getPnr() + "这个Booking. 执行更新.");
        }
//        boolean result = System.currentTimeMillis() % 2 == 0;
        boolean result = false;
        if (result) {
//            pnrs.add(airReservation.getPnr());
            System.out.println("保存成功.");
            return true;
        } else {
            System.out.println("保存失败. 等待重试.");
            return false;
        }
    }
}
