package com.caixabank.loansmanager.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(title = "Loans Manager", version = "v1", description = "A loans manager for a caixabank technical interview", contact = @Contact(name = "Joaquin Borrego Fernandez", email = "juakylc14@gmail.com"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/2.0")))
@Configuration
@EnableCaching
public class ApplicationConfig {

}
