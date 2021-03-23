package com.search.server.service.external.impl;

import com.search.server.config.property.OpenApiProperty;
import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.KakaoSearchResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Log4j2
@SpringBootTest
class KakaoSearchServiceTest {

    @Autowired
    private KakaoSearchService kakaoSearchService;

    @Autowired
    private OpenApiProperty property;

    @MockBean
    private RestTemplate restTemplate;

    SearchRequestDto searchRequestDto;
    KakaoSearchResponseDto kakaoSearchResponseDto;

//    @BeforeEach
//    public void initDummyRequest() {
//        searchRequestDto = SearchRequestDto.builder()
//                .keyWord("테스트")
//                .page(1)
//                .size(10)
//                .build();
//    }
//
//    @BeforeEach
//    public void initDummyKakaoResponse() {
//        KakaoSearchResponseDto.Meta meta = KakaoSearchResponseDto.Meta.builder()
//                .isEnd(false)
//                .pageableCount(1)
//                .totalCount(10)
//                .build();
//        List<KakaoSearchResponseDto.Documents> documents = Collections.singletonList(
//                KakaoSearchResponseDto.Documents.builder()
//                        .placeName("자바맛집")
//                        .build()
//        );
//        kakaoSearchResponseDto = KakaoSearchResponseDto.builder()
//                .documents(documents)
//                .meta(meta)
//                .build();
//    }

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
        assertNotNull(findResponse);
    }

//    @Test
//    void 요청파라미터는_필수값이다() {
//        // given
//        String api = property.getKakaoUri();
//        String params = UriComponentsBuilder.fromHttpUrl(api)
//                .queryParam("query", searchRequestDto.getKeyWord())
//                .queryParam("page", searchRequestDto.getPage())
//                .queryParam("size", searchRequestDto.getSize())
//                .build().toString();
//
//        ArgumentCaptor<String> args = ArgumentCaptor.forClass(String.class);
//
//        // when
//        when(restTemplate.exchange(args.capture(), any(), any(), eq(KakaoSearchResponseDto.class)))
//                .thenReturn(new ResponseEntity<>(kakaoSearchResponseDto, HttpStatus.OK));
//
//        log.info(kakaoSearchResponseDto.getDocuments().get(0).getPlaceName());
//
//        kakaoSearchService.searchData(searchRequestDto);
//        log.info("args >>> " + args.getValue());
//
//        // then
//        assertEquals(args.getValue(), params);
//    }
}