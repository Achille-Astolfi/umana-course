package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "component")
public class UmanaBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(UmanaBootApplication.class, args);
	}
}
