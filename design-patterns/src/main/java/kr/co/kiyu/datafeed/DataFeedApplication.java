package kr.co.kiyu.datafeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // DB 커넥션 비활성화
public class DataFeedApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataFeedApplication.class,  args);
	}

}