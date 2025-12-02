package com.sakila.data;
import com.sakila.models.Inventory;
import java.sql.*;
import java.util.Arrays;
public final class InventoryRepo extends DataContext<Inventory, Integer> {
    public InventoryRepo(ConnectionProvider provider) {
        super(provider, "inventory", "inventory_id", Arrays.asList("inventory_id", "film_id", "store_id", "last_update"));
    }
    @Override
    protected Inventory mapRow(ResultSet rs) throws SQLException {
        Inventory i = new Inventory();
        i.inventory_id = rs.getInt("inventory_id");
        i.film_id = rs.getInt("film_id");
        i.store_id = rs.getInt("store_id");
        Timestamp lu = rs.getTimestamp("last_update");
        i.last_update = lu != null ? lu.toLocalDateTime() : null;
        return i;
    }
    @Override
    protected void bindInsert(PreparedStatement ps, Inventory i) throws SQLException {
        int idx = 1;
        ps.setInt(idx++, i.film_id);
        ps.setInt(idx++, i.store_id);
        ps.setTimestamp(idx++, Timestamp.valueOf(i.last_update != null ? i.last_update : java.time.LocalDateTime.now()));
    }
    @Override
    protected void bindUpdate(PreparedStatement ps, Inventory i) throws SQLException {
        bindInsert(ps, i);
    }
    @Override
    protected Integer extractPk(ResultSet rs) throws SQLException { return rs.getInt(1); }
    @Override
    protected void setPk(PreparedStatement ps, int index, Integer id) throws SQLException {
        ps.setInt(index, id);
    }
}