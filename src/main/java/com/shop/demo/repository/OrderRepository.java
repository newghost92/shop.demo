package com.shop.demo.repository;

import com.shop.demo.domain.entities.Order;
import com.shop.demo.repository.mapper.I_Order;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"customer, orderItems"})
    Optional<Order> getDetailById(Long id);

    @Query(nativeQuery = true,
            value = " SELECT " +
                    "   o.id                AS id, " +
                    "   c.id                AS customerId, " +
                    "   c.name              AS customerName, " +
                    "   c.address           AS customerAddress, " +
                    "   c.email             AS customerEmail, " +
                    "   c.telephone_number  AS customerTel, " +
                    "   o.total_price       AS totalPrice, " +
                    "   o.status            AS status " +
                    " FROM orders o " +
                    " LEFT JOIN customers c " +
                    "   ON o.customer_id = c.id " +
                    " WHERE (:id IS NULL OR o.id = :id) " +
                    "   AND (:customerName IS NULL OR c.name LIKE ':customerName%') " +
                    "   AND (:status IS NULL OR o.status = :status) " ,
            countQuery = " SELECT COUNT (1) " +
                    " FROM orders o " +
                    " LEFT JOIN customers c " +
                    "   ON o.customer_id = c.id " +
                    " WHERE (:id IS NULL OR o.id = :id) " +
                    "   AND (:customerName IS NULL OR c.name LIKE ':customerName%') " +
                    "   AND (:status IS NULL OR o.status = :status) " )
    Page<I_Order> search(
            @Param("id") Long id,
            @Param("customerName") String customerName,
            @Param("status") Integer status,
            Pageable pageable
    );
}
