package com.assessment.controllers;

import com.assessment.requests.Checkout;
import com.assessment.responses.RentalAgreement;
import com.assessment.services.RentalAgreementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/rental-application")
public class RentalAgreementsController {
    private final RentalAgreementsService service;

    @Autowired
    public RentalAgreementsController(RentalAgreementsService service){
        this.service = service;
    }

    @PostMapping("/checkout")
    public ResponseEntity<RentalAgreement> checkout(@RequestBody Checkout checkout) throws Exception{
        RentalAgreement ra = service.createAgreement(checkout);

        return ResponseEntity.ok(ra);
    }
}
