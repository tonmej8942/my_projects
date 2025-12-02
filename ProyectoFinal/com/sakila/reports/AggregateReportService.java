package com.sakila.reports;
import com.sakila.data.ConnectionProvider;
import java.sql.*;
import java.util.*;
public class AggregateReportService {
    private final ConnectionProvider provider;
    public AggregateReportService(ConnectionProvider provider) {
        this.provider = provider;
    }
    public Map<Integer, Long> rentalsByStore() {
        String sql = "SELECT i.store_id, COUNT(*) AS cnt " +
                     "FROM rental r " +
                     "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                     "GROUP BY i.store_id";
        return singleGroupCount(sql, "store_id", "cnt");
    }
    public Map<Integer, Long> rentalsByCity() {
        String sql = "SELECT a.city_id, COUNT(*) AS cnt " +
                     "FROM rental r " +
                     "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                     "JOIN store s ON i.store_id = s.store_id " +
                     "JOIN address a ON s.address_id = a.address_id " +
                     "GROUP BY a.city_id";
        return singleGroupCount(sql, "city_id", "cnt");
    }
    public Map<Integer, Long> rentalsByCountry() {
        String sql = "SELECT c.country_id, COUNT(*) AS cnt " +
                     "FROM rental r " +
                     "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                     "JOIN store s ON i.store_id = s.store_id " +
                     "JOIN address a ON s.address_id = a.address_id " +
                     "JOIN city ci ON a.city_id = ci.city_id " +
                     "JOIN country c ON ci.country_id = c.country_id " +
                     "GROUP BY c.country_id";
        return singleGroupCount(sql, "country_id", "cnt");
    }
    public Map<Integer, Long> rentalsByCustomer() {
        String sql = "SELECT r.customer_id, COUNT(*) AS cnt " +
                     "FROM rental r GROUP BY r.customer_id";
        return singleGroupCount(sql, "customer_id", "cnt");
    }
    public Map<Integer, Double> paymentsByStore() {
        String sql = "SELECT i.store_id, SUM(p.amount) AS total " +
                     "FROM payment p " +
                     "JOIN rental r ON p.rental_id = r.rental_id " +
                     "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                     "GROUP BY i.store_id";
        return singleGroupSum(sql, "store_id", "total");
    }
    public Map<Integer, Double> paymentsByCity() {
        String sql = "SELECT a.city_id, SUM(p.amount) AS total " +
                     "FROM payment p " +
                     "JOIN rental r ON p.rental_id = r.rental_id " +
                     "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                     "JOIN store s ON i.store_id = s.store_id " +
                     "JOIN address a ON s.address_id = a.address_id " +
                     "GROUP BY a.city_id";
        return singleGroupSum(sql, "city_id", "total");
    }
    public Map<Integer, Double> paymentsByCountry() {
        String sql = "SELECT c.country_id, SUM(p.amount) AS total " +
                     "FROM payment p " +
                     "JOIN rental r ON p.rental_id = r.rental_id " +
                     "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                     "JOIN store s ON i.store_id = s.store_id " +
                     "JOIN address a ON s.address_id = a.address_id " +
                     "JOIN city ci ON a.city_id = ci.city_id " +
                     "JOIN country c ON ci.country_id = c.country_id " +
                     "GROUP BY c.country_id";
        return singleGroupSum(sql, "country_id", "total");
    }
    private Map<Integer, Long> singleGroupCount(String sql, String keyCol, String valCol) {
        Map<Integer, Long> out = new LinkedHashMap<>();
        try (Connection conn = provider.get();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.put(rs.getInt(keyCol), rs.getLong(valCol));
        } catch (SQLException e) {
            throw new RuntimeException("Aggregate count failed: " + e.getMessage(), e);
        }
        return out;
    }

    private Map<Integer, Double> singleGroupSum(String sql, String keyCol, String valCol) {
        Map<Integer, Double> out = new LinkedHashMap<>();
        try (Connection conn = provider.get();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.put(rs.getInt(keyCol), rs.getDouble(valCol));
        } catch (SQLException e) {
            throw new RuntimeException("Aggregate sum failed: " + e.getMessage(), e);
        }
        return out;
    }
}