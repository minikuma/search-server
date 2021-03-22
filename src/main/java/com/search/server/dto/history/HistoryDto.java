package com.search.server.dto.history;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HistoryDto {
    private String keyWord;
    private LocalDateTime searchDate;
}
