package com.example.taskEvdokimov.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "supply")
public class Supply implements Serializable {

    @Id
    private Long id_doc;
    private LocalDate doc_date;
    private LocalDate posting_date;
    private String name_user;
    private boolean is_authorized;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private Users users;

    @OneToMany(mappedBy = "supply", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SupplyProduct> supplies = new ArrayList<>();

    @JsonManagedReference
    public List<SupplyProduct> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<SupplyProduct> supplies) {
        this.supplies = supplies;
    }

    public Long getId_doc() {
        return id_doc;
    }

    public void setId_doc(Long id_doc) {
        this.id_doc = id_doc;
    }

    public LocalDate getDoc_date() {
        return doc_date;
    }

    public void setDoc_date(LocalDate doc_date) {
        this.doc_date = doc_date;
    }

    public LocalDate getPosting_date() {
        return posting_date;
    }

    public void setPosting_date(LocalDate posting_date) {
        this.posting_date = posting_date;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public boolean isIs_authorized() {
        return is_authorized;
    }

    public void setIs_authorized(boolean is_authorized) {
        this.is_authorized = is_authorized;
    }

    @JsonBackReference
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
