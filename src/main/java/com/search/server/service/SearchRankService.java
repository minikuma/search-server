package com.search.server.service;

import com.search.server.domain.SearchRank;
import com.search.server.dto.rank.BaseRankResponse;
import com.search.server.dto.rank.SearchRankDto;
import com.search.server.dto.search.SearchRequestDto;
import com.search.server.repository.SearchRankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 검색 랭킹 서비스
 * @version 1.0
 * @author jeonjihoon
 */

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchRankService {

    private final SearchRankRepository searchRankRepository;

    @Transactional
    public void registSearchRank(SearchRequestDto reqeust) {
        SearchRank findSearchRank = searchRankRepository.findByKeyWord(reqeust.getKeyWord());
        if (findSearchRank == null) {
            SearchRank searchRank = SearchRank.builder()
                    .rankingCount(1)
                    .keyWord(reqeust.getKeyWord())
                    .build();
            searchRankRepository.save(searchRank);
        } else {
            findSearchRank.setRankingCount(findSearchRank.getRankingCount() + 1);
        }
    }

    public BaseRankResponse<SearchRankDto> rankingList() {
        List<SearchRank> findRank = searchRankRepository.findTop10ByOrderByRankingCountDesc();

        List<SearchRankDto> lists = findRank.stream().map(f -> SearchRankDto.builder()
                .keyWord(f.getKeyWord())
                .rankingCount(f.getRankingCount())
                .build()).collect(Collectors.toList());

        return BaseRankResponse.<SearchRankDto>builder()
                .ranking(lists)
                .totalCount(lists.size())
                .status(200)
                .message("success")
                .build();
    }
}
