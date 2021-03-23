package com.search.server.controller.api;

import com.search.server.dto.rank.BaseRankResponse;
import com.search.server.dto.rank.SearchRankDto;
import com.search.server.service.SearchRankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SearchRankingController {

    private final SearchRankService searchRankService;

    @GetMapping("/user/search/ranking")
    public BaseRankResponse<SearchRankDto> searchRanking() {
        return searchRankService.rankingList();
    }
}
