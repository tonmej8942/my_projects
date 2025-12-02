package com.sakila.data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import com.sakila.models.Film;
public final class FilmRepo extends DataContext<Film, Integer> {
	public FilmRepo(ConnectionProvider provider) {
		super(provider, "film", "film_id", Arrays.asList("film_id", "title", "description", "release_year", "language_id", "rental_duration", "rental_rate", "length", "replacement_cost", "rating", "last_update"));
    }
    @Override
    protected Film mapRow(ResultSet rs) throws SQLException {
        Film f = new Film();
        f.film_id = rs.getInt("film_id");
        f.title = rs.getString("title");
        f.description = rs.getString("description");
        f.release_year = rs.getInt("release_year");
        f.language_id = rs.getInt("language_id");
        f.rental_duration = rs.getInt("rental_duration");
        f.rental_rate = rs.getDouble("rental_rate");
        f.length = rs.getInt("length");
        f.replacement_cost = rs.getDouble("replacement_cost");
        f.rating = rs.getString("rating");
        Timestamp lu = rs.getTimestamp("last_update");
        f.last_update = lu != null ? lu.toLocalDateTime() : null;
        return f;
    }
    @Override
    protected void bindInsert(PreparedStatement ps, Film f) throws SQLException {
        int i = 1;
        ps.setString(i++, f.title);
        ps.setString(i++, f.description);
        ps.setObject(i++, f.release_year);
        ps.setObject(i++, f.language_id);
        ps.setObject(i++, f.rental_duration);
        ps.setObject(i++, f.rental_rate);
        ps.setObject(i++, f.length);
        ps.setObject(i++, f.replacement_cost);
        ps.setString(i++, f.rating);
        ps.setTimestamp(i++, Timestamp.valueOf(f.last_update != null ? f.last_update : java.time.LocalDateTime.now()));
    }
    @Override
    protected void bindUpdate(PreparedStatement ps, Film f) throws SQLException {
        bindInsert(ps, f);
    }
    @Override
    protected Integer extractPk(ResultSet rs) throws SQLException {
        return rs.getInt(1);
    }
    @Override
    protected void setPk(PreparedStatement ps, int index, Integer id) throws SQLException {
        ps.setInt(index, id);
    }
}