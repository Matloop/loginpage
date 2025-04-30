package com.example.loginauthapi.infra.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // Importar a interface

@Configuration
public class CorsConfig implements WebMvcConfigurer { // Implementar a interface

    @Override // Boa prática adicionar @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a configuração para todos os endpoints ("/**")
                .allowedOrigins("http://localhost:4200") // Permite requisições desta origem
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT") // Métodos HTTP permitidos
                .allowedHeaders("*"); // Permite todos os cabeçalhos na requisição (IMPORTANTE!)
        // .allowCredentials(true); // Descomente se precisar enviar cookies/auth headers
    }
}