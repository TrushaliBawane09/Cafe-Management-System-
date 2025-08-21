package com.apigateway.JWT;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> endPoints = List.of(
                "/auth/signup",
                "/auth/login"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request ->endPoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
