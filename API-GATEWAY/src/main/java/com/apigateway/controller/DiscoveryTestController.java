package com.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscoveryTestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/test-services")
    public List<String> getServices() {
        return discoveryClient.getServices();  // returns list like ["com.bill.service", "com.auth-service"]
    }
}
