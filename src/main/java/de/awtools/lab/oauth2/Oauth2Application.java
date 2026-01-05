package de.awtools.lab.oauth2;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan("de.awtools.lab.oauth2")
public class Oauth2Application {

    @CrossOrigin
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(500).body(Map.of("error", "The principal is null. User not authenticated."));
        }
        return ResponseEntity.ok(Collections.singletonMap("name", principal.getAttribute("name")));
    }

	public static void main(String[] args) {
		SpringApplication.run(Oauth2Application.class, args);
	}

}
