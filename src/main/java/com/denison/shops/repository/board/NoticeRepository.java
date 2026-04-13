package com.denison.shops.repository.board;

import com.denison.shops.domain.board.Event;
import com.denison.shops.domain.board.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // ✅ subject 필드로 검색 + delFlag 필드로 필터링
    Page<Notice> findBySubject(
            String subject,
            Notice.DeleteFlag deleteFlag,  // ✅ delFlag (소문자 'd')
            Pageable pageable);
    // ✅ delFlag 필드만으로 필터링
    // ✅ 새로운 메서드 추가 (부분 일치 LIKE 검색)
    Page<Notice> findBySubjectContainingAndDelFlag(
            String subject,
            Notice.DeleteFlag delFlag,
            Pageable pageable);
    // ✅ contents 필드 검색용 (content -> contents 필드명 주의!)
    Page<Notice> findByContentsContainingAndDelFlag(
            String contents,
            Notice.DeleteFlag delFlag,
            Pageable pageable);

    // ✅ 전체 조회용
    Page<Notice> findByDelFlagOrderByWdateDesc(
            Notice.DeleteFlag delFlag,
            Pageable pageable);

    Page<Notice> findByDelFlag(
            Notice.DeleteFlag deleteFlag,  // ✅ delFlag
            Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Notice n SET n.hit = n.hit + 1 WHERE n.no = :no")
    int incrementHitJpql(@Param("no") Long no);

    @Query("SELECT n FROM Notice n " +
            "WHERE n.delFlag = 'n'" +
            "ORDER BY n.id DESC")
    List<Notice> findMainBoardNoticeJpql( org.springframework.data.domain.Pageable pageable);
}
