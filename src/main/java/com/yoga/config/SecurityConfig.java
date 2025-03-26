package com.yoga.config;

import com.yoga.filter.JWTAuthenticationFilter;
import com.yoga.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


      private MyUserDetailsService myUserDetailsService;

      private JWTAuthenticationFilter jwtAuthenticationFilter;


      @Autowired
      public SecurityConfig(MyUserDetailsService myUserDetailsService,JWTAuthenticationFilter jwtAuthenticationFilter){
          this.myUserDetailsService = myUserDetailsService;
          this.jwtAuthenticationFilter = jwtAuthenticationFilter;
      }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

         return httpSecurity
                 .cors(cors-> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                 .csrf(csrf->csrf.disable())
                 .authorizeHttpRequests(request-> request
                         .requestMatchers("/auth/**","/attendance/**").permitAll()
                         .requestMatchers("/admin/**","/revenue/**").hasRole("ADMIN")
                         .requestMatchers("/batch/**").hasAnyRole("ADMIN","EMPLOYEE")
                         .requestMatchers("/student/**").hasAnyRole("ADMIN","EMPLOYEE")
                         .requestMatchers("/branch/**").hasRole("ADMIN")
                         .requestMatchers("/fee/**").hasAnyRole("ADMIN","EMPLOYEE")
                         .requestMatchers("/employee").hasRole("EMPLOYEE")
                         .requestMatchers("/branches/**").hasAnyRole("ADMIN","EMPLOYEE")
                         .requestMatchers("/lead/**").hasAnyRole("ADMIN","EMPLOYEE")
                         .requestMatchers("/receipt/**").hasAnyRole("ADMIN","EMPLOYEE")
                         .anyRequest().authenticated())
                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                 .httpBasic(Customizer.withDefaults())
                 .sessionManagement(session-> session
                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        return daoAuthenticationProvider;
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//          return config.getAuthenticationManager();
//    }

    @Bean
    public AuthenticationManager authenticationManager(){
       return new ProviderManager(Collections.singletonList(daoAuthenticationProvider()));
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:3001")); // Frontend URL
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3002"));  // Allow specific origins or use "*"
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Allowed HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "x-auth-token", "Accept"));  // Allowed headers
        configuration.setExposedHeaders(Arrays.asList("Authorization", "x-auth-token"));  // Expose headers to the client
        configuration.setAllowCredentials(true);  // Allow credentials (cookies, authorization headers, etc.)
        configuration.setMaxAge(3600L);  // Cache preflight response for 1 hour
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Apply CORS configuration to all endpoints
        return source;
    }


}
