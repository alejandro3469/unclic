//package mx.com.endtoend.infrastructure.configurations;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//
//
//@Configuration
//@EnableWebMvc
//public class SwaggerConfig{
//
//	@Bean
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .securityContexts(Arrays.asList(securityContext()))
//                .securitySchemes(Arrays.asList(apiKey()))
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .produces(Set.of(MediaType.APPLICATION_JSON_VALUE))
//                .consumes(Set.of(MediaType.APPLICATION_JSON_VALUE));  
//    }
//
//    private ApiKey apiKey(){
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//
//    private SecurityContext securityContext(){
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//
//    private List<SecurityReference> defaultAuth(){
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//    }
//
//    private ApiInfo apiInfo(){
//        return new ApiInfo(
//        		 "API POS",
//                 "API POS",
//                 "v1.0",
//                 "TERMS OF SERVICE URL",
//                 new Contact("NAME", "URL", "EMAIL"),
//                 "LICENSE",
//                 "LICENSE URL",
//                 Collections.emptyList()
//        );
//    }
//    
//}
