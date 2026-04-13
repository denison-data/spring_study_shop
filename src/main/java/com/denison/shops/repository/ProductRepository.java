package com.denison.shops.repository;

import com.denison.shops.domain.product.Product;
import com.denison.shops.dto.api.ProductDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // ✅ 기본 메서드만 사용
    // JPA가 제공하는 기본 메서드:
    // - Optional<Product> findById(Long id)
    // - List<Product> findAll()
    // - <S extends Product> S save(S entity)
    // - void deleteById(Long id)
    // 등등...

    // ❌ 이 메서드 삭제 (status 필드가 없음)
    // Optional<Product> findByIdAndStatus(Long id, String status);
    Optional<Product> findByCode(String code);

    // ✅ 필요한 경우 del_flag 기반으로 조회
    Optional<Product> findByIdAndDelFlag(Long id, String delFlag);

    // ✅ 삭제되지 않은 상품만 조회
    Optional<Product> findByIdAndDelFlagNot(Long id, String delFlag);

    // _ver2 메서드 추가
    @Query("SELECT p FROM Product p " +
            "WHERE p.delFlag = :delFlag " +
            "AND p.code IS NOT NULL " +
            "AND p.code <> :code " +
            "ORDER BY p.name DESC")
    List<Product> findByDelFlagAndCodeIsNotNullAndCodeNotOrderByNameDesc_ver2(@Param("delFlag") String delFlag,
                                                                              @Param("code") String code);

    @Query(value = "SELECT * FROM cmmall_good_new WHERE del_flag = :delFlag AND display = :display AND oem_id = :oemId AND code != :code ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<Product> findRandomProducts(@Param("delFlag") String delFlag,
                                     @Param("display") String display,
                                     @Param("oemId") String oemId,
                                     @Param("code") String code);
    // 코드가 아닌경우
    List<Product> findByDelFlagAndCodeIsNotNullAndCodeNotOrderByNameDesc(String delFlag, String code);

    List<Product> findByDelFlagAndDisplayAndOemIdAndCodeNot(String delFlag, String display, String oemId, String code);

    @Query("SELECT p FROM Product p " +
            "WHERE p.delFlag = 'n' AND p.endYn ='y' AND p.display='y' " +
            "AND p.category LIKE CONCAT('%/', :catNo, '/%') ")
    Page<Product> findByCategoryLike(@Param("catNo") String catNo, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.delFlag = 'n' AND p.endYn ='y' AND p.display='y' " +
            "AND p.categoryCode LIKE CONCAT('%/', :catNo, '/%') ")
    Page<Product> findByCategoryCodeLike(@Param("catNo") String catNo, Pageable pageable);

    //  AND p.endYn ='y' AND p.display='y'
    // Category_Code LIKE 검색으로 상품 조회 (페이징 지원)
    @Query("SELECT p FROM Product p " +
            "WHERE p.delFlag = 'n' AND p.endYn ='y' AND p.display='y' " +
            "AND p.category LIKE CONCAT('%/', :catNo, '/%') " +
            "AND (:catNo2 IS NULL OR p.categoryCode LIKE CONCAT('%', :catNo2, '%'))")
    Page<Product> findByCategoryCodeLikeAge(@Param("catNo") String catNo,
                                         @Param("catNo2") String catNo2,
                                         Pageable pageable);


// ✅ 이름으로 검색 (삭제되지 않은 상품)
    // List<Product> findByNameContainingAndDelFlagNot(String name, String delFlag);
}