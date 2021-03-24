package com.search.server.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 검색 정보
 * @version 1.0
 * @author jeonjihoon
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchDto {
    private String placeName;
}
