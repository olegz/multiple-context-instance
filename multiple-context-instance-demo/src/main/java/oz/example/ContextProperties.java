package oz.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class ContextProperties {

	private Map<String, Object> environment = new HashMap<>();

	private Class<?>[] configurationClassNames;

	public Map<String, Object> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<String, Object> environment) {
		this.environment = environment;
	}

	public Class<?>[] getConfigurationClassNames() {
		return configurationClassNames;
	}

	public void setConfigurationClassNames(Class<?>[] configurationClassNames) {
		this.configurationClassNames = configurationClassNames;
	}
}
