package com.denison.shops.repository.board;

import com.denison.shops.domain.board.Faq;
import com.denison.shops.domain.board.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    @Query("SELECT f FROM Faq f WHERE f.delFlag = :delFlag ORDER BY f.id DESC")
    List<Faq> findMainBoardFaqJpql(@Param("delFlag") Faq.DeleteFlag delFlag, Pageable pageable);

    // ✅ 새로운 메서드 추가 (부분 일치 LIKE 검색)
    Page<Faq> findBySubjectContainingAndDelFlagAndDivision(
            String subject,
            Faq.DeleteFlag delFlag,
            String division,
            Pageable pageable);

    Page<Faq> findByContentsContainingAndDelFlagAndDivision(
            String contents,
            Faq.DeleteFlag delFlag,
            String division,
            Pageable pageable);

    Page<Faq> findBySubjectAndDelFlagAndDivision(
            String subject,
            Faq.DeleteFlag delFlag,
            String division,
            Pageable pageable);

    // ✅ subject 필드로 검색 + delFlag 필드로 필터링
    Page<Faq> findBySubject(
            String subject,
            Faq.DeleteFlag deleteFlag,  // ✅ delFlag (소문자 'd')
            String division,
            Pageable pageable);

    // 레포지토리
    Page<Faq> findByDelFlagAndDivisionOrderByWdateDesc(
            Faq.DeleteFlag delFlag,
            String division,
            Pageable pageable);

}
