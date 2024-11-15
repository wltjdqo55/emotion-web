package suggest.taste_the_weather;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TasteTheWeatherWebApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "local");
		SpringApplication.run(TasteTheWeatherWebApplication.class, args);
	}

}
