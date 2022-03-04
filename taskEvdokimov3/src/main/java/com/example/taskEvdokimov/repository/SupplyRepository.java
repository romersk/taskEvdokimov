package com.example.taskEvdokimov.repository;

import com.example.taskEvdokimov.entity.Supply;
import com.example.taskEvdokimov.entity.SupplyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    @Query("SELECT u FROM Supply u WHERE u.doc_date > ?1")
    Collection<Supply> findAllByDate(LocalDate date);
}
