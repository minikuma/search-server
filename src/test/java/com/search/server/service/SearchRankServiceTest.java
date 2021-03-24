package com.search.server.service;

import com.search.server.domain.SearchRank;
import com.search.server.dto.rank.BaseRankResponse;
import com.search.server.dto.rank.SearchRankDto;
import com.search.server.repository.SearchRankRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SearchRankServiceTest {

    @Autowired
    private SearchRankService searchRankService;

    @Autowired
    private SearchRankRepository searchRankRepository;

    @Test
    @WithMockUser(roles="USER")
    void 검색랭킹_조회_테스트() {
        // 검색 랭킹 데이터 등록
        SearchRank searchRank = SearchRank.builder()
                .keyWord("검색조회")
                .rankingCount(2)
                .build();

        searchRankRepository.save(searchRank);

        BaseRankResponse<SearchRankDto> response = searchRankService.rankingList();

        assertEquals(response.getRanking().get(0).getKeyWord(), searchRank.getKeyWord());
    }
}