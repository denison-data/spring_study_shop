package com.denison.shops.repository.board;

import com.denison.shops.domain.board.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QnaRepository extends JpaRepository<Qna, Integer> {
    @Query("SELECT MIN(q.main) FROM Qna q WHERE q.idx = :idx")
    Integer findMinMainByIdx(@Param("idx") int idx);

}
