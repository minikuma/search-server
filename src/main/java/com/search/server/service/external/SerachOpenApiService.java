package com.search.server.service.external;

import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.KakaoSearchResponseDto;
import com.search.server.dto.search.SearchDto;
import com.search.server.dto.search.SearchRequestDto;

import java.util.concurrent.CompletableFuture;

public interface SerachOpenApiService {
    /* *
     * 검색 데이터
     * @version 1.0
     * @author jeonjihoon
     */
    BaseResponseDto<SearchDto> searchData(SearchRequestDto request);
}