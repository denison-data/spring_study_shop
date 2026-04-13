package com.denison.shops.repository.board;

import com.denison.shops.domain.board.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 현재 글 번호보다 작은 글 중 가장 큰(가까운) 글 하나
    Optional<Review> findTopByNoLessThanAndDelFlagOrderByNoDesc(Long no, Review.DeleteFlag deleteFlag);

    // 현재 글 번호보다 큰 글 중 가장 작은(가까운) 글 하나
    Optional<Review> findTopByNoGreaterThanAndDelFlagOrderByNoAsc(Long no, Review.DeleteFlag deleteFlag);

    Page<Review> findByDelFlagOrderByRegdateDesc(
            Review.DeleteFlag delFlag,
            Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Review r SET r.hit = r.hit + 1 WHERE r.no = :no")
    int incrementHitJpql(@Param("no") Long no);

}
