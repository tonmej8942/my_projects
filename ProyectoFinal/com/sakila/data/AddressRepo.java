package com.sakila.data;
import com.sakila.models.Address;
import java.sql.*;
import java.util.Arrays;
public final class AddressRepo extends DataContext<Address, Integer> {
    public AddressRepo(ConnectionProvider provider) {
        super(provider, "address", "address_id", Arrays.asList("address_id","address","district","city_id","postal_code","phone"));
    }
    @Override
    protected Address mapRow(ResultSet rs) throws SQLException {
        Address a = new Address();
        a.address_id = rs.getInt("address_id");
        a.address = rs.getString("address");
        a.district = rs.getString("district");
        a.city_id = rs.getInt("city_id");
        a.postal_code = rs.getString("postal_code");
        a.phone = rs.getString("phone");
        return a;
    }
    @Override
    protected void bindInsert(PreparedStatement ps, Address a) throws SQLException {
        int i = 1;
        ps.setString(i++, a.address);
        ps.setString(i++, a.district);
        ps.setInt(i++, a.city_id);
        ps.setString(i++, a.postal_code);
        ps.setString(i++, a.phone);
    }
    @Override
    protected void bindUpdate(PreparedStatement ps, Address a) throws SQLException { bindInsert(ps, a); }
    @Override
    protected Integer extractPk(ResultSet rs) throws SQLException { return rs.getInt(1); }
    @Override
    protected void setPk(PreparedStatement ps, int index, Integer id) throws SQLException { ps.setInt(index, id); }
}