package com.search.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 검색 히스토리 도메인 클래스
 * @version 1.0
 * @author jeonjihoon
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;

    @Column
    private String keyWord;

    @CreatedDate
    private LocalDateTime searchDate;
}
