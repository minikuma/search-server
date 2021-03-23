package com.search.server.service.external.impl;

import com.search.server.config.property.OpenApiProperty;
import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.KakaoSearchResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;
import com.search.server.exception.biz.KakaoOpenApiException;
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
@EnableRetry
@RequiredArgsConstructor
@Service
public class KakaoSearchService implements SerachOpenApiService {

    private final RestTemplate restTemplate;
    private final OpenApiProperty property;

    @Retryable(maxAttempts = 2, value = KakaoOpenApiException.class, backoff = @Backoff(delay = 200))
    public BaseResponseDto<SearchDto> searchData(SearchRequestDto request) throws KakaoOpenApiException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "KakaoAK " + property.getKakaoApiKey());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(property.getKakaoUri())
                .queryParam("query", request.getKeyWord())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .build();

        try {
            ResponseEntity<KakaoSearchResponseDto> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, KakaoSearchResponseDto.class);
            log.info("here!!");
            return Objects.requireNonNull(responseEntity.getBody()).convertSerchResponse(request);
        } catch (Exception e) {
            throw new KakaoOpenApiException();
        }
    }

    @Recover
    private BaseResponseDto<SearchDto> recover(KakaoOpenApiException e, SearchRequestDto request) {
        log.info("Kakao Open API Fault " + e.getMessage());
        return new BaseResponseDto<SearchDto>();
    }
}
