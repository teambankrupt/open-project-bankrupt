package com.example.coreweb.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String securitySchemaOAuth2 = "oauth2schema";
    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc = "accessEverything";

    @Value("${auth.client.id}")
    private String clientId;
    @Value("${auth.client.secret}")
    private String clientSecret;

    @Value("${applicationName}")
    private String applicationName;

    @Value("${baseUrl}")
    private String appUrl;

    @Value("${contactEmail}")
    private String contactEmail;

    @Value("${host.full.dns.auth.link}")
    private String authLink;

    @Bean
    public Docket api() {

        List<ResponseMessage> responseMessageList = new java.util.ArrayList<>();

        responseMessageList.add(new ResponseMessageBuilder().code(200).message("Successfully retrieved list").build());
//                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(401).message("You are not authorized to view the resource").build());
//                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(403).message("Accessing the resource you were trying to reach is forbidden").build());
//                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("The resource you were trying to reach is not found").build());
//                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(406).message("The resource you were trying to reach is not acceptable").build());
//                .responseModel(new ModelRef("string")).build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("Internal server error").build());
//                .responseModel(new ModelRef("string")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext()))
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList);
    }


    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = newArrayList();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = newArrayList();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authLink + "/oauth/token?client_id=" + this.clientId + "&client_secret=" + this.clientSecret);

        grantTypes.add(creGrant);

        return new OAuth(securitySchemaOAuth2, authorizationScopeList, grantTypes);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/api/**"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference(securitySchemaOAuth2, authorizationScopes));
    }

    /*@Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(clientId, clientSecret, "", "", "", ApiKeyVehicle.HEADER, "", " ");
    }*/

    @Bean
    public SecurityConfiguration securityInfo() {
        return SecurityConfigurationBuilder.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scopeSeparator(" ")
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(this.applicationName + " Api").description("")
                .termsOfServiceUrl(this.appUrl + "/terms")
                .contact(new Contact(this.applicationName + " Admin", this.appUrl, this.contactEmail))
                .license("MIT").licenseUrl(this.appUrl + "/license").version("1.0.0").build();
    }
}