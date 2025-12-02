package com.sakila.models;
import java.time.LocalDateTime;
public class Film {
    public Integer film_id;
    public String title;
    public String description;
    public Integer release_year;
    public Integer language_id;
    public Integer rental_duration = 3; // Definir valor manualmente
    public Double rental_rate = 4.99; // Definit valor manualmente
    public Integer length;
    public Double replacement_cost = 19.99; // Definir valor manualmente
    public String rating = "G"; // Definir rating manualmente
    public LocalDateTime last_update;
}