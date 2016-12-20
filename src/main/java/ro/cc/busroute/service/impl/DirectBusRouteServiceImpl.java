package ro.cc.busroute.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ro.cc.busroute.BusRouteApplication;
import ro.cc.busroute.service.DirectBusRouteService;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by cornelcondila on 12/18/2016.
 */
@Service
public class DirectBusRouteServiceImpl implements DirectBusRouteService {

    @Autowired
    private Environment env;

    private Integer routesCount;
    private Map<Integer, List<Integer>> routes = null;
    private Integer counter = 0;


    @PostConstruct
    public void init() {
        String[] nonOptionArgs = env.getProperty(CommandLinePropertySource.DEFAULT_NON_OPTION_ARGS_PROPERTY_NAME,
                String[].class);

        if (nonOptionArgs == null || nonOptionArgs.length == 0) {
            throw new RuntimeException("Non option args not found!");
        }

        String busRouteDataFilePath = nonOptionArgs[0];
//        FileUtils.writeToFile(busRouteDataFilePath);

        try (Stream<String> stream = Files.lines(Paths.get(busRouteDataFilePath))) {
            routes = stream.map(line ->
                                    Arrays.stream(line.contains(" ") ?
                                                        StringUtils.tokenizeToStringArray(line, " ") :
                                                        new String[] { line })
                                            .map(Integer::parseInt)
                                            .collect(toList()))
                          .filter(tokens -> validateInputLine(tokens))
                          .collect(Collectors.toConcurrentMap(tokens -> tokens.get(0),
                                                    tokens -> tokens.subList(1, tokens.size())));

            if (!routesCount.equals(routes.size())) {
                throw new RuntimeException("The specified number of routes is different than the actual number of defined routes!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            BusRouteApplication.SERVICE_UP = Boolean.FALSE;
        }

        System.out.println(busRouteDataFilePath);
    }

    @Override
    public Boolean directRoute(Integer depSid, Integer arrSid) {
        return directRouteObviousAlgorithm(depSid, arrSid);
    }

    private Boolean directRouteObviousAlgorithm(Integer depSid, Integer arrSid) {
        for (List<Integer> route : routes.values()) {
            int depSidIndex = route.indexOf(depSid);
            if (depSidIndex != -1) {
                int arrSidIndex = route.indexOf(arrSid);
                if (arrSidIndex > depSidIndex) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean validateInputLine(List<Integer> lineTokens) {
        if (lineTokens.size() == 1) {
            if (routesCount != null) {
                throw new RuntimeException("Each route must have at least two station ids! Route not valid!");
            }

            if (lineTokens.get(0) > 100000) {
                throw new RuntimeException("More than 100000 routes! Routes limit exceeded!");
            }

            routesCount = lineTokens.get(0);
            return false;
        }

        if (lineTokens.size() > 1001) {
            throw new RuntimeException("More than 1000 stations in a route! Route stations limit exceeded!");
        }

        if (lineTokens.stream().distinct().count() != lineTokens.size()) {
            throw new RuntimeException("Duplicate station ids found in the same route!");
        }

        synchronized (counter) {
            counter++;
            if (counter % 1000 == 0) {
                System.out.println("Counter: " + counter);
            }
        }

        return true;
    }

}
