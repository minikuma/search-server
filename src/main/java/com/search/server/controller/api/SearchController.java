package com.search.server.controller.api;

import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;
import com.search.server.service.external.impl.KakaoSearchService;
import com.search.server.service.external.impl.NaverSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SearchController {

    private final KakaoSearchService kakaoSearchService;
    private final NaverSearchService naverSearchService;


    @GetMapping("/user/search")
    public BaseResponseDto<SearchDto> search(@RequestBody SearchRequestDto request) {

        BaseResponseDto<SearchDto> k = kakaoSearchService.searchData(request);
        BaseResponseDto<SearchDto> n = naverSearchService.searchData(request);

        log.info("kakao: " + k.getPlaces().get(0).getPlaceName());
        log.info("naver: " + n.getPlaces().get(0).getPlaceName());

        return k;
    }

    @GetMapping("/user/search/kakao")
    public BaseResponseDto<SearchDto> kakaoSearch(@RequestBody SearchRequestDto request) {
        return kakaoSearchService.searchData(request);
    }

    @GetMapping("/user/search/naver")
    public BaseResponseDto<SearchDto> naverSearch(@RequestBody SearchRequestDto request) {
        return naverSearchService.searchData(request);
    }
}
