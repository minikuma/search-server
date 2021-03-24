package com.search.server.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 검색 요청
 * @version 1.0
 * @author jeonjihoon
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchRequestDto {
    @NotBlank(message = "검색 키워드는 필수값입니다.")
    private String keyWord;
    private int page;
    private int size;
}
