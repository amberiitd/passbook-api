package com.namber.passbookapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(
                        Arrays.asList(
                                new SecurityContextBuilder().securityReferences(
                                        Arrays.asList(new SecurityReference("BasicAuth", getOAuthScopes()))
                                ).build()
                        )
                )
                .securitySchemes(getAuthSchemes())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private AuthorizationScope[] getOAuthScopes() {
        return new AuthorizationScope[]{new AuthorizationScope("read", "fetch song data")};
    }

    private List<SecurityScheme> getAuthSchemes() {
        return Arrays.asList(new BasicAuth("BasicAuth"));
    }
}
