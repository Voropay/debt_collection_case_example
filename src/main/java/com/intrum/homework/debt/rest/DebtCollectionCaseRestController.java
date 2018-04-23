package com.intrum.homework.debt.rest;

import com.intrum.homework.debt.domain.debtcollectioncase.*;
import com.intrum.homework.debt.domain.user.JwtUser;
import com.intrum.homework.debt.service.DebtCollectionCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DebtCollectionCaseRestController {

    @Autowired
    private DebtCollectionCaseService debtService;

    @RequestMapping(value = "/dccase/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody final DebtCollectionCaseRequest dcCase) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) auth.getPrincipal();
        DebtCollectionCase dbCaseObj = debtService.create(dcCase, user);
        if(dbCaseObj != null) {
            return ResponseEntity.ok("created");
        } else {
            return ResponseEntity.badRequest().body("failed");
        }
    }

    @RequestMapping(value = "/dccase/get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<DebtCollectionCaseResponse>> getAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) auth.getPrincipal();
        List<DebtCollectionCaseEntity> debtCases = debtService.findByOwnerId(user.getId());
        List<DebtCollectionCaseResponse> output = new ArrayList<>();
        for(DebtCollectionCaseEntity entity: debtCases) {
            output.add(new DebtCollectionCaseResponse(entity));
        }
        return ResponseEntity.ok(output);
    }

    @RequestMapping(value = "/dccase/updatestatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateStatus(@RequestBody final DebtCollectionCaseStatusRequest dcCase) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) auth.getPrincipal();
        DebtCollectionCase dbCaseObj = debtService.updateStatus(dcCase, user);
        if(dbCaseObj != null) {
            return ResponseEntity.ok("updated");
        } else {
            return ResponseEntity.badRequest().body("failed");
        }
    }

}
