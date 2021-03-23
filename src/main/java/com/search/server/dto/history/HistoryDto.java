package com.search.server.dto.history;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 검색 히스토리 세부 항목 DTO
 * @version 1.0
 * @author jeonjihoon
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HistoryDto {
    private String keyWord;
    private LocalDateTime searchDate;
}
