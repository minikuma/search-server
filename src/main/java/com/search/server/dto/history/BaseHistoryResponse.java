package com.search.server.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BaseHistoryResponse<T> {
    private List<T> history;
    private int status;
    private String message;
}
