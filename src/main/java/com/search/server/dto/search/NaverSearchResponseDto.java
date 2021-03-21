package com.search.server.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NaverSearchResponseDto {

    private List<Item> items;
    private int total;
    private int display;
    private int start;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Item {
        @JsonProperty("title")
        private String title;

        public SearchDto converSerachDto() {
            String htmlTag = "<[^>]*>";
            return SearchDto.builder()
                    .placeName(this.title != null ? this.title.replaceAll(htmlTag, "") : null)
                    .build();
        }
    }

    public BaseResponseDto<SearchDto> convertSerchResponse(SearchRequestDto request) {
        return BaseResponseDto.<SearchDto>builder()
                .places(this.items.stream().map(Item::converSerachDto).collect(Collectors.toList()))
                .totalPage(this.total)
                .page(request.getPage())
                .totalCount(this.display)
                .size(request.getSize())
                .build();
    }
}
