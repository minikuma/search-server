package com.search.server.service.external.impl;

import com.search.server.config.property.OpenApiProperty;
import com.search.server.dto.search.*;
import com.search.server.exception.biz.KakaoOpenApiException;
import com.search.server.exception.biz.NaverOpenApiException;
import com.search.server.service.external.SerachOpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@EnableRetry
@Service
public class NaverSearchService implements SerachOpenApiService {

    private final RestTemplate restTemplate;
    private final OpenApiProperty property;

    @Retryable(maxAttempts = 2, value = NaverOpenApiException.class, backoff = @Backoff(delay = 200))
    public BaseResponseDto<SearchDto> searchData(SearchRequestDto request) throws NaverOpenApiException{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Naver-Client-Id", property.getNaverClientId());
        headers.add("X-Naver-Client-Secret", property.getNaverClientSecret());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(property.getNaverUri())
                .queryParam("query", request.getKeyWord())
                .queryParam("start", request.getPage())
                .queryParam("display", request.getSize())
                .build();

        try {
            ResponseEntity<NaverSearchResponseDto> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, NaverSearchResponseDto.class);
            return Objects.requireNonNull(responseEntity.getBody()).convertSerchResponse(request);
        } catch (Exception e) {
            throw new NaverOpenApiException();
        }
    }

    @Recover
    private BaseResponseDto<SearchDto> recover(NaverOpenApiException e, SearchRequestDto request) {
        log.info("Naver Open API Fault");
        return new BaseResponseDto<SearchDto>();
    }
}
