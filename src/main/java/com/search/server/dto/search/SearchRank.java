package com.search.server.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 검색 랭킹을 위한 클래스
 * @version 1.0
 * @author jeonjihoon
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SearchRank {
    private String keyWord;
    private int count;
}
