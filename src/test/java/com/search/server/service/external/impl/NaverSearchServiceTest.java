package com.search.server.service.external.impl;

import com.search.server.config.property.OpenApiProperty;
import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NaverSearchServiceTest {
    @Autowired
    private NaverSearchService naverSearchService;

    @Autowired
    private OpenApiProperty property;

    @Test
    void naver_open_api_property_injection_test() {
        String expectd = "https://openapi.naver.com/v1/search/local.json?";
        assertEquals(expectd, property.getNaverUri());
    }

    @Test
    void 외부_API_연동_테스트() {
        // given
        SearchRequestDto request = SearchRequestDto.builder()
                .page(1)
                .size(2)
                .keyWord("곱창")
                .build();

        // when
        BaseResponseDto<SearchDto> result = naverSearchService.searchData(request);

        // then
        assertEquals(result.getSize(), 2);
    }
}