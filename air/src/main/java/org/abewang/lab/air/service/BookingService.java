package org.abewang.lab.air.service;

import org.abewang.lab.air.domain.AirReservation;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
public interface BookingService {
    boolean saveBooking(AirReservation airReservation);
}
