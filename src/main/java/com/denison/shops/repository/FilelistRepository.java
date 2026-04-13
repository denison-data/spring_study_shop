package com.denison.shops.repository;

import com.denison.shops.domain.board.Filelist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilelistRepository extends JpaRepository<Filelist, Long> {
    // fidx 리스트로 조회 (IN 쿼리)
    List<Filelist> findByFidxIn(List<Long> fidxes);

    // filename1으로 검색
    List<Filelist> findByFilename1Containing(String filename);
}
