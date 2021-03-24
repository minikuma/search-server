package com.search.server.service.external.impl;

import com.search.server.config.property.OpenApiProperty;
import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
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
        SearchRequestDto request = SearchRequestDto.builder()
                .page(1)
                .size(2)
                .keyWord("전골")
                .build();
        // when
        BaseResponseDto<SearchDto> findResponse = kakaoSearchService.searchData(request);

        // then
        assertEquals(findResponse.getSize(), 2);
    }
}