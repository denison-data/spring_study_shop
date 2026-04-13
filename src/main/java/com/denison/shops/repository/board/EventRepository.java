package com.denison.shops.repository.board;

import com.denison.shops.domain.board.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    // ✅ subject 필드로 검색 + delFlag 필드로 필터링
    Page<Event> findBySubjectContainingAndDelFlag(
            String subject,
            Event.DeleteFlag delFlag,  // ✅ delFlag (소문자 'd')
            Pageable pageable);

    // ✅ showit 필드로 필터링 + delFlag 필드로 필터링
    Page<Event> findByShowitAndDelFlag(
            String showit,
            Event.DeleteFlag delFlag,  // ✅ delFlag
            Pageable pageable);

    // ✅ delFlag 필드만으로 필터링
    Page<Event> findByDelFlag(
            Event.DeleteFlag delFlag,  // ✅ delFlag
            Pageable pageable);

    // no(기본키)로 이벤트 조회
    @Query("SELECT e FROM Event e WHERE e.id = :no")
    Optional<Event> findByNo(@Param("no") Long no);

    // 삭제되지 않은 이벤트만 조회
    @Query("SELECT e FROM Event e WHERE e.id = :no AND e.delFlag = 'n'")
    Optional<Event> findByNoAndActive(@Param("no") Long no);

    // 4. PHP와 동일한 Native Query (FileList JOIN 포함)
    @Query(value = "SELECT a.*, " +
            "(SELECT filename1 FROM file_list WHERE f_idx = a.fileidx) AS attach " +
            "FROM cmmall_event a " +
            "WHERE del_flag = 'n' " +
            "AND showit = 'y' " +
            "AND subject NOT LIKE '%당첨자%' " +
            "ORDER BY no DESC",
            nativeQuery = true)
    Page<Object[]> findEventsWithAttach(Pageable pageable);
}

