/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.frontend.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import co.id.mii.frontend.helper.RestTemplateInterceptorHeader;

/**
 *
 * @author RAI
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();

        if (interceptors.isEmpty()) {
            interceptors = new ArrayList<>();
        }

        interceptors.add(new RestTemplateInterceptorHeader());

        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}
