package de.solugo.scrumpoker.config;

import de.solugo.scrumpoker.util.BeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfiguration {

    @Bean
    public BeanMapper beanMapper() {
        return new BeanMapper();
    }
}
