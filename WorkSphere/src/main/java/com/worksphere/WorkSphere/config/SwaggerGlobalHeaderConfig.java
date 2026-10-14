package com.worksphere.WorkSphere.config;

import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerGlobalHeaderConfig {

    @Bean
    public OpenApiCustomizer customHeaderOpenApiCustomizer() {

        return openApi -> openApi.getPaths()
                .values()
                .forEach(pathItem -> pathItem.readOperations()
                        .forEach(operation -> {

                            Parameter tenantHeader = new Parameter()

                                    .in("header")

                                    .name("X-Tenant-ID")

                                    .required(true)

                                    .description("Tenant Identifier")

                                    .example("dc");

                            operation.addParametersItem(tenantHeader);
                        }));
    }
}