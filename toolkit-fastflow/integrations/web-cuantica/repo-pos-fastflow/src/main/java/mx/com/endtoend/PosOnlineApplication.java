package mx.com.endtoend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(scanBasePackages = { "mx.com.endtoend" })
@EnableScheduling
@EnableAsync
@EnableEurekaClient
@EntityScan(basePackages = { "mx.com.endtoend" })
public class PosOnlineApplication extends SpringBootServletInitializer  {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PosOnlineApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(PosOnlineApplication.class, args);
	}

	@Scheduled(cron = "0 0/5 * * * * ")
	private void cleanMemory() {
		log.info("CALL CLEAN JAVA HEAP MEMORY");
		System.gc();
	}

}
