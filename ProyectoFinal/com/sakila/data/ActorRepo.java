package com.sakila.data;
import com.sakila.models.Actor;
import java.sql.*;
import java.util.Arrays;
public final class ActorRepo extends DataContext<Actor, Integer> {
    public ActorRepo(ConnectionProvider provider) {
        super(provider, "actor", "actor_id", Arrays.asList("actor_id","first_name","last_name","last_update"));
    }
    @Override
    protected Actor mapRow(ResultSet rs) throws SQLException {
        Actor a = new Actor();
        a.actor_id = rs.getInt("actor_id");
        a.first_name = rs.getString("first_name");
        a.last_name = rs.getString("last_name");
        Timestamp lu = rs.getTimestamp("last_update");
        a.last_update = lu != null ? lu.toLocalDateTime() : null;
        return a;
    }
    @Override
    protected void bindInsert(PreparedStatement ps, Actor a) throws SQLException {
        int i = 1;
        ps.setString(i++, a.first_name);
        ps.setString(i++, a.last_name);
        ps.setTimestamp(i++, Timestamp.valueOf(a.last_update != null ? a.last_update : java.time.LocalDateTime.now()));
    }
    @Override
    protected void bindUpdate(PreparedStatement ps, Actor a) throws SQLException {
        bindInsert(ps, a);
    }
    @Override
    protected Integer extractPk(ResultSet rs) throws SQLException { return rs.getInt(1); }
    @Override
    protected void setPk(PreparedStatement ps, int index, Integer id) throws SQLException { ps.setInt(index, id); }
}