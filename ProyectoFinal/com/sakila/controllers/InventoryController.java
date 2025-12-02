package com.sakila.controllers;
import com.sakila.data.InventoryRepo;
import com.sakila.models.Inventory;
import java.util.*;
public class InventoryController {
    private final InventoryRepo repo;
    public InventoryController(InventoryRepo repo) { this.repo = repo; }
    public List<Inventory> listAll() { return repo.getAll(); }
    public List<Inventory> byFilm(Integer filmId) {
        Map<String,Object> f = new HashMap<>();
        f.put("film_id", filmId);
        return repo.search(f);
    }
    public Integer create(Inventory i) { return repo.post(i); }
    public boolean update(Integer id, Inventory i) { return repo.put(id, i); }
    public boolean remove(Integer id) { return repo.delete(id); }
}