package com.sakila.controllers;
import com.sakila.data.CustomerRepo;
import com.sakila.models.Customer;
import java.util.*;
public class CustomerController {
    private final CustomerRepo repo;
    public CustomerController(CustomerRepo repo) { this.repo = repo; }
    public List<Customer> listAll() { return repo.getAll(); }
    public Optional<Customer> get(Integer id) { return repo.getById(id); }
    public List<Customer> byStore(Integer storeId) {
        Map<String,Object> f = new HashMap<>();
        f.put("store_id", storeId);
        return repo.search(f);
    }
    public Integer create(Customer c) { return repo.post(c); }
    public boolean update(Integer id, Customer c) { return repo.put(id, c); }
    public boolean remove(Integer id) { return repo.delete(id); }
}