package com.search.server.controller.api;

import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.KakaoSearchResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;
import com.search.server.service.external.impl.KakaoSearchService;
import com.search.server.service.external.impl.NaverSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SearchController {

    private final KakaoSearchService kakaoSearchService;
    private final NaverSearchService naverSearchService;

    @GetMapping("/user/search/kakao")
    public BaseResponseDto<SearchDto> kakaoSearch(@RequestBody SearchRequestDto request) {
        return kakaoSearchService.searchData(request);
    }

    @GetMapping("/user/search/naver")
    public BaseResponseDto<SearchDto> naverSearch(@RequestBody SearchRequestDto request) {
        return naverSearchService.searchData(request);
    }
}
