package com.search.server.repository;

import com.search.server.domain.SearchHistory;
import com.search.server.dto.history.HistoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserName(String userName);
}
