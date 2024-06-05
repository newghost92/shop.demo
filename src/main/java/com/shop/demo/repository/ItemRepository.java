package com.shop.demo.repository;

import com.shop.demo.domain.entities.Item;
import com.shop.demo.repository.mapper.I_Item;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByName(String name);

    @Modifying
    @Query(value = " UPDATE items SET status = :status WHERE id = :id", nativeQuery = true)
    void updateItemStatus(@Param("id") Long id, @Param("status") Integer status);

    @Query(nativeQuery = true,
            value = " SELECT " +
                    "   i.id            AS id, " +
                    "   i.name          AS name, " +
                    "   i.description   AS description, " +
                    "   i.price         AS price, " +
                    "   i.status        AS status, " +
                    "   i.number        AS inventory " +
                    " FROM items i " +
                    " LEFT JOIN storages s " +
                    "   ON i.id = s.item_id " +
                    " WHERE (:name IS NULL OR i.name LIKE ':name%') " +
                    "   AND (:description IS NULL OR MATCH(i.description) AGAINST(:description)) " +
                    "   AND (:status IS NULL OR i.status = :status) " ,
            countQuery = " SELECT COUNT (1) " +
                    " FROM items i " +
                    " LEFT JOIN storages s " +
                    "   ON i.id = s.item_id " +
                    " WHERE (:name IS NULL OR i.name LIKE ':name%') " +
                    "   AND (:description IS NULL OR MATCH(i.description) AGAINST(:description)) " +
                    "   AND (:status IS NULL OR i.status = :status) ")
    Page<I_Item> search(
            @Param("name") String name,
            @Param("description") String description,
            @Param("status") Integer status,
            Pageable pageable
    );

}
