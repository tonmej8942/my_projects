package com.sakila.data;
import com.sakila.models.Customer;
import java.sql.*;
import java.util.Arrays;
public final class CustomerRepo extends DataContext<Customer, Integer> {
    public CustomerRepo(ConnectionProvider provider) {
        super(provider, "customer", "customer_id", Arrays.asList("customer_id","store_id","first_name","last_name","email","address_id","active"));
    }
    @Override
    protected Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.customer_id = rs.getInt("customer_id");
        c.store_id = rs.getInt("store_id");
        c.first_name = rs.getString("first_name");
        c.last_name = rs.getString("last_name");
        c.email = rs.getString("email");
        c.address_id = rs.getInt("address_id");
        c.active = rs.getBoolean("active");
        return c;
    }
    @Override
    protected void bindInsert(PreparedStatement ps, Customer c) throws SQLException {
        int i = 1;
        ps.setInt(i++, c.store_id);
        ps.setString(i++, c.first_name);
        ps.setString(i++, c.last_name);
        ps.setString(i++, c.email);
        ps.setInt(i++, c.address_id);
        ps.setBoolean(i++, c.active != null ? c.active : Boolean.TRUE);
    }
    @Override
    protected void bindUpdate(PreparedStatement ps, Customer c) throws SQLException { bindInsert(ps, c); }
    @Override
    protected Integer extractPk(ResultSet rs) throws SQLException { return rs.getInt(1); }
    @Override
    protected void setPk(PreparedStatement ps, int index, Integer id) throws SQLException { ps.setInt(index, id); }
}