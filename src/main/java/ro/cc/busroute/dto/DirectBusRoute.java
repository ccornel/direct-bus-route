package ro.cc.busroute.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by cornelcondila on 12/18/2016.
 */
public class DirectBusRoute implements Serializable {

    @JsonProperty("dep_sid")
    private Integer depSid;

    @JsonProperty("arr_sid")
    private Integer arrSid;

    @JsonProperty("direct_bus_route")
    private Boolean directBusRoute;

    public Integer getDepSid() {
        return depSid;
    }

    public void setDepSid(Integer depSid) {
        this.depSid = depSid;
    }

    public Integer getArrSid() {
        return arrSid;
    }

    public void setArrSid(Integer arrSid) {
        this.arrSid = arrSid;
    }

    public Boolean getDirectBusRoute() {
        return directBusRoute;
    }

    public void setDirectBusRoute(Boolean directBusRoute) {
        this.directBusRoute = directBusRoute;
    }
}
