package com.sakila.data;
import com.sakila.models.Payment;
import java.sql.*;
import java.util.Arrays;
public final class PaymentRepo extends DataContext<Payment, Integer> {
    public PaymentRepo(ConnectionProvider provider) {
        super(provider, "payment", "payment_id",
              Arrays.asList("payment_id","customer_id","staff_id","rental_id","amount","payment_date"));
    }
    @Override
    protected Payment mapRow(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.payment_id = rs.getInt("payment_id");
        p.customer_id = rs.getInt("customer_id");
        p.staff_id = rs.getInt("staff_id");
        p.rental_id = rs.getInt("rental_id");
        p.amount = rs.getDouble("amount");
        Timestamp pd = rs.getTimestamp("payment_date");
        p.payment_date = pd != null ? pd.toLocalDateTime() : null;
        return p;
    }
    @Override
    protected void bindInsert(PreparedStatement ps, Payment p) throws SQLException {
        int i = 1;
        ps.setInt(i++, p.customer_id);
        ps.setInt(i++, p.staff_id);
        ps.setInt(i++, p.rental_id);
        ps.setDouble(i++, p.amount);
        ps.setTimestamp(i++, Timestamp.valueOf(p.payment_date != null ? p.payment_date : java.time.LocalDateTime.now()));
    }
    @Override
    protected void bindUpdate(PreparedStatement ps, Payment p) throws SQLException { bindInsert(ps, p); }
    @Override
    protected Integer extractPk(ResultSet rs) throws SQLException { return rs.getInt(1); }
    @Override
    protected void setPk(PreparedStatement ps, int index, Integer id) throws SQLException { ps.setInt(index, id); }
}