package com.search.server.repository;

import com.search.server.domain.SearchRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRankRepository extends JpaRepository<SearchRank, Long> {
    List<SearchRank> findTop10ByOrderByRankingCountDesc();
    SearchRank findByKeyWord(String keyWord);
}
