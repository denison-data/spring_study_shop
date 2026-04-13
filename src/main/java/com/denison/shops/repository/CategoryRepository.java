package com.denison.shops.repository;

import com.denison.shops.domain.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // keyword + delFlag=N 조건으로 카테고리 조회
    @Query("SELECT c FROM Category c " +
            "WHERE c.catcontent LIKE CONCAT('%', :keyword, '%') " +
            "AND c.delFlag = :delFlag")
    List<Category> findByKeywordAndDelFlag(@Param("keyword") String keyword,
                                           @Param("delFlag") Category.DeleteFlag delFlag);
}

