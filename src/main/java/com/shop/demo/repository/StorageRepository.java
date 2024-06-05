package com.shop.demo.repository;

import com.shop.demo.domain.entities.Storage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {

    Optional<Storage> findByItem(Long itemId);
}
