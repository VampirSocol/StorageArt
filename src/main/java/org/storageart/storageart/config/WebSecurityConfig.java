package org.storageart.storageart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.storageart.storageart.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

//    @Override
//    public void configure(HttpSecurity http) throws Exception{
////        http
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and()
////                .csrf().disable()
////                .httpBasic().disable()
////                .formLogin().disable()
////                .authorizeRequests()
////                //.antMatchers("/registration").permitAll()
//////                .antMatchers(HttpMethod.PUT, "/books/*").hasAnyAuthority()
//////                .antMatchers(HttpMethod.DELETE, "/books/*").hasAnyAuthority()
////                .anyRequest().permitAll();
//        http
//                .authorizeHttpRequests(requests -> requests
//                        //.antMatchers(HttpMethod.POST, "/home", "/registration", "/js/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout(logout -> logout.permitAll());
//
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/users/registration")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        //        .authenticationEntryPoint(new BasicAuthenticationEntryPoint());
        //http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
        //return http.build();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("static/**");
    }

}
