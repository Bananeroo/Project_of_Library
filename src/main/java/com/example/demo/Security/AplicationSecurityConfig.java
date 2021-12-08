package com.example.demo.Security;

import com.example.demo.Auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import static com.example.demo.Security.ApplicationUserRole.ADMIN;
import static com.example.demo.Security.ApplicationUserRole.USER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public AplicationSecurityConfig(PasswordEncoder passwordEncoder,
                                    ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/management/**").permitAll()
                .antMatchers("/book/**").hasAnyRole(ADMIN.name(),USER.name())
                .antMatchers("/add_file").hasRole(ADMIN.name())
                .antMatchers("/addbook").hasRole(ADMIN.name())
                .antMatchers("/orders").hasRole(ADMIN.name())
                .antMatchers("/returned").hasRole(ADMIN.name())
                .antMatchers("/deleted").hasRole(ADMIN.name())
                .antMatchers("/update").hasRole(ADMIN.name())
                .antMatchers("/delete").hasRole(ADMIN.name())

//                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ADMIN_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(ADMIN_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(ADMIN_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/view/1",true)
                .and()
                .rememberMe()
                .key("uniqueAndSecret")
                .userDetailsService(applicationUserService);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }


//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//         UserDetails kamilUser = User.builder()
//                .username("Kamil")
//                .password(passwordEncoder.encode("pass"))
////                .roles(USER.name())
//                 .authorities(USER.getGrantedAuthority())
//                .build();
//        UserDetails zbigniewUser =User.builder()
//                .username("Zbigniew")
//                .password(passwordEncoder.encode("nowe"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthority())
//                .build();
//        UserDetails tomUser =User.builder()
//                .username("Zbigniewe")
//                .password(passwordEncoder.encode("nowe"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthority())
//                .build();
//
//
//        return new InMemoryUserDetailsManager(
//                kamilUser,
//                 zbigniewUser
//        );
//    }
}
