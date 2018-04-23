package com.intrum.homework.debt.service;

import com.intrum.homework.debt.domain.debtcollectioncase.DebtCollectionCase;
import com.intrum.homework.debt.domain.debtcollectioncase.DebtCollectionCaseEntity;
import com.intrum.homework.debt.domain.debtcollectioncase.DebtCollectionCaseRequest;
import com.intrum.homework.debt.domain.debtcollectioncase.DebtCollectionCaseStatusRequest;
import com.intrum.homework.debt.domain.user.User;

import java.util.List;

public interface DebtCollectionCaseService {
    DebtCollectionCase create(DebtCollectionCaseRequest debtCollectionCaseRequest, User user);
    List<DebtCollectionCaseEntity> findByOwnerId(Long id);
    DebtCollectionCase updateStatus(DebtCollectionCaseStatusRequest debtCollectionCaseStatusRequest, User user);
}
