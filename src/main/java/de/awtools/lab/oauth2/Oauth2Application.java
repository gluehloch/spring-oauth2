package de.awtools.lab.oauth2;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController()
@ComponentScan("de.awtools.lab.oauth2")
public class Oauth2Application {

    @GetMapping("/api/user")
    public ResponseEntity<Map<String, Object>> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) { // This should NEVER happen because the endpoint is protected.
            return ResponseEntity.status(500).body(Map.of("error", "The principal is null. User not authenticated."));
        }
         return ResponseEntity.ok(Map.of("name", principal.getAttribute("name"), "email", principal.getAttribute("email")));
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

	public static void main(String[] args) {
		SpringApplication.run(Oauth2Application.class, args);
	}

}
