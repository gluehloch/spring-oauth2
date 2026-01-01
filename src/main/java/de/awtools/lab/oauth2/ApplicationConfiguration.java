package de.awtools.lab.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class ApplicationConfiguration {
   
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/", "/error", "/webjars/**", "/index.html",
                    "/login/oauth2/code/github").permitAll()
                .anyRequest().authenticated())
            .oauth2Client(Customizer.withDefaults())
            .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

}
