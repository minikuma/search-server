package com.search.server.repository;

import com.search.server.domain.SearchHistory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
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
        List<SearchHistory> findHistory = searchHistoryRepository.findByUserNameOrderByIdDesc(searchHistory.getUserName());

        // then
        assertEquals(findHistory.size(), 1);
        assertEquals(findHistory.get(0).getKeyWord(), searchHistory.getKeyWord());
        assertEquals(findHistory.get(0).getSearchDate(), searchHistory.getSearchDate());
    }

    @Test
    void 검색히스토리가_여러개인_경우_최신검색히스토리가_먼저_검색된다() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2020, 11, 12, 12, 32, 22, 3333);
        SearchHistory searchHistory1 = SearchHistory.builder()
                .keyWord("검색-2")
                .userName("회원-1")
                .searchDate(dateTime)
                .build();

        SearchHistory searchHistory2 = SearchHistory.builder()
                .keyWord("검색-3")
                .userName("회원-1")
                .searchDate(LocalDateTime.now())
                .build();

        // when
        searchHistoryRepository.save(searchHistory1);
        searchHistoryRepository.save(searchHistory2);

        List<SearchHistory> findHistory = searchHistoryRepository.findByUserNameOrderByIdDesc(searchHistory1.getUserName()); // 동일회원

        log.info("첫 번째 검색어: " + findHistory.get(0).getKeyWord() + " | " + "두 번째 검색어: " + findHistory.get(1).getKeyWord());

        // then
        assertEquals(findHistory.size(), 2);
        assertEquals(findHistory.get(0).getKeyWord(), searchHistory2.getKeyWord());
        assertEquals(findHistory.get(1).getKeyWord(), searchHistory1.getKeyWord());
    }
}