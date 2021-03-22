package com.search.server.service.external.impl;

import com.search.server.config.property.OpenApiProperty;
import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KakaoSearchServiceTest {

    @Autowired
    private KakaoSearchService kakaoSearchService;

    @Autowired
    private OpenApiProperty property;

    @Test
    void kakao_open_api_property_injection_test() {
        String expectd = "https://dapi.kakao.com/v2/local/search/keyword.json?";
        assertEquals(expectd, property.getKakaoUri());
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
        BaseResponseDto<SearchDto> result = kakaoSearchService.searchData(request);

        // then
        assertEquals(result.getSize(), 2);
    }
}