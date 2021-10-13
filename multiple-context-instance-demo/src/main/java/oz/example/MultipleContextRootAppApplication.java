package oz.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.MapPropertySource;

@SpringBootApplication
@EnableConfigurationProperties(MultiContextProperties.class)
public class MultipleContextRootAppApplication {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MultipleContextRootAppApplication.class, args);
		List<ConfigurableApplicationContext> childContexts = context.getBean("childContexts", List.class);
		for (ConfigurableApplicationContext configurableApplicationContext : childContexts) {
			System.out.println(configurableApplicationContext.getBean("car"));
		}
	}

	@Bean
	public List<ConfigurableApplicationContext> childContexts(ConfigurableApplicationContext parentContext, MultiContextProperties properties) {
		List<ConfigurableApplicationContext> childContexts = new ArrayList<>();
		for (Entry<String, ContextProperties> configurationEntry : properties.getContexts().entrySet()) {
			childContexts.add(initializeChildContext(configurationEntry.getKey(), configurationEntry.getValue(), parentContext));
		}
		return childContexts;
	}

	private ConfigurableApplicationContext initializeChildContext(String configurationName, ContextProperties contextProperties,
			ConfigurableApplicationContext parentContext) {

		Map<String, Object> childContextProperties = new HashMap<>();
		this.flatten(null, contextProperties.getEnvironment(), childContextProperties);

		AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
		List<Class<?>> sourceClasses = new ArrayList<>();
		sourceClasses.addAll(Arrays.asList(contextProperties.getConfigurationClassNames()));
		childContext.register(sourceClasses.toArray(new Class[] {}));

		MapPropertySource binderPropertySource = new MapPropertySource(configurationName, childContextProperties);
		childContext.getEnvironment().getPropertySources().addFirst(binderPropertySource);
		childContext.setDisplayName(configurationName + "_context");

		childContext.getEnvironment().merge(parentContext.getEnvironment());

		childContext.setParent(parentContext);
		childContext.refresh();
		return childContext;
	}

	@SuppressWarnings("unchecked")
	private void flatten(String propertyName, Object value,
			Map<String, Object> flattenedProperties) {
		if (value instanceof Map) {
			((Map<Object, Object>) value).forEach((k, v) -> flatten(
					(propertyName != null ? propertyName + "." : "") + k, v,
					flattenedProperties));
		}
		else {
			flattenedProperties.put(propertyName, value.toString());
		}
	}
}
