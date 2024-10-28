package suggest.taste_the_weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TasteTheWeatherWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasteTheWeatherWebApplication.class, args);
	}

}
