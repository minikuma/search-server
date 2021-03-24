package com.search.server.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 검색 히스토리 응답 DTO
 * @version 1.0
 * @author jeonjihoon
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryRequestDto {
    @NotBlank(message = "사용자는 필수값입니다.")
    private String userName;
}
