package com.search.server.service;

import com.search.server.domain.SearchHistory;
import com.search.server.dto.history.BaseHistoryResponse;
import com.search.server.dto.history.HistoryDto;
import com.search.server.dto.history.HistoryRequestDto;
import com.search.server.dto.search.SearchRequestDto;
import com.search.server.repository.SearchHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchHistoryServiceTest {

    @Autowired
    private SearchHistoryService searchHistoryService;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Test
    @WithMockUser(roles="USER")
    void 검색히스토리_조회_테스트() {
        // 검색 데이터 등록
        SearchHistory searchHistory = SearchHistory.builder()
                .searchDate(LocalDateTime.now())
                .keyWord("검색-1")
                .userName("minikuma")
                .build();
        searchHistoryRepository.save(searchHistory);

        HistoryRequestDto request = HistoryRequestDto.builder()
                .userName("minikuma")
                .build();

        BaseHistoryResponse<HistoryDto> response = searchHistoryService.searchHistoryList(request);

        assertEquals(response.getTotalCount(), 1);
        assertEquals(response.getHistory().get(0).getKeyWord(), searchHistory.getKeyWord());
    }
}