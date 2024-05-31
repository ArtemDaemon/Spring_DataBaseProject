package ru.springdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing books in the database.
 */
@Component
public class BookDao {
    private JdbcTemplate jdbcTemplate;

    /**
     * Sets the data source for the JdbcTemplate and initializes the books table.
     *
     * @param dataSource the data source to set
     */
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        initializeTable();
    }

    /**
     * Initializes the books table by creating it if it does not exist and truncating it.
     */
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

    /**
     * Retrieves all books from the database.
     *
     * @return a list of all books
     */
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BookRowMapper());
    }

    /**
     * Finds a book by its ID.
     *
     * @param id the ID of the book to find
     * @return the book with the specified ID, or null if not found
     */
    public Book findById(int id) {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE id = ?", new Object[]{id}, new BookRowMapper());
        return books.isEmpty() ? null : books.get(0);
    }

    /**
     * Checks if a book exists in the database by its ID.
     *
     * @param id the ID of the book to check
     * @return true if the book exists, false otherwise
     */
    public boolean isBookExist(int id) {
        Book searchingBook = findById(id);
        return searchingBook != null;
    }

    /**
     * Inserts a new book into the database.
     *
     * @param book the book to insert
     * @return the number of rows affected
     */
    public int insert(Book book) {
        return jdbcTemplate.update("INSERT INTO books (id, title, author, genre, pages, price) VALUES (?, ?, ?, ?, ?, ?)",
                book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getPages(), book.getPrice());
    }

    /**
     * Updates an existing book in the database.
     *
     * @param book the book with updated information
     * @return the number of rows affected
     */
    public int update(Book book) {
        return jdbcTemplate.update("UPDATE books SET title = ?, author = ?, genre = ?, pages = ?, price = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getGenre(), book.getPages(), book.getPrice(), book.getId());
    }

    /**
     * Deletes a book from the database by its ID.
     *
     * @param id the ID of the book to delete
     * @return the number of rows affected
     */
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    /**
     * Finds books whose price is less than the specified value.
     *
     * @param price the price to compare
     * @return a list of books with a price less than the specified value
     */
    public List<Book> findByPriceLessThan(int price) {
        return jdbcTemplate.query("SELECT * FROM books WHERE price < ?", new Object[]{price}, new BeanPropertyRowMapper<>(Book.class));
    }
}
