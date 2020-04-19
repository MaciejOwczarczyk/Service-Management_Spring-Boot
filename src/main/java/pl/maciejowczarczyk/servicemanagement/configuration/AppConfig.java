package pl.maciejowczarczyk.servicemanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.maciejowczarczyk.servicemanagement.role.RoleConverter;


@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "pl.maciejowczarczyk.servicemanagement")
@ComponentScan("pl.maciejowczarczyk.servicemanagement")
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login/login");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(getRoleConverter());
    }

    @Bean
    public RoleConverter getRoleConverter() {
        return new RoleConverter();
    }
}
