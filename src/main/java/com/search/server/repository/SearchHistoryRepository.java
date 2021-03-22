package com.search.server.repository;

import com.search.server.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserNameOrderByIdDesc(String userName);
}
