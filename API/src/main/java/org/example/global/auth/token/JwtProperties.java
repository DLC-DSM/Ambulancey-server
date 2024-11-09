package org.example.global.auth.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    String prefix;
    String secret;
    String header;
    Long expiration_access = 1000*60*60*60*24L;
}
