package com.example.taskEvdokimov.service;

import com.example.taskEvdokimov.entity.SupplyProduct;
import com.example.taskEvdokimov.repository.SupplyProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SupplyProductService {

    @Autowired
    private SupplyProductRepository repository;

    public List<SupplyProduct> getByDate(String day, String month, String year) {
        String date = year + "-" + month + "-" + day;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<SupplyProduct> list = (List<SupplyProduct>) repository.findAllByDate(LocalDate.parse(date,formatter));
        System.out.println(list.get(0).getSupply().getId_doc() + "\n\n");
        return (List<SupplyProduct>) repository.findAllByDate(LocalDate.parse(date,formatter));
    }

    public List<SupplyProduct> getByDateIfAuthorized(String day, String month, String year) {
        String date = year + "-" + month + "-" + day;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return (List<SupplyProduct>) repository.findAllByDateIfAuthorized(LocalDate.parse(date,formatter), true);
    }
}
