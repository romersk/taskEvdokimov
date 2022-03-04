package com.example.taskEvdokimov.service;

import com.example.taskEvdokimov.entity.Supply;
import com.example.taskEvdokimov.entity.SupplyProduct;
import com.example.taskEvdokimov.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SupplyService {

    @Autowired
    private SupplyRepository repository;

    public List<Supply> getByDate(String day, String month, String year) {
        String date = year + "-" + month + "-" + day;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return (List<Supply>) repository.findAllByDate(LocalDate.parse(date,formatter));
    }
}
