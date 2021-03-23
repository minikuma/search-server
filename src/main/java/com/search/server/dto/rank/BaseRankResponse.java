package com.search.server.dto.rank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BaseRankResponse<T> {
    private List<T> ranking;
    private int totalCount;
    private int status;
    private String message;
}
