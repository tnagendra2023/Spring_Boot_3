package com.product.management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = getServerDetails();
        Contact contact = getContactDetails();
        Info info = getLicenceAndInfoDetails(contact);
        return new OpenAPI().info(info).servers(List.of(devServer));
    }

    private static Info getLicenceAndInfoDetails(Contact contact) {
        License mitLicense = new License().name("License").url("https://compnayname.com/licenses/");
        return new Info()
                .title("Product Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage products.")
                .termsOfService("https://www.companyname.com/terms")
                .license(mitLicense);
    }

    private static Contact getContactDetails() {
        Contact contact = new Contact();
        contact.setEmail("youremail@gmail.com");
        contact.setName("name");
        contact.setUrl("https://www.appurl.com");
        return contact;
    }

    private Server getServerDetails() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");
        return devServer;
    }
}
