package com.fourseers.parttimejob.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@Order(-2)
public class CorsFilter implements GlobalFilter {

    private static final String MAX_AGE = "18000L";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders requestHeaders = request.getHeaders();
            HttpHeaders headers = response.getHeaders();
            // allow all origins
            headers.add("Access-Control-Allow-Origin", request.getHeaders().getOrigin());
            headers.add("Access-Control-Allow-Methods", request.getMethodValue());
            headers.add("Access-Control-Max-Age", MAX_AGE);
            headers.addAll("Access-Control-Allow-Headers", requestHeaders.getAccessControlRequestHeaders());
            headers.add("Access-Control-Expose-Headers", "*");
            headers.add("Access-Control-Allow-Credentials", "true");
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
        }
        return chain.filter(exchange);
    }

    // should be defined when using gateway cors configuration with eureka
//    @Bean
//    @Primary
//    public RouteDefinitionLocator discoveryClientRouteDefinitionLocator(
//            DiscoveryClient discoveryClient, DiscoveryLocatorProperties properties) {
//        return new DiscoveryClientRouteDefinitionLocator(discoveryClient, properties);
//    }
}
