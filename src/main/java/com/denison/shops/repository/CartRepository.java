package com.denison.shops.repository;

import com.denison.shops.domain.product.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findBySessionIdAndNoAndOptionNoAndQuantity(
            String sessionId, int no, String optionNo, int quantity
    );

    List<Cart> findBySessionIdOrderByCnoDesc(String sessionId);

}
