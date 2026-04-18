package de.awtools.lab.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@EnableWebSecurity
@Configuration
public class ApplicationConfiguration {
   
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.ALL));

        http.requestCache(cache -> cache.requestCache(requestCache))
            .csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers( "/error", "/favicon.ico", "/css/**", "/script/**", "/index.html").permitAll()
                .anyRequest().authenticated())
            .logout(l -> l
            	.logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("X-XSRF-TOKEN")
                .invalidateHttpSession(false).addLogoutHandler(clearSiteData).permitAll())
				.oauth2Client(Customizer.withDefaults()).oauth2Login(login -> login.defaultSuccessUrl("/", true))
				.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
		http.addFilterAfter(new CsrfCookieFilter(), CsrfFilter.class);
		return http.build();
	}

}
