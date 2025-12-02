package com.sakila.data;
/*
 * Copyright (c) 2025 Anthony Mejia
 * Licensed under MIT. All rights reserved.
 */
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * @author Anthony Mejia
 * @param <T>
 * @param <K>
 */
public interface iDatapost<T, K> {
    /**
     * @param entity
     * @return Clave
     */
    K post(T entity);
    /**
     * @param id Clave
     * @param entity
     * @return true
     */
    boolean put(K id, T entity);
    /**
     * @param id Clave
     * @return true
     */
    boolean delete(K id);
    /**
     * @param id Clave
     * @return Optional
     */
    Optional<T> getById(K id);
    /**
     * @return Lista
     */
    List<T> getAll();
    /**
     * @param filters Mapa columna -> valor.
     * @return Lista
     */
    List<T> search(Map<String, Object> filters);
}