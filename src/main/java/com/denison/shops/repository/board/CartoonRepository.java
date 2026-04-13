package com.denison.shops.repository.board;

import com.denison.shops.domain.board.Cartoon;
import com.denison.shops.domain.board.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CartoonRepository extends JpaRepository<Cartoon, Long> {
    /*
     Optional<Blog> findTopByNoLessThanAndDelFlagOrderByNoDesc(Long no, Blog.DeleteFlag deleteFlag);

    // 현재 글 번호보다 큰 글 중 가장 작은(가까운) 글 하나
    Optional<Blog> findTopByNoGreaterThanAndDelFlagOrderByNoAsc(Long no, Blog.DeleteFlag deleteFlag);
     */
    Optional<Cartoon> findTopByIdLessThanAndDelFlagOrderByIdDesc(Long id, Cartoon.DeleteFlag deleteFlag);

    Optional<Cartoon> findTopByIdGreaterThanAndDelFlagOrderByIdAsc(Long id, Cartoon.DeleteFlag deleteFlag);
    // 레포지토리
    Page<Cartoon> findByDelFlagAndDivisionOrderByWdateDesc(
            Cartoon.DeleteFlag delFlag,
            String division,
            Pageable pageable);
    /*
    @Query(value = "SELECT no, subject FROM cmmall_cartoon " +
            "WHERE no < :currentId " +
            "AND del_flag = 'N' " +
            "ORDER BY no DESC LIMIT 1", nativeQuery = true)
    Optional<Object[]> findPrevCartoonInfo(@Param("currentId") Long currentId);

    @Query(value = "SELECT no, subject FROM cmmall_cartoon " +
            "WHERE no > :currentId " +
            "AND del_flag = 'N' " +
            "ORDER BY no ASC LIMIT 1", nativeQuery = true)
    Optional<Object[]> findNextCartoonInfo(@Param("currentId") Long currentId);
    */

    @Modifying
    @Transactional
    @Query("UPDATE Cartoon c SET c.hit = c.hit + 1 WHERE c.id = :no")
    int incrementHitJpql(@Param("no") Long no);
}
