package com.example.taskEvdokimov.controller;

import com.example.taskEvdokimov.entity.Supply;
import com.example.taskEvdokimov.entity.SupplyProduct;
import com.example.taskEvdokimov.service.SupplyProductService;
import com.example.taskEvdokimov.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/postings")
public class ContentController {

    @Autowired
    private SupplyProductService service;

    @GetMapping("/{day}/{month}/{year}")
    public ResponseEntity<List<SupplyProduct>> list(@PathVariable Map<String, String> pathVarsMap) {
        String day = pathVarsMap.get("day");
        String month = pathVarsMap.get("month");
        String year = pathVarsMap.get("year");

        try {
            return new ResponseEntity<List<SupplyProduct>>(service.getByDate(day, month, year), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<SupplyProduct>>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{day}/{month}/{year}/true")
    public ResponseEntity<List<SupplyProduct>> listIfAuthorized(@PathVariable Map<String, String> pathVarsMap) {
        String day = pathVarsMap.get("day");
        String month = pathVarsMap.get("month");
        String year = pathVarsMap.get("year");

        try {
            return new ResponseEntity<List<SupplyProduct>>(service.getByDateIfAuthorized(day, month, year), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<SupplyProduct>>(HttpStatus.NOT_FOUND);
        }
    }
}
