package ru.springdb;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper implementation to map rows of a ResultSet to Book objects.
 */
public class BookRowMapper implements RowMapper<Book> {
    /**
     * Maps the current row of the given ResultSet to a Book object.
     *
     * @param rs the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the Book object for the current row
     * @throws SQLException if a SQLException is encountered getting column values
     */
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setGenre(rs.getString("genre"));
        book.setPages(rs.getInt("pages"));
        book.setPrice(rs.getInt("price"));

        return book;
    }
}
