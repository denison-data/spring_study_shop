package com.denison.shops.repository;

import com.denison.shops.domain.product.BestGood;
import com.denison.shops.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestGoodRepository extends JpaRepository<BestGood, Long> {

    @Query(value = "SELECT og.No as id, og.`Code` as code, og.`Name` as name, og.`Vendor` as vendor, og.`MDName` as mdname, " +
            "og.`Category_Code` as categoryCode, og.`Middle_Picture` as middlePicture, og.`Comment` as comment, og.`Small_Picture` as smallPicture " +
            "FROM cmmall_best_code mc " +
            "LEFT JOIN cmmall_good_new og ON mc.`Code` = og.`Code` " +
            "WHERE mc.del_flag = 'n' " +
            "AND mc.ing_flag = 'y' " +
            "AND mc.division = :division " +
            "AND og.`Display` = 'y' " +
            "ORDER BY mc.RegDate DESC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM cmmall_best_code mc " +
                    "LEFT JOIN cmmall_good_new og ON mc.`Code` = og.`Code` " +
                    "WHERE mc.del_flag = 'n' " +
                    "AND mc.ing_flag = 'y' " +
                    "AND mc.division = :division " +
                    "AND og.`Display` = 'y'",
            nativeQuery = true)


    Page<ProductProjection> findBestProducts(@Param("division") String division, Pageable pageable);

}



