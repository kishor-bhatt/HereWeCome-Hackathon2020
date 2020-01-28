package com.here.mwv.configuration;

import com.here.account.auth.OAuth1ClientCredentialsProvider;
import com.here.account.oauth2.HereAccessTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MultiWaypointConfig {

    @Value("${here.token.endpoint.url}")
    private String tokenEndpointUrl;

    @Value("${here.access.key.id}")
    private String accessKeyId;

    @Value("${here.access.key.secret}")
    private String accessKeySecret;



    @Bean
    @Primary
    RestTemplate getRestTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    /**
     * Creates a bean of type {@link HereAccessTokenProvider}
     * @return {@link HereAccessTokenProvider} object
     */
    @Bean
    public HereAccessTokenProvider hereAccessTokenProvider(){
        return HereAccessTokenProvider.builder()
                .setClientAuthorizationRequestProvider(getOAuth1ClientCredentialsProvider())
                .build();
    }
    private OAuth1ClientCredentialsProvider getOAuth1ClientCredentialsProvider(){
        return new OAuth1ClientCredentialsProvider(tokenEndpointUrl,accessKeyId,accessKeySecret);
    }
}
