package com.denison.shops.repository.board;

import com.denison.shops.domain.board.GoodReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GoodReviewRepository extends JpaRepository<GoodReview, Long> {
    // 현재 글 번호보다 작은 글 중 가장 큰(가까운) 글 하나
    Optional<GoodReview> findTopByNoLessThanAndDivisionAndDelFlagOrderByNoDesc(Long no, String division, GoodReview.DeleteFlag deleteFlag);
    // 현재 글 번호보다 큰 글 중 가장 작은(가까운) 글 하나
    Optional<GoodReview> findTopByNoGreaterThanAndDivisionAndDelFlagOrderByNoAsc(Long no, String division, GoodReview.DeleteFlag deleteFlag);

    Page<GoodReview> findByDivisionAndDelFlag(String division, GoodReview.DeleteFlag deleteFlag, Pageable pageable);

    Page<GoodReview> findByDivisionAndDelFlagAndProductCodeIsNotNullAndProductCode(String division, GoodReview.DeleteFlag deleteFlag, String productCode, Pageable pageable);

    @Query("SELECT r FROM GoodReview r " +
            "WHERE r.delFlag = 'n'" +
            "ORDER BY r.id DESC")
    List<GoodReview> findMainBoardGoodReviewJpql(org.springframework.data.domain.Pageable pageable);
}
