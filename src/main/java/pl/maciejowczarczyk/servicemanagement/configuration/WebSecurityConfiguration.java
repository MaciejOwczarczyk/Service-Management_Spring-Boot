package pl.maciejowczarczyk.servicemanagement.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.maciejowczarczyk.servicemanagement.user.SpringDataUserDetailsService;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "pl.maciejowczarczyk.servicemanagement")
@ComponentScan("pl.maciejowczarczyk.servicemanagement")
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final DataSource dataSource;

    //    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}user").roles("USER")
//                .and()
//                .withUser("admin").password("{noop}admin").roles("ADMIN");
//    }


//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(bCryptPasswordEncoder());
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enabled"
//                        + " from users where username=?")
//                .authoritiesByUsernameQuery("select username, authority "
//                        + "from authorities where username=?")
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .csrf().disable()
                .logout().logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers( "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/assets/**", "/fonts/**", "/dis/**", "/vendor1/**", "/Front/**", "/vendor/**");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringDataUserDetailsService customerUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

}
