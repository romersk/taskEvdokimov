package com.example.taskEvdokimov.repository;

import com.example.taskEvdokimov.entity.SupplyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface SupplyProductRepository extends JpaRepository<SupplyProduct, Long> {
    @Query("SELECT u FROM SupplyProduct u WHERE u.supply.doc_date > ?1")
    Collection<SupplyProduct> findAllByDate(LocalDate date);

    @Query("SELECT u FROM SupplyProduct u WHERE u.supply.doc_date > ?1 AND u.supply.is_authorized = ?2")
    Collection<SupplyProduct> findAllByDateIfAuthorized(LocalDate date, boolean isAuthorized);
}
