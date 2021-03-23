package com.search.server.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 검색 랭킹 도메인
 * @version 1.0
 * @author jeonjihoon
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SearchRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String keyWord;

    @Column
    @Setter
    private Integer rankingCount;
}
