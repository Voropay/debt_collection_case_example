package com.intrum.homework.debt.service;

import com.intrum.homework.debt.domain.customer.Customer;
import com.intrum.homework.debt.domain.debtcollectioncase.*;
import com.intrum.homework.debt.domain.user.User;
import com.intrum.homework.debt.repository.DebtCollectionCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DebtCollectionCaseServiceImpl implements DebtCollectionCaseService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private DebtCollectionCaseRepository debtCollectionCaseRepository;

    public DebtCollectionCase create(DebtCollectionCaseRequest debtCollectionCaseRequest, User user) {

        Customer customer = customerService.create(debtCollectionCaseRequest.getCustomer());

        if(user.getId() == null) {
            user = userService.findByUsername(user.getUsername());
        }

        DebtCollectionCaseEntity dbCase = new DebtCollectionCaseEntity();
        dbCase.setAmount(debtCollectionCaseRequest.getAmount());
        dbCase.setCustomer(customer);
        dbCase.setOwnerId(user.getId());
        dbCase.setStatus(debtCollectionCaseRequest.getStatus());
        dbCase.setDueDate(debtCollectionCaseRequest.getDueDate());
        dbCase.setPaidDate(debtCollectionCaseRequest.getPaidDate());
        debtCollectionCaseRepository.save(dbCase);

        return dbCase;
    }

    public List<DebtCollectionCaseEntity> findByOwnerId(Long ownerId) {
        return debtCollectionCaseRepository.findByOwnerId(ownerId);
    }

    public DebtCollectionCase updateStatus(DebtCollectionCaseStatusRequest debtCollectionCaseStatusRequest, User user) {
        Optional<DebtCollectionCaseEntity> result = debtCollectionCaseRepository.findById(debtCollectionCaseStatusRequest.getId());
        if(!result.isPresent()) {
            return null;
        }
        DebtCollectionCaseEntity dcCase = result.get();
        if(dcCase.getOwnerId() != user.getId()) {
            return null;
        }
        dcCase.setStatus(debtCollectionCaseStatusRequest.getStatus());
        if(debtCollectionCaseStatusRequest.getStatus() == DebtCollectionCaseStatus.PAID.status()) {
            dcCase.setPaidDate(debtCollectionCaseStatusRequest.getPaidDate());
        }
        debtCollectionCaseRepository.save(dcCase);
        return dcCase;
    }
}
