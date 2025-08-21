package com.apigateway.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Autowired
    private RouterValidator validator;

    private final SecretKey secretKey;

    public JwtAuthFilter(@Value("${jwt.secret}") String secret) {
        super(Config.class);
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {

                // 1. Check for Authorization header
                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    log.error("❌ Missing or invalid Authorization header for path: {}", exchange.getRequest().getURI().getPath());
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String token = authHeader.substring(7);
                log.info("➡️ token: {}", token);

                // 2. Validate token
                Claims claims;
                try {
                    claims = Jwts.parserBuilder()
                            .setSigningKey(secretKey)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
                    log.info("✅ Claims extracted: {}", claims);

                } catch (Exception e) {
                    log.error("❌ Invalid JWT token for path: {}. Error: {}", exchange.getRequest().getURI().getPath(), e.getMessage());
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                // 3. Check roles for admin paths
                String path = exchange.getRequest().getURI().getPath();
                log.info("➡️ Path requested: {}", path);

                if (path.startsWith("/category/") || path.startsWith("/admin/") || path.startsWith("/product/")) {
                    List<Map<String, String>> roles = (List<Map<String, String>>) claims.get("roles");
                    log.info("➡️ Token roles: {}", roles);

                    boolean isAdmin = roles.stream()
                            .anyMatch(role ->
                                    "ROLE_ADMIN".equals(role.get("authority"))
                                            || "ROLE_SUPERADMIN".equals(role.get("authority"))
                            );

                    if (!isAdmin) {
                        log.error("❌ Access denied (403) for non-admin user on path: {}", path);
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }
                }
                log.info("✅ JWT validated successfully for path: {}", path);
            }else{
                log.info("✅ Public route accessed: {}", exchange.getRequest().getURI().getPath());
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // No custom config needed for now
    }
}
