package com.denison.shops.repository;

import com.denison.shops.domain.product.Social;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialRepository extends JpaRepository<Social, Long> {
    Optional<Social> findByCodeAndDelFlag(String code, String delFlag);

    Optional<Social> findTopByDelFlagAndDisplayOrderByInsertDateDesc(String delFlag, String display);

}
