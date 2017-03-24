package ro.cc.busroute.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by cornelcondila on 12/20/2016.
 */
public class FileUtils {

    private static FileUtils theInstance = new FileUtils();

    public static FileUtils getInstance() {
        return theInstance;
    }

    private FileUtils() {
    }

    public void writeToFile(String busRouteDataFilePath, Integer routesNo, Integer routeStationsNo, Integer allStationsNo) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(busRouteDataFilePath))) {
            writer.write(routesNo.toString());
            writer.newLine();
            for (int i = 0; i < routesNo; i++) {
                writer.write(String.valueOf(i) + " ");
                new Random().ints(routeStationsNo, 0, allStationsNo).forEach(j -> {
                    try {
                        writer.write(String.valueOf(j) + " ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                if (i != routesNo - 1) {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
