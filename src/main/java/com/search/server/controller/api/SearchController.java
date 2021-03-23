package com.search.server.controller.api;

import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;
import com.search.server.service.SearchHistoryService;
import com.search.server.service.SearchRankService;
import com.search.server.service.external.impl.KakaoSearchService;
import com.search.server.service.external.impl.NaverSearchService;
import com.search.server.util.PrioritySearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 검색 컨트롤러
 * @version 1.0
 * @author jeonjihoon
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SearchController {

    private final KakaoSearchService kakaoSearchService;
    private final NaverSearchService naverSearchService;
    private final SearchHistoryService searchHistoryService;
    private final SearchRankService searchRankService;

    @GetMapping("/user/search")
    public BaseResponseDto<SearchDto> search(@RequestBody SearchRequestDto request, @RequestHeader(name = "X-USER-NAME") String userName) {

        BaseResponseDto<SearchDto> kakaoResponse = kakaoSearchService.searchData(request);
        BaseResponseDto<SearchDto> naverResponse = naverSearchService.searchData(request);

        searchHistoryService.createSearchHistory(request, userName);
        searchRankService.registSearchRank(request);

        List<SearchDto> convertPlaces = PrioritySearch.prioritySearchResult(kakaoResponse, naverResponse);

        return BaseResponseDto.<SearchDto>builder()
                .places(convertPlaces)
                .page(request.getPage())
                .size(request.getSize())
                .totalPage(request.getPage())
                .totalCount(request.getSize())
                .build();
    }
}
