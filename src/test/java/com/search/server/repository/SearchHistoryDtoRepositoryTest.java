package com.search.server.repository;

import com.search.server.domain.SearchHistory;
import com.search.server.dto.history.HistoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SearchHistoryDtoRepositoryTest {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Test
    void 검색히스토리를_등록하고_회원으로_조회한다() {
        // given
        SearchHistory searchHistory = SearchHistory.builder()
                .keyWord("검색-1")
                .userName("회원-1")
                .searchDate(LocalDateTime.now())
                .build();
        // when
        searchHistoryRepository.save(searchHistory);
        List<SearchHistory> findHistory = searchHistoryRepository.findByUserName(searchHistory.getUserName());

        // then
        assertEquals(findHistory.size(), 1);
        assertEquals(findHistory.get(0).getKeyWord(), searchHistory.getKeyWord());
        assertEquals(findHistory.get(0).getSearchDate(), searchHistory.getSearchDate());
    }
}