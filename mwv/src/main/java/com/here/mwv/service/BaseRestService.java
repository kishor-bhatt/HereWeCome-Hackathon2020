package com.here.mwv.service;




import com.here.mwv.configuration.MultiWaypointConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This abstract class having an implementation to call any  Restful  service supporting oauth1 and oauth2.
 * Implementation class need to provide the authentication details by overriding the  abstract method.
 */

@Slf4j
public abstract class BaseRestService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MultiWaypointConfig multiWaypointConfig;

    /**
     * This method responsible to call RestFul service .
     * @param requestedPath
     * @param method
     * @param responseType
     * @param url
     * @param body
     * @param headers
     * @param params
     * @param <T>
     * @param <B>
     * @return
     */
    public <T, B> ResponseEntity<T> processRequest(String requestedPath, HttpMethod method,
                                                   Class<T> responseType, String url,
                                                   B body, HttpHeaders headers, Map<String, ?> params) {
        if (null == headers) {
            throw new RestClientException("Missing Header,Please provide the header");
        }
        if (null == params) {
            params = new HashMap<>();
        }
        HttpEntity<?> request = new HttpEntity<>(body, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + requestedPath);

        params.forEach((k,v)->{
            try {
                builder.queryParam(k, URLEncoder.encode((String)v,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("Unable to encode query parameter with error message "+e.getMessage(),e);
            }
        });
        URI requestUri = builder.build(true).toUri();
        log.debug("Request: url-{}, method-{}, request-{}, responseType-{}, params-{}", requestUri, method, request,
                responseType, params);
        return  restTemplate.exchange(requestUri, method, request, responseType);
    }

    public void populateWithBearer(HttpHeaders headers) {
        if (Objects.nonNull(headers)) {
            headers.add("Authorization", "Bearer " +multiWaypointConfig.hereAccessTokenProvider().getAccessToken());
        }
    }



}



