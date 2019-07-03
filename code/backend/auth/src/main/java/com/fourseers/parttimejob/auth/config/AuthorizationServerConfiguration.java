package com.fourseers.parttimejob.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    // use redis to store token
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean("redisTokenStore")
    public RedisTokenStore getRedisTokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123456");
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql

        clients.inMemory()
                .withClient("client_1")
                .authorizedGrantTypes("password", "client_credentials")
                .scopes("all","read", "write")
                .authorities("client_credentials")
                .accessTokenValiditySeconds(7200)
                .secret(passwordEncoder.encode("123456"))

                .and().withClient("client_2")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("all","read", "write")
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(10000)
                .authorities("password")
                .secret(passwordEncoder.encode("123456"))

                .and().withClient("client_3").authorities("authorization_code","refresh_token")
                .secret(passwordEncoder.encode("123456"))
                .authorizedGrantTypes("password", "authorization_code")
                .scopes("all","read", "write")
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(10000)
                .redirectUris("http://localhost:8080/callback","http://localhost:8080/signin")

                .and().withClient("client_test")
                .secret(passwordEncoder.encode("123456"))
                .authorizedGrantTypes("all flow")
                .authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token","password", "implicit")
                .redirectUris("http://localhost:8080/callback","http://localhost:8080/signin")
                .scopes("all","read", "write")
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(10000);
        // clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(getRedisTokenStore());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()")          // oauth/token_key
                .checkTokenAccess("isAuthenticated()")  // oauth/check_token
                .allowFormAuthenticationForClients();
    }


}
