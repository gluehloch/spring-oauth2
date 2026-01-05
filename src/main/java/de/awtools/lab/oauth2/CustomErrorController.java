package de.awtools.lab.oauth2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CustomErrorController {

    @GetMapping("/error")
    public ResponseEntity<Map<String, String>> handleError() {
        return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred"));
    }
}
