package com.PayoutEngine.processor.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class PSPApiClient {
    @Autowired
    private ExternalApiProperties externalApiProperties;

    private static final Logger log = LoggerFactory.getLogger(PSPApiClient.class);

    public ResponseEntity<?> callApi(String apiName, Map<String, String> urlParams, Object requestBody, Class<?> responsetype) {
        RestTemplate restTemplate = new RestTemplate();
        ApiConfig apiConfig = getApiConfig(apiName);
        if (apiConfig == null) {
            throw new IllegalArgumentException("API configuration not found for: " + apiName);
        }

        String url = resolveUrl(apiConfig.getUrl(), urlParams);
        HttpMethod method = HttpMethod.valueOf(apiConfig.getMethod().toUpperCase());
        HttpEntity<?> entity = apiConfig.isRequestBodyRequired()
                ? new HttpEntity<>(requestBody)
                : HttpEntity.EMPTY;

        return restTemplate.exchange(url, method, entity, responsetype);
    }

    private ApiConfig getApiConfig(String apiName) {
        return externalApiProperties.getApis().stream()
                .filter(api -> api.getName().equalsIgnoreCase(apiName))
                .findFirst()
                .orElse(null);
    }

    private String resolveUrl(String url, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return url;
    }

    public void callPartnerAPI()  {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> quote = restTemplate.getForEntity(
                "https://jsonplaceholder.typicode.com/todos/1", String.class);
        log.info(quote.toString());
    }
}
