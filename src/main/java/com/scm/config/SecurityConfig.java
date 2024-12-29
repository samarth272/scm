package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    //user create & login using java code in memory service
    // @Bean
    // public UserDetailsService userDetailsService(){

    //        UserDetails user1=User.withDefaultPasswordEncoder().username("admin123").password("admin123").roles("ADMIN","USER").build();

    //        UserDetails user2= User.withDefaultPasswordEncoder().username("user123").password("password").build();

    //      var inMemoryUserDetailsManager=new InMemoryUserDetailsManager(user1,user2);
    //      return inMemoryUserDetailsManager;
    //     }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
       
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
         daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;

    } 

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity)throws Exception{

       //configure authentication provider
       //routers public to private
       
       httpSecurity.authorizeHttpRequests(authorize->{
        //  authorize.requestMatchers("/home","/signup").permitAll();
    
        authorize.requestMatchers("/user/**").authenticated();
        authorize.anyRequest().permitAll();
      
    });
    
    //form default login
    httpSecurity.formLogin(formLogin->{

        //
        formLogin.loginPage("/login");
        formLogin.loginProcessingUrl("/authenticate");
        formLogin.successForwardUrl("/user/dashboard");
        formLogin.failureForwardUrl("/login?error=true");
 
        //formLogin.defaultSuccessUrl("/home");
        formLogin.usernameParameter("email");
        formLogin.passwordParameter("password");
       
      //  formLogin.failureHandler(new AuthenticationFailureHandler(){

        //     @Override
        //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        //             AuthenticationException exception) throws IOException, ServletException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
        //     }
            

        // });

        // formLogin.successHandler(new AuthenticationSuccessHandler() {

        //     @Override
        //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        //             Authentication authentication) throws IOException, ServletException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
        //     }
            
        // });


     });

     //main imp
     httpSecurity.csrf(AbstractHttpConfigurer::disable);
     httpSecurity.logout(logoutForm->{
  
        logoutForm.logoutUrl("/logout");
        logoutForm.logoutSuccessUrl("/login?logout=true");
     });

       return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
