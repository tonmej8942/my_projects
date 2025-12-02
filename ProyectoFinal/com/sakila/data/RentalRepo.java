package com.sakila.data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import com.sakila.models.Rental;
public final class RentalRepo extends DataContext<Rental, Integer> {
    public RentalRepo(ConnectionProvider provider) {
        super(provider, "rental", "rental_id", Arrays.asList("rental_id","rental_date","inventory_id","customer_id","return_date","staff_id","last_update"));
    }
    @Override
    protected Rental mapRow(ResultSet rs) throws SQLException {
        Rental r = new Rental();
        r.rental_id = rs.getInt("rental_id");
        r.rental_date = rs.getTimestamp("rental_date").toLocalDateTime();
        r.inventory_id = rs.getInt("inventory_id");
        r.customer_id = rs.getInt("customer_id");
        Timestamp ret = rs.getTimestamp("return_date");
        r.return_date = ret != null ? ret.toLocalDateTime() : null;
        r.staff_id = rs.getInt("staff_id");
        Timestamp lu = rs.getTimestamp("last_update");
        r.last_update = lu != null ? lu.toLocalDateTime() : null;
        return r;
    }
    @Override
    protected void bindInsert(PreparedStatement ps, Rental r) throws SQLException {
        int i = 1;
        ps.setTimestamp(i++, Timestamp.valueOf(r.rental_date != null ? r.rental_date : java.time.LocalDateTime.now()));
        ps.setInt(i++, r.inventory_id);
        ps.setInt(i++, r.customer_id);
        if (r.return_date != null) ps.setTimestamp(i++, Timestamp.valueOf(r.return_date));
        else ps.setNull(i++, Types.TIMESTAMP);
        ps.setInt(i++, r.staff_id);
        ps.setTimestamp(i++, Timestamp.valueOf(r.last_update != null ? r.last_update : java.time.LocalDateTime.now()));
    }
    @Override
    protected void bindUpdate(PreparedStatement ps, Rental r) throws SQLException {
        bindInsert(ps, r);
    }
    @Override
    protected Integer extractPk(ResultSet rs) throws SQLException { return rs.getInt(1); }
    @Override
    protected void setPk(PreparedStatement ps, int index, Integer id) throws SQLException { ps.setInt(index, id); }
}