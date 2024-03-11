package io.github.matheusfsantos.tgid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TgidTransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgidTransactionServiceApplication.class, args);
	}

}
