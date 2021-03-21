package com.search.server.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Kakao Open Search API 응답
 * @version 1.0
 * @author jeonjihoon
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoSearchResponseDto {

    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("documents")
    private List<Documents> documents;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    private static class Documents {
        @JsonProperty("place_name")
        private String placeName;  // 장소이름

        // Document -> SearchDto 로 변경
        public SearchDto convertSearchDto() {
            return SearchDto.builder()
                    .placeName(this.placeName)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    private static class Meta {
        @JsonProperty("total_count")
        private int totalCount;
        @JsonProperty("pageable_count")
        private int pageableCount;
        @JsonProperty("is_end")
        private boolean isEnd;
    }


    public BaseResponseDto<SearchDto> convertSerchResponse(SearchRequestDto request) {
        return BaseResponseDto.<SearchDto>builder()
                .places(this.documents.stream().map(Documents::convertSearchDto).collect(Collectors.toList()))
                .totalPage(this.meta.pageableCount)
                .page(request.getPage())
                .size(request.getSize())
                .totalCount(this.meta.totalCount)
                .build();
    }
}
