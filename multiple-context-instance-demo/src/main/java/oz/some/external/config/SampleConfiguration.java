package oz.some.external.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ItemsProperties.class)
public class SampleConfiguration {

	@Bean
	public Car car(ItemsProperties properties) {
		Car car = new Car();
		car.setMake((String) properties.getCar().get("make"));
		car.setModel((String) properties.getCar().get("model"));
		return car;
	}
}
