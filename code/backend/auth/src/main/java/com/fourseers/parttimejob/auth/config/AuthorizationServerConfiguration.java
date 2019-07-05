package com.fourseers.parttimejob.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Value("${app.test_resource_id:TESTID}")
    private String TEST_RESOURCE_ID;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userDetailsServiceBean")
    private UserDetailsService userDetailsService;


    // use redis to store token
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        String wechatClientSecret = passwordEncoder.encode("123456");
        String webClientSecret = passwordEncoder.encode("123456");
        clients.inMemory()
                .withClient("wechatClient")
                .secret(wechatClientSecret)
                //.resourceIds(TEST_RESOURCE_ID)
                .authorizedGrantTypes("password", "client_credentials", "refresh_token")
                //.scopes("select")
                .authorities("oauth2")
                .and().withClient("client_2")
                .resourceIds(TEST_RESOURCE_ID)
                .authorizedGrantTypes("password", "client_credentials", "refresh_token")
                .scopes("server")
                .authorities("oauth2")
                .secret(webClientSecret)
                .and().withClient("_internalMS")
                //.resourceIds(TEST_RESOURCE_ID)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                //.scopes("select")
                .authorities("oauth2");
        // clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(getRedisTokenStore())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()")          // oauth/token_key
                .checkTokenAccess("isAuthenticated()")  // oauth/check_token
                .allowFormAuthenticationForClients();
    }


}
