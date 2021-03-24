package com.search.server.controller.api;

import com.search.server.domain.SearchRank;
import com.search.server.repository.SearchRankRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
class SearchRankingControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private SearchRankRepository searchRankRepository;

    @BeforeAll
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @WithMockUser("USER")
    void 검색어_랭킹_정상_조회_랭킹존재() throws Exception {
        // 검색어 랭킹 등록
        SearchRank searchRank = SearchRank.builder()
                .rankingCount(1)
                .keyWord("검색어-1")
                .build();

        searchRankRepository.save(searchRank);

        // 검색어 랭킹 조회
        mvc.perform(get("/api/v1/user/search/ranking"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("USER")
    void 검색어_랭킹_정상_조회_랭킹미존재() throws Exception {
        // 검색어 랭킹 조회
        mvc.perform(get("/api/v1/user/search/ranking"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").value(0));
    }
}