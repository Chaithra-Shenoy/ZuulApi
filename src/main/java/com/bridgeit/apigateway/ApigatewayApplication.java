package com.bridgeit.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.bridgeit.apigateway.filter.RouteFilter;
import com.google.common.base.Preconditions;



@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}
	@Bean
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }
	
}
