package com.denison.shops.repository.board;

import com.denison.shops.domain.board.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    // 1. PHP 쿼리와 동일한 기능: RAND() + LIMIT
    /* 미사용
    @Query(value = "SELECT * FROM cmmall_keyword " +
            "WHERE del_flag = 'n' AND division = :division " +
            "ORDER BY RAND() LIMIT :limit",
            nativeQuery = true)
    List<Keyword> findRandomKeywords(
            @Param("division") String division,
            @Param("limit") int limit);
    */
    // 1. JPQL 버전 (권장 - 타입 안전)
    @Query("SELECT k FROM Keyword k " +
            "WHERE k.delFlag = 'n' AND k.division = :division " +
            "ORDER BY FUNCTION('RAND')")
    List<Keyword> findRandomKeywordsJpql(
            @Param("division") String division,
            org.springframework.data.domain.Pageable pageable);
}
