package az.unibank.msauth.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI myOpenAPI() {
        var contact = new Contact();
        contact.setEmail("info@unibank.az");
        contact.setName("Unibank");
        contact.setUrl("https://www.unibank.az");

        var mitLicense = new License().name("MIT License").url("https://unibank.az/licenses/mit/");

        var info = new Info()
                .title(applicationName + " API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage " + applicationName)
                .termsOfService("https://www.unibank.az/terms")
                .license(mitLicense);

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(info);
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
