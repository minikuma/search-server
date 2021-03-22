package com.search.server.service;

import com.search.server.domain.SearchHistory;
import com.search.server.dto.history.BaseHistoryResponse;
import com.search.server.dto.history.HistoryDto;
import com.search.server.dto.history.HistoryRequestDto;
import com.search.server.dto.search.SearchRequestDto;
import com.search.server.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional
    public void createSearchHistory(SearchRequestDto request, String userName) {
        SearchHistory searchHistory = SearchHistory.builder()
                .userName(userName)
                .searchDate(LocalDateTime.now())
                .keyWord(request.getKeyWord())
                .build();
        searchHistoryRepository.save(searchHistory);
    }

    public BaseHistoryResponse<HistoryDto> searchHistoryList(HistoryRequestDto request) {
        List<SearchHistory> findHistoryList = searchHistoryRepository.findByUserName(request.getUserName());
        List<HistoryDto> lists = findHistoryList.stream().map(searchHistory -> HistoryDto.builder()
                .searchDate(searchHistory.getSearchDate())
                .keyWord(searchHistory.getKeyWord())
                .build()).collect(Collectors.toList());

        return BaseHistoryResponse.<HistoryDto>builder()
                .history(lists)
                .status(200)
                .message("success")
                .build();
    }
}
