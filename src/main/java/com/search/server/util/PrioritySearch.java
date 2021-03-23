package com.search.server.util;

import com.search.server.dto.search.BaseResponseDto;
import com.search.server.dto.search.SearchDto;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * OPEN API 결과를 우선순위에 따라 재 정의
 * @version 1.0
 * @author jeonjihoon
 */
@Slf4j
public class PrioritySearch {
    public static List<SearchDto> prioritySearchResult(BaseResponseDto<SearchDto> r1, BaseResponseDto<SearchDto> r2) {
        WeakHashMap<String, Integer> maps = new WeakHashMap<>();

        for (int i = 0; i < r1.getPlaces().size(); i++) {
            maps.put(r1.getPlaces().get(i).getPlaceName(), 1);
        }

        for (int i = 0; i < r2.getPlaces().size(); i++) {
            maps.put(r2.getPlaces().get(i).getPlaceName(), 0);
        }

        List<Map.Entry<String, Integer>> list = new LinkedList<>(maps.entrySet());
        sortedList(list);
        return createSearchDtoList(list);
    }

    /**
     * 맵 엔트리 정보 정렬
     * @param lists (API 연동 결과 셋)
     */
    private static void sortedList(List<Map.Entry<String, Integer>> lists) {
        lists.sort((o1, o2) -> {
            int comparision = (o1.getValue() - o2.getValue()) * -1;
            return comparision == 0 ? o1.getKey().compareTo(o2.getKey()) : comparision;
        });
    }

    /**
     * 정렬된 리스트 기준으로 결과 셋 생성
     * @param list (맵 정렬 리스트)
     * @return SearchDto 결과 셋 리스트
     */
    private static List<SearchDto> createSearchDtoList(List<Map.Entry<String, Integer>> list) {
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        List<String> mapToLists = new ArrayList<>(sortedMap.keySet());
        List<SearchDto> searchDtoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            SearchDto searchDto = SearchDto.builder()
                    .placeName(mapToLists.get(i)).build();
            searchDtoList.add(searchDto);
        }
        return searchDtoList;
    }
}
