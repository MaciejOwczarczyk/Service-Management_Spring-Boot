package pl.maciejowczarczyk.servicemanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.maciejowczarczyk.servicemanagement.role.RoleConverter;
import pl.maciejowczarczyk.servicemanagement.user.UserConverter;
import pl.maciejowczarczyk.servicemanagement.user.UserRepository;
import pl.maciejowczarczyk.servicemanagement.user.UserService;


@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "pl.maciejowczarczyk.servicemanagement")
@ComponentScan("pl.maciejowczarczyk.servicemanagement")
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login/login");
        registry.addViewController("/403").setViewName("403");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(getRoleConverter());
        registry.addConverter(getUserConverter());
    }

    @Bean
    public RoleConverter getRoleConverter() {
        return new RoleConverter();
    }

    @Bean
    public UserConverter getUserConverter() {
        return new UserConverter();
    }

//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(10485760); // 10MB
//        multipartResolver.setMaxUploadSizePerFile(5242880); // 5MB
//        return multipartResolver;
//    }

}
