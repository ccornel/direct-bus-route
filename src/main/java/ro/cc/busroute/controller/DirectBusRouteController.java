package ro.cc.busroute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.cc.busroute.BusRouteApplication;
import ro.cc.busroute.dto.DirectBusRoute;
import ro.cc.busroute.service.DirectBusRouteService;

/**
 * Created by cornelcondila on 12/18/2016.
 */
@RestController
@RequestMapping(path = "/direct")
public class DirectBusRouteController {

    @Autowired
    private DirectBusRouteService directBusRouteService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DirectBusRoute> checkRoute(@RequestParam("dep_sid") Integer depSid,
                                                    @RequestParam("arr_sid") Integer arrSid) {
        System.out.println("dep_sid : ".concat(depSid.toString()));
        System.out.println("arr_sid : ".concat(arrSid.toString()));

        if (!BusRouteApplication.SERVICE_UP) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }

        DirectBusRoute response = new DirectBusRoute();
        response.setArrSid(arrSid);
        response.setDepSid(depSid);
        response.setDirectBusRoute(directBusRouteService.directRoute(depSid, arrSid));

        return ResponseEntity.ok(response);
    }
}
