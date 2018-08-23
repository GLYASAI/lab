package org.abewang.lab.air.service.impl;

import org.abewang.lab.air.domain.AirReservation;
import org.abewang.lab.air.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
@Service
public class BookingServiceImpl implements BookingService {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Override
    public boolean saveBooking(AirReservation airReservation) {
        System.out.println("保存PNR为" + airReservation.getPnr() + "的Booking...");
        // TODO, 失败的情况
        return true;
    }
}
