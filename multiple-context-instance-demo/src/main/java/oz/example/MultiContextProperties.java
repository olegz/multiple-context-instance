package oz.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.context.demo")
public class MultiContextProperties {


	private Map<String, ContextProperties> contexts = new HashMap<>();

	public Map<String, ContextProperties> getContexts() {
		return contexts;
	}

	public void setContexts(Map<String, ContextProperties> contexts) {
		this.contexts = contexts;
	}
}
