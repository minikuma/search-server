package com.search.server.service.external.impl;

import com.search.server.config.property.OpenApiProperty;
import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.KakaoSearchResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;
import com.search.server.service.external.SerachOpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoSearchService implements SerachOpenApiService {

    private final RestTemplate restTemplate;
    private final OpenApiProperty property;

    @Override
    public BaseResponseDto<SearchDto> searchData(SearchRequestDto request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "KakaoAK " + property.getKakaoApiKey());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(property.getKakaoUri())
                .queryParam("query", request.getKeyWord())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .build();
        ResponseEntity<KakaoSearchResponseDto> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, KakaoSearchResponseDto.class);
        log.info(responseEntity.getStatusCode().toString());
        return Objects.requireNonNull(responseEntity.getBody()).convertSerchResponse(request);
    }
}
