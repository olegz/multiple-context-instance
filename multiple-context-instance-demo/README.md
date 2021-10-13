## Simulation of multiple instance of child context configuration

This sample project simulates creation of multiple instances of Spring configuration. While greatly simplified, it is using similar approach 
we use in Spring Cloud Stream when we need to create multiple instances of the same configuration. 

While in the real world child context configuration provided in a separate JAR (included as a dependency), in this example, 
the child configuration (application) is represented by `oz.some.external.config` package.

The `application.yaml` provides configuration based on which root application can determine how many instances of child context it needs.

```
spring:
  context:
    demo:
      contexts:
        contextA:
          configuration-class-names:
            oz.some.external.config.SampleConfiguration
          environment:
            items:
              car:
                make: BMW
                model: 328i
        contextB:
          configuration-class-names:
            oz.some.external.config.SampleConfiguration
          environment:
            items:
              car:
                make: Tesla
                model: S3
```
The `MultiContextProperties` class represents `spring.clontext.demo` which as you can see will contain a map of `contexts`  properties 
represented by `ContextProperties`. The `MultipleContextRootAppApplication` creates child context for each element in the `contexts` printing the 
example instance of a Car object 


Obviously if it was only about a `Car` object, we would not have to go through the same ceremony. But in real life child context configuration usually contains many beans and also depends on other auto-configurations. For example, in our case we can have multiple instances of Rabbit binder (one for input and one for output). Each instance connects to the different instance of the actual Rabbit brokers running on different hosts/ports etc.
 


