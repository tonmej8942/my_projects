package com.sakila.data;
import com.sakila.models.Country;
import java.sql.*;
import java.util.Arrays;
public final class CountryRepo extends DataContext<Country, Integer> {
    public CountryRepo(ConnectionProvider provider) {
        super(provider, "country", "country_id", Arrays.asList("country_id","country"));
    }
    @Override
    protected Country mapRow(ResultSet rs) throws SQLException {
        Country c = new Country();
        c.country_id = rs.getInt("country_id");
        c.country = rs.getString("country");
        return c;
    }
    @Override
    protected void bindInsert(PreparedStatement ps, Country c) throws SQLException {
        int i = 1;
        ps.setString(i++, c.country);
    }
    @Override
    protected void bindUpdate(PreparedStatement ps, Country c) throws SQLException { bindInsert(ps, c); }
    @Override
    protected Integer extractPk(ResultSet rs) throws SQLException { return rs.getInt(1); }
    @Override
    protected void setPk(PreparedStatement ps, int index, Integer id) throws SQLException { ps.setInt(index, id); }
}