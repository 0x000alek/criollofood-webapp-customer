package com.criollofood.bootapp.customer.config;

import com.criollofood.bootapp.customer.util.AESEncrypter;
import com.criollofood.bootapp.customer.util.AuthenticationFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    @Bean
    public AESEncrypter aesEncrypter() {
        return new AESEncrypter();
    }

    @Bean
    public AuthenticationFacade authFacade() {
        return new AuthenticationFacade();
    }
}
