package com.denison.shops.repository;

import com.denison.shops.domain.product.GoodOption;
import com.denison.shops.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodOptionRepository extends JpaRepository<GoodOption, Long> {
    // ✅ 상품 ID(No)로 조회하고 정렬
//    @Query(value = "SELECT * FROM cmmall_good_option WHERE No = :productId AND ing_flag = 'y' ORDER BY No ASC", nativeQuery = true)
    // 네이티브 쿼리 제거하고 JPA 쿼리 사용
    List<GoodOption> findByProductIdAndIngFlag(Long productId, String ingFlag);

    /* list 업데이트후 미사용
    @Query("SELECT o FROM GoodOption o WHERE o.productId = :productId AND o.ingFlag = 'y'")
    List<GoodOption> findByProductId(@Param("productId") Long productId);
    */
    // ✅ 또는 String 타입으로 조회하는 경우
//    @Query("SELECT o FROM GoodOption o WHERE o.productId = :productId AND o.ingFlag = 'y' ORDER BY o.guest ASC")
//    List<GoodOption> findByProductIdOrdered(@Param("productId") Long productId);

}
