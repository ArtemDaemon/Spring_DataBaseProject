package ru.springdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class BookDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        initializeTable();
    }

    public void initializeTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY, " +
                "title VARCHAR(255), " +
                "author VARCHAR(255), " +
                "genre VARCHAR(255), " +
                "pages INTEGER, " +
                "price INTEGER)");
        jdbcTemplate.execute("TRUNCATE books");
    }

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BookRowMapper());
    }

    public Book findById(int id) {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE id = ?", new Object[]{id}, new BookRowMapper());
        return books.isEmpty() ? null : books.get(0);
    }

    public boolean isBookExist(int id) {
        Book searchingBook = findById(id);
        return searchingBook != null;
    }

    public int insert(Book book) {
        return jdbcTemplate.update("INSERT INTO books (id, title, author, genre, pages, price) VALUES (?, ?, ?, ?, ?, ?)",
                book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getPages(), book.getPrice());
    }

    public int update(Book book) {
        return jdbcTemplate.update("UPDATE books SET title = ?, author = ?, genre = ?, pages = ?, price = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getGenre(), book.getPages(), book.getPrice(), book.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    public List<Book> findByPriceLessThan(int price) {
        return jdbcTemplate.query("SELECT * FROM books WHERE price < ?", new Object[]{price}, new BeanPropertyRowMapper<>(Book.class));
    }
}
