package com.search.server.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 검색 히스토리 응답 DTO
 * @param <T>
 * @version 1.0
 * @author jeonjihoon
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BaseHistoryResponse<T> {
    private List<T> history;
    private int totalCount;
    private int status;
    private String message;
}
