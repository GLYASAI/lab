package org.abewang.lab.air.service;

import org.abewang.lab.air.domain.AirReservation;
import org.abewang.lab.air.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
public class BookingServiceTest {
    @Test
    void testSaveBooking() {
        BookingService bookingService = new BookingServiceImpl();
        AirReservation airReservation = new AirReservation();
        airReservation.setPnr("AAAAAA");
        assertTrue(bookingService.saveBooking(airReservation));
    }
}
