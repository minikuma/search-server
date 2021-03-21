package com.search.server.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
