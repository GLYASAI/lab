package org.abewang.lab.air.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
public class AirReservation {
    private String pnr;

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
}
