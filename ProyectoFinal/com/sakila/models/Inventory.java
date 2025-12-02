package com.sakila.models;
import java.time.LocalDateTime;
public class Inventory {
    public Integer inventory_id;
    public Integer film_id;   // FK -> film
    public Integer store_id;  // FK -> store
    public LocalDateTime last_update;
}