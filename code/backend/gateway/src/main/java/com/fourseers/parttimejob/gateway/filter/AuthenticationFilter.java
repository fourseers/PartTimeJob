package com.fourseers.parttimejob.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.fourseers.parttimejob.gateway.AuthService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final static String BASIC_AUTH_TOKEN = "Authorization";
    private final static String CLIENT_TOKEN = "x-access-token";
    private final static String INTERNAL_TOKEN = "x-internal-token";


    @Autowired
    private AuthService authService;

    /*
     * Filter for authorization.
     * Check both client auth (clientId + clientSecret in HTTP Basic Auth)
     * & access token (in HTTP headers)
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // check if should be authenticated
        if (exchange.getRequest().getPath().toString().startsWith("/auth"))
            return chain.filter(exchange);

        Logger logger = LoggerFactory.getLogger(this.getClass());

        ServerHttpRequest request = exchange.getRequest();
        logger.debug("{} {}", request.getMethodValue(), request.getPath().value());

        String clientAuthToken = request.getHeaders().getFirst(BASIC_AUTH_TOKEN);
        String accessToken = request.getHeaders().getFirst(CLIENT_TOKEN);

        // check existence of client info & access token
        if (StringUtils.isBlank(clientAuthToken) || StringUtils.isBlank(accessToken)) {
            logger.debug("[NO TOKEN OR TOKEN ILLEGAL]");
            return unauthorized(exchange);
        }

        //invoke auth service to check authorization
        JSONObject response = authService.checkToken(accessToken, clientAuthToken);
        if (!response.containsKey("error")) {
            ServerHttpRequest.Builder builder = request.mutate();
            // unset client token
            //builder.header(CLIENT_TOKEN, "");
            // TODO use jwt
            builder.header(INTERNAL_TOKEN, response.getString("user_name"));
            return chain.filter(exchange.mutate().request(builder.build()).build());
        }
        return unauthorized(exchange);
    }

    /**
     * Refuses and return 401
     *
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap("Forbidden, access token not found.".getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public static class Config {

    }
}