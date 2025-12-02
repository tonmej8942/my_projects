package com.sakila.reports;
import com.sakila.data.*;
import com.sakila.models.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
public class ReportService {
    public final FilmRepo films;
    public final InventoryRepo inventory;
    public final RentalRepo rentals;
    public ReportService(FilmRepo films, InventoryRepo inventory, RentalRepo rentals) {
        this.films = films;
        this.inventory = inventory;
        this.rentals = rentals;
    }
    public <T> void exportCsv(List<T> data, String[] headers, ValueExtractor<T> extractor, File file) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println(String.join(",", headers));
            for (T t : data) {
                List<String> vals = extractor.apply(t);
                pw.println(vals.stream().map(v -> v == null ? "" : v.replace(",", " ")).collect(Collectors.joining(",")));
            }
        } catch (IOException e) {
            throw new RuntimeException("CSV export failed: " + e.getMessage(), e);
        }
    }
    public <T> void exportJson(List<T> data, JsonSerializer<T> serializer, File file) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println("[");
            for (int i = 0; i < data.size(); i++) {
                pw.print(serializer.toJson(data.get(i)));
                if (i < data.size() - 1) pw.println(",");
            }
            pw.println("]");
        } catch (IOException e) {
            throw new RuntimeException("JSON export failed: " + e.getMessage(), e);
        }
    }
    public Map<String, Object> statsInventoryPerFilm() {
        List<Inventory> inv = inventory.getAll();
        Map<Integer, Long> countPerFilm = inv.stream().collect(Collectors.groupingBy(i -> i.film_id, Collectors.counting()));
        Map<String, Object> out = new HashMap<>();
        out.put("total_items", inv.size());
        out.put("by_film", countPerFilm);
        return out;
    }
    public Map<String, Object> statsActorsPerFilm(Map<Integer, List<Integer>> filmActors) {
        Map<String, Object> out = new HashMap<>();
        Map<Integer, Integer> counts = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> e : filmActors.entrySet()) counts.put(e.getKey(), e.getValue().size());
        out.put("actors_by_film", counts);
        return out;
    }
    public Map<String, Object> statsRentalsByStoreCityCountry(Map<Integer, Integer> storeToCity, Map<Integer, Integer> cityToCountry) {
        List<Rental> rs = rentals.getAll();
        Map<Integer, Long> byStore = rs.stream().collect(Collectors.groupingBy(r -> inferStoreId(r), Collectors.counting()));
        Map<Integer, Long> byCity = new HashMap<>();
        Map<Integer, Long> byCountry = new HashMap<>();
        for (Map.Entry<Integer, Long> e : byStore.entrySet()) {
            Integer store = e.getKey();
            Integer city = storeToCity.getOrDefault(store, -1);
            Integer country = cityToCountry.getOrDefault(city, -1);
            byCity.put(city, byCity.getOrDefault(city, 0L) + e.getValue());
            byCountry.put(country, byCountry.getOrDefault(country, 0L) + e.getValue());
        }
        Map<String, Object> out = new HashMap<>();
        out.put("by_store", byStore);
        out.put("by_city", byCity);
        out.put("by_country", byCountry);
        return out;
    }
    public Map<String, Object> statsPayments(Map<Integer, List<Double>> paymentsByStore) {
        Map<String, Object> out = new HashMap<>();
        double total = 0.0;
        int count = 0;
        Map<Integer, Double> avgByStore = new HashMap<>();
        for (Map.Entry<Integer, List<Double>> e : paymentsByStore.entrySet()) {
            double sum = e.getValue().stream().mapToDouble(d -> d).sum();
            int c = e.getValue().size();
            total += sum;
            count += c;
            avgByStore.put(e.getKey(), c == 0 ? 0.0 : sum / c);
        }
        out.put("total", total);
        out.put("promedio_global", count == 0 ? 0.0 : total / count);
        out.put("promedio_por_tienda", avgByStore);
        return out;
    }
    @SuppressWarnings("unused")
	public Map<String, Long> agingAccountsReceivable(List<Rental> rentalsList) {
        Map<String, Long> buckets = new HashMap<>();
        buckets.put("0-30", 0L);
        buckets.put("31-60", 0L);
        buckets.put("61-90", 0L);
        buckets.put(">90", 0L);
        java.time.LocalDate today = java.time.LocalDate.now();
        for (Rental r : rentalsList) {
            if (r.return_date != null) continue;
            long days = java.time.Duration.between(r.rental_date, java.time.LocalDateTime.now()).toDays();
            if (days <= 30) buckets.compute("0-30", (k,v)->v+1);
            else if (days <= 60) buckets.compute("31-60", (k,v)->v+1);
            else if (days <= 90) buckets.compute("61-90", (k,v)->v+1);
            else buckets.compute(">90", (k,v)->v+1);
        }
        return buckets;
    }
    private Integer inferStoreId(Rental r) {
    	return -1;
    }
    @FunctionalInterface
    public interface ValueExtractor<T> { List<String> apply(T t); }
    @FunctionalInterface
    public interface JsonSerializer<T> { String toJson(T t); }

}
