package com.search.server.service.external.impl;

import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.NaverSearchResponseDto;
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
public class NaverSearchService implements SerachOpenApiService {

    private final RestTemplate restTemplate;

    @Override
    public BaseResponseDto<SearchDto> searchData(SearchRequestDto request) {

        String uri = "https://openapi.naver.com/v1/search/local.json?";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Naver-Client-Id", "nLVpM1bLgQDCkD7nNaUq");
        headers.add("X-Naver-Client-Secret", "bIdM2DYoYX");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("query", request.getKeyWord())
                .queryParam("start", request.getPage())
                .queryParam("display", request.getSize())
                .build();

        ResponseEntity<NaverSearchResponseDto> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, NaverSearchResponseDto.class);
        log.info(responseEntity.getStatusCode().toString());
        return Objects.requireNonNull(responseEntity.getBody()).convertSerchResponse(request);
    }
}