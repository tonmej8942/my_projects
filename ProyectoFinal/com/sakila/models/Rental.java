package com.sakila.models;
import java.time.LocalDateTime;
public class Rental {
    public Integer rental_id;
    public java.time.LocalDateTime rental_date;
    public Integer inventory_id; // FK
    public Integer customer_id;  // FK
    public LocalDateTime return_date;
    public Integer staff_id;     // FK
    public LocalDateTime last_update;
}