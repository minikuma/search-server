package com.search.server.controller.api;

import com.search.server.dto.history.BaseHistoryResponse;
import com.search.server.dto.history.HistoryDto;
import com.search.server.dto.history.HistoryRequestDto;
import com.search.server.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 검색 히스토리 컨트롤러
 * @version 1.0
 * @author jeonjihoon
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    @GetMapping("/user/search/history")
    public BaseHistoryResponse<HistoryDto> searchHistory(@RequestBody HistoryRequestDto request) {
        return searchHistoryService.searchHistoryList(request);
    }
}
