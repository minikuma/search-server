package com.search.server.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 장소 검색 응답
 * @param <T>
 * @version 1.0
 * @author jeonjihoon
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BaseResponseDto<T> {
    private List<T> places;
    private int page;
    private int size;
    private int totalPage;
    private int totalCount;
}
