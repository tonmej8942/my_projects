package com.sakila.models;
import java.time.LocalDateTime;
public class Payment {
    public Integer payment_id;
    public Integer customer_id; // FK
    public Integer staff_id;    // FK
    public Integer rental_id;   // FK
    public Double amount;
    public LocalDateTime payment_date;
}