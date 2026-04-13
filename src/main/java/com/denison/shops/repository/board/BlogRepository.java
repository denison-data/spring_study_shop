package com.denison.shops.repository.board;

import com.denison.shops.domain.board.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    // 현재 글 번호보다 작은 글 중 가장 큰(가까운) 글 하나
    Optional<Blog> findTopByNoLessThanAndDelFlagOrderByNoDesc(Long no, Blog.DeleteFlag deleteFlag);

    // 현재 글 번호보다 큰 글 중 가장 작은(가까운) 글 하나
    Optional<Blog> findTopByNoGreaterThanAndDelFlagOrderByNoAsc(Long no, Blog.DeleteFlag deleteFlag);

    Page<Blog> findByDelFlagOrderByWdateDesc(
            Blog.DeleteFlag delFlag,
            Pageable pageable);

}
