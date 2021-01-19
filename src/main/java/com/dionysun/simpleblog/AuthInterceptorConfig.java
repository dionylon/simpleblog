package com.dionysun.simpleblog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthenticationInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public JwtAuthenticationInterceptor jwtAuthenticationInterceptor(){
        return new JwtAuthenticationInterceptor();
    }
}
