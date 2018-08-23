package org.abewang.lab.air;

import org.abewang.lab.air.domain.vo.AirBookReqVO;
import org.abewang.lab.air.domain.Passenger;
import org.abewang.lab.air.util.JsonUtil;
import org.junit.jupiter.api.Test;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
public class AirServiceTest {
    @Test
    void testAirBook() {
        AirBookReqVO airBookReqVo = new AirBookReqVO();
        Passenger passenger = new Passenger();
        passenger.setName("abewang");
        airBookReqVo.getPassengers().add(passenger);
        System.out.println(JsonUtil.toJson(airBookReqVo));
    }
}
