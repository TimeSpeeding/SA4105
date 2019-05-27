package com.t13.dva.LAPS.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.t13.dva.LAPS.util.LoginSuccessHandle;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;
    
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                	.antMatchers("/", "/login", "/registration").permitAll()
                	.antMatchers("/admin/**").hasRole("ADMIN")
                	.antMatchers("/employee/**").hasRole("EMPLOYEE")
                	.antMatchers("/manager/**").hasRole("MANAGER")
                	.anyRequest().authenticated()
                	.and()
                .formLogin()
                	.loginPage("/login")
                	.failureUrl("/login?error=true")
                	.successHandler(new LoginSuccessHandle())
                    .usernameParameter("username")
                    .passwordParameter("password")
                	.and()
                .logout()
                .logoutSuccessUrl("/login")
                	.permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
    
}