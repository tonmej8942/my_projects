package com.sakila.data;
/*
 * Copyright (c) 2025 Anthony Mejia
 * Licensed under MIT. All rights reserved.
 */
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;
/**
 * @author Anthony Mejia
 * @param <T>
 * @param <K>
 */
public abstract class DataContext<T, K> implements iDatapost<T, K> {
    protected final ConnectionProvider connProvider;
    protected final String tableName;
    protected final String pkColumn;
    protected final List<String> columns;
    protected DataContext(ConnectionProvider provider, String tableName, String pkColumn, List<String> columns) {
        this.connProvider = provider;
        this.tableName = tableName;
        this.pkColumn = pkColumn;
        this.columns = Collections.unmodifiableList(new ArrayList<>(columns));
    }
    @Override
    public final K post(T entity) {
        String colList = String.join(", ", nonPkColumns());
        String placeholders = String.join(", ", Collections.nCopies(nonPkColumns().size(), "?"));
        String sql = "INSERT INTO " + tableName + " (" + colList + ") VALUES (" + placeholders + ")";
        try (Connection conn = connProvider.get();
        		PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	bindInsert(ps, entity);
            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Insert no afecto filas");
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return extractPk(rs);
                else throw new SQLException("No se obtuvo PK autogenerada");
            }
        } catch (SQLException ex) {
            throw new DataException("Post failed: " + ex.getMessage(), ex);
        }
    }
    @Override
    public final boolean put(K id, T entity) {
        String setList = String.join(", ", toSetMarks(nonPkColumns()));
        String sql = "UPDATE " + tableName + " SET " + setList + " WHERE " + pkColumn + " = ?";
        try (Connection conn = connProvider.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        	bindUpdate(ps, entity);
            setPk(ps, nonPkColumns().size() + 1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataException("Put failed: " + ex.getMessage(), ex);
        }
    }
    @Override
    public final boolean delete(K id) {
        String sql = "DELETE FROM " + tableName + " WHERE " + pkColumn + " = ?";
        try (Connection conn = connProvider.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setPk(ps, 1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataException("Delete failed: " + ex.getMessage(), ex);
        }
    }
    @Override
    public final Optional<T> getById(K id) {
        String sql = "SELECT " + String.join(", ", columns) + " FROM " + tableName + " WHERE " + pkColumn + " = ?";
        try (Connection conn = connProvider.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setPk(ps, 1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataException("GetById failed: " + ex.getMessage(), ex);
        }
    }
    @Override
    public final List<T> getAll() {
        String sql = "SELECT " + String.join(", ", columns) + " FROM " + tableName;
        try (Connection conn = connProvider.get();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<T> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException ex) {
            throw new DataException("GetAll failed: " + ex.getMessage(), ex);
        }
    }
    @Override
    public final List<T> search(Map<String, Object> filters) {
        if (filters == null || filters.isEmpty()) return getAll();
        StringBuilder sb = new StringBuilder("SELECT ").append(String.join(", ", columns))
                .append(" FROM ").append(tableName).append(" WHERE ");
        List<Object> params = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, Object> e : filters.entrySet()) {
            if (i++ > 0) sb.append(" AND ");
            sb.append(e.getKey()).append(" = ?");
            params.add(e.getValue());
        }
        try (Connection conn = connProvider.get();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            for (int idx = 0; idx < params.size(); idx++) ps.setObject(idx + 1, params.get(idx));
            try (ResultSet rs = ps.executeQuery()) {
                List<T> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        } catch (SQLException ex) {
            throw new DataException("Search failed: " + ex.getMessage(), ex);
        }
    }
    public final List<T> searchLike(String column, String patternRegex) {
        Pattern p = Pattern.compile(patternRegex);
        List<T> all = getAll();
        List<T> out = new ArrayList<>();
        for (T t : all) {
            if (matchesColumn(t, column, p)) out.add(t);
        }
        return out;
    }
    protected abstract T mapRow(ResultSet rs) throws SQLException;
    protected abstract void bindInsert(PreparedStatement ps, T entity) throws SQLException;
    protected abstract void bindUpdate(PreparedStatement ps, T entity) throws SQLException;
    protected abstract K extractPk(ResultSet rs) throws SQLException;
    protected abstract void setPk(PreparedStatement ps, int index, K id) throws SQLException;
    protected final List<String> nonPkColumns() {
        List<String> non = new ArrayList<>();
        for (String c : columns) if (!c.equalsIgnoreCase(pkColumn)) non.add(c);
        return non;
    }
    private List<String> toSetMarks(List<String> cols) {
        List<String> sets = new ArrayList<>();
        for (String c : cols) sets.add(c + " = ?");
        return sets;
    }
    public final <E> List<T> searchIn(String column, Collection<? extends E> values) {
    	if (values == null || values.isEmpty()) return Collections.emptyList();
        String placeholders = String.join(", ", Collections.nCopies(values.size(), "?"));
        String sql = "SELECT " + String.join(", ", columns) + " FROM " + tableName + " WHERE " + column + " IN (" + placeholders + ")";
        try (Connection conn = connProvider.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int i = 1;
            for (Object v : values) ps.setObject(i++, v);
            try (ResultSet rs = ps.executeQuery()) {
                List<T> out = new ArrayList<>();
                while (rs.next()) out.add(mapRow(rs));
                return out;
            }
        } catch (SQLException ex) {
            throw new DataException("SearchIn failed: " + ex.getMessage(), ex);
        }
    }
    protected boolean matchesColumn(T entity, String column, Pattern p) {
        Object value = Reflect.get(entity, column);
        return value != null && p.matcher(value.toString()).matches();
    }
}