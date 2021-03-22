package com.search.server.controller.api;

import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;
import com.search.server.service.external.impl.KakaoSearchService;
import com.search.server.service.external.impl.NaverSearchService;
import com.search.server.util.PrioritySearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<SearchDto> convertPlaces = PrioritySearch.prioritySearchResult(k, n, request.getSize());
        return BaseResponseDto.<SearchDto>builder()
                .places(convertPlaces)
                .page(request.getPage())
                .size(request.getSize())
                .totalPage(request.getPage())
                .totalCount(request.getSize())
                .build();
    }
//
//    @GetMapping("/user/search/kakao")
//    public BaseResponseDto<SearchDto> kakaoSearch(@RequestBody SearchRequestDto request) {
//        return kakaoSearchService.searchData(request);
//    }
//
//    @GetMapping("/user/search/naver")
//    public BaseResponseDto<SearchDto> naverSearch(@RequestBody SearchRequestDto request) {
//        return naverSearchService.searchData(request);
//    }
}
