package suggest.emotion_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class EmotionWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmotionWebApplication.class, args);
	}

}
