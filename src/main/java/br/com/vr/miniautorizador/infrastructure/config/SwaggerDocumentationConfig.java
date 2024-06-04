package br.com.vr.miniautorizador.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocumentationConfig {

    @Bean
    OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("mini-autorizador")
                        .description("Autorizador de transações para cartões de benefícios da VR.")
                        .version("v0.0.1"));
    }
}