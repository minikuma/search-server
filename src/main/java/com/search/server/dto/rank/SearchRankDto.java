package com.search.server.dto.rank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 검색 랭크 세부 항목 DTO
 * @version 1.0
 * @author jeonjihoon
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchRankDto {
    private String keyWord;
    private int rankingCount;
}
