package ro.cc.busroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ro.cc.busroute"})
public class BusRouteApplication {

	public static Boolean SERVICE_UP = Boolean.TRUE;

	public static void main(String[] args) {
		if (args.length == 0) {
			SERVICE_UP = Boolean.FALSE;
		}

		SpringApplication.run(BusRouteApplication.class, args);
	}

}
