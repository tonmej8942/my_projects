package com.sakila.data;
import com.sakila.models.Store;
import java.sql.*;
import java.util.Arrays;
public final class StoreRepo extends DataContext<Store, Integer> {
    public StoreRepo(ConnectionProvider provider) {
        super(provider, "store", "store_id",
              Arrays.asList("store_id","manager_staff_id","address_id"));
    }
    @Override
    protected Store mapRow(ResultSet rs) throws SQLException {
        Store s = new Store();
        s.store_id = rs.getInt("store_id");
        s.manager_staff_id = rs.getInt("manager_staff_id");
        s.address_id = rs.getInt("address_id");
        return s;
    }
    @Override
    protected void bindInsert(PreparedStatement ps, Store s) throws SQLException {
        int i = 1;
        ps.setInt(i++, s.manager_staff_id);
        ps.setInt(i++, s.address_id);
    }
    @Override
    protected void bindUpdate(PreparedStatement ps, Store s) throws SQLException { bindInsert(ps, s); }
    @Override
    protected Integer extractPk(ResultSet rs) throws SQLException { return rs.getInt(1); }
    @Override
    protected void setPk(PreparedStatement ps, int index, Integer id) throws SQLException { ps.setInt(index, id); }
}