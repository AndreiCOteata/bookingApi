package com.example.application.config.swagger;

import com.example.application.common.dtos.ErrorResponse;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
@EnableSwagger2
public class SwaggerConfig{

    private final TypeResolver typeResolver = new TypeResolver();

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket api() {
        List<ResponseMessage> globalResponseMessages = buildGlobalResponseMessages();
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select().apis(RequestHandlerSelectors.basePackage("com.example.application"))
                .paths(PathSelectors.any()).build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,globalResponseMessages)
                .globalResponseMessage(RequestMethod.POST,globalResponseMessages)
                .globalResponseMessage(RequestMethod.PUT,globalResponseMessages)
                .globalResponseMessage(RequestMethod.HEAD,globalResponseMessages)
                .additionalModels(typeResolver.resolve(ErrorResponse.class)).apiInfo(apiInfo());
    }

    private List<ResponseMessage> buildGlobalResponseMessages() {
        ModelRef errorResponseModel = new ModelRef("ErrorResponse");
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(METHOD_NOT_ALLOWED.value())
                        .responseModel(errorResponseModel)
                        .message("Method not allowed").build(),
                new ResponseMessageBuilder()
                        .code(INTERNAL_SERVER_ERROR.value())
                        .responseModel(errorResponseModel)
                        .message("Internal Server Error").build(),
                new ResponseMessageBuilder()
                        .code(SERVICE_UNAVAILABLE.value())
                        .responseModel(errorResponseModel)
                        .message("Service unavailable").build(),
                new ResponseMessageBuilder()
                        .code(UNAUTHORIZED.value())
                        .responseModel(errorResponseModel)
                        .message("Unauthorized").build(),
                new ResponseMessageBuilder()
                        .code(FORBIDDEN.value())
                        .responseModel(errorResponseModel)
                        .message("Forbidden").build()
        );
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Booking App")
                .description("The webportal component serves as the central booking backend")
                .contact(new Contact("Booking", "www.i-don't-have-a-domain.net", "sdaapp.email.sender@gmail.com"))
                .version("1.0")
                .build();
    }
}