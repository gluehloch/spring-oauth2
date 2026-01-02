package de.awtools.lab.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@Configuration
public class ApplicationConfiguration {
   
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/", "/error", "/script/**", "/index.html").permitAll()
                .anyRequest().authenticated())
                /* 
                .logout(l ->
                    l.invalidateHttpSession(false)
                    .logoutSuccessUrl("/").permitAll())
                    */
                    /*
logout.deleteCookies("remove")
.invalidateHttpSession(false)
.logoutUrl("/custom-logout")
.logoutSuccessUrl("/logout-success")
                    */
            .oauth2Client(Customizer.withDefaults())
            .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

}
