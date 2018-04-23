package com.intrum.homework.debt.repository;

import com.intrum.homework.debt.domain.debtcollectioncase.DebtCollectionCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebtCollectionCaseRepository extends JpaRepository<DebtCollectionCaseEntity, Long> {
    List<DebtCollectionCaseEntity> findByOwnerId(Long id);
}
