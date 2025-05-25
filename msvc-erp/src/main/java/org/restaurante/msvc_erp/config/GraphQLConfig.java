package org.restaurante.msvc_erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> {

            wiringBuilder.type("ErpQuery", typeConfig -> {
                return typeConfig;
            });


            wiringBuilder.type("ErpMutation", typeConfig -> {
                return typeConfig;
            });
        };
    }


}
