package com.sakila.controllers;
import com.sakila.data.PaymentRepo;
import com.sakila.models.Payment;
import java.util.*;
public class PaymentController {
    private final PaymentRepo repo;
    public PaymentController(PaymentRepo repo) { this.repo = repo; }
    public List<Payment> listAll() { return repo.getAll(); }
    public Optional<Payment> get(Integer id) { return repo.getById(id); }
    public List<Payment> byCustomer(Integer customerId) {
        Map<String,Object> f = new HashMap<>();
        f.put("customer_id", customerId);
        return repo.search(f);
    }
    public Integer create(Payment p) { return repo.post(p); }
    public boolean update(Integer id, Payment p) { return repo.put(id, p); }
    public boolean remove(Integer id) { return repo.delete(id); }
}