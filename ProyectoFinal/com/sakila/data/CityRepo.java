package com.sakila.data;
import com.sakila.models.City;
import java.sql.*;
import java.util.Arrays;
public final class CityRepo extends DataContext<City, Integer> {
    public CityRepo(ConnectionProvider provider) {
        super(provider, "city", "city_id", Arrays.asList("city_id","city","country_id"));
    }
    @Override
    protected City mapRow(ResultSet rs) throws SQLException {
        City c = new City();
        c.city_id = rs.getInt("city_id");
        c.city = rs.getString("city");
        c.country_id = rs.getInt("country_id");
        return c;
    }
    @Override
    protected void bindInsert(PreparedStatement ps, City c) throws SQLException {
        int i = 1;
        ps.setString(i++, c.city);
        ps.setInt(i++, c.country_id);
    }
    @Override
    protected void bindUpdate(PreparedStatement ps, City c) throws SQLException { bindInsert(ps, c); }
    @Override
    protected Integer extractPk(ResultSet rs) throws SQLException { return rs.getInt(1); }
    @Override
    protected void setPk(PreparedStatement ps, int index, Integer id) throws SQLException { ps.setInt(index, id); }
}