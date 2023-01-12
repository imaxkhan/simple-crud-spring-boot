package com.imax.stock.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapperConf = new ModelMapper();
        modelMapperConf.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapperConf;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }

}
