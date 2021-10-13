package oz.some.external.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "items")
public class ItemsProperties {

	private Map<String, Object> car = new HashMap<>();

	public Map<String, Object> getCar() {
		return car;
	}

	public void setCar(Map<String, Object> car) {
		this.car = car;
	}


}
