package com.sakila.controllers;
import com.sakila.data.FilmRepo;
import com.sakila.models.Film;
import java.util.*;
public class FilmController {
    private final FilmRepo repo;
    public FilmController(FilmRepo repo) { this.repo = repo; }
    public List<Film> listAll() { return repo.getAll(); }
    public Optional<Film> get(Integer id) { return repo.getById(id); }
    public List<Film> searchByTitleRegex(String regex) { return repo.searchLike("title", regex); }
    public Integer create(Film f) { return repo.post(f); }
    public boolean update(Integer id, Film f) { return repo.put(id, f); }
    public boolean remove(Integer id) { return repo.delete(id); }
}