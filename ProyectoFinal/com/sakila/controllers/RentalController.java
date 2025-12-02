package com.sakila.controllers;
import com.sakila.data.RentalRepo;
import com.sakila.models.Rental;
import java.util.*;
public class RentalController {
    private final RentalRepo repo;
    public RentalController(RentalRepo repo) { this.repo = repo; }
    public List<Rental> listAll() { return repo.getAll(); }
    public List<Rental> byCustomer(Integer customerId) {
        Map<String,Object> filters = new HashMap<>();
        filters.put("customer_id", customerId);
        return repo.search(filters);
    }
    public Integer create(Rental r) { return repo.post(r); }
    public boolean update(Integer id, Rental r) { return repo.put(id, r); }
    public boolean remove(Integer id) { return repo.delete(id); }
}