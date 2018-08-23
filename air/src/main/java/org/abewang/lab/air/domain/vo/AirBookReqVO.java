package org.abewang.lab.air.domain.vo;

import org.abewang.lab.air.domain.Passenger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
public class AirBookReqVO {
    private List<Passenger> passengers = new ArrayList<>();

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
}
