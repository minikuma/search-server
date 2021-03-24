package com.search.server.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.server.domain.SearchHistory;
import com.search.server.dto.history.HistoryRequestDto;
import com.search.server.repository.SearchHistoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
class SearchHistoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @BeforeAll
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @WithMockUser(roles="USER")
    void 사용자기준_검색_히스토리_정상조회() throws Exception {
        // 히스토리 도메인 저장
        SearchHistory searchHistory = SearchHistory.builder()
                .searchDate(LocalDateTime.now())
                .userName("회원-1")
                .keyWord("국수")
                .build();

        searchHistoryRepository.save(searchHistory);

        // 히스토리 조회 용 요청 생성
        HistoryRequestDto history = HistoryRequestDto.builder()
                .userName("회원-1")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        // 사용자 기준 검색 히스토리 조회
        mvc.perform(get("/api/v1/user/search/history")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(history)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    void 사용자기준_검색_히스토리_유효하지않은_사용자() throws Exception {
        // 히스토리 도메인 저장
        SearchHistory searchHistory = SearchHistory.builder()
                .searchDate(LocalDateTime.now())
                .userName("회원-2")
                .keyWord("문방구")
                .build();

        searchHistoryRepository.save(searchHistory);

        // 히스토리 조회 용 요청 생성 (유효하지 않는 회원)
        HistoryRequestDto history = HistoryRequestDto.builder()
                .userName("회원-1")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        // 사용자 기준 검색 히스토리 조회
        mvc.perform(get("/api/v1/user/search/history")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(history)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").value(0));
    }
}