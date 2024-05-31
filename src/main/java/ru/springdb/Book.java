package ru.springdb;

/**
 * Represents a book with various attributes such as id, title, author, genre, pages, and price.
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int pages;
    private int price;

    /**
     * Default constructor for creating an empty Book object.
     */
    public Book() {
        this.id = 0;
        this.title = "";
        this.author = "";
        this.genre = "";
        this.pages = 0;
        this.price = 0;
    }

    /**
     * Constructor for creating a Book object with specified attributes.
     *
     * @param id the unique identifier of the book
     * @param title the title of the book
     * @param author the author of the book
     * @param genre the genre of the book
     * @param pages the number of pages in the book
     * @param price the price of the book
     */
    public Book(Integer id, String title, String author, String genre, int pages, int price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
        this.price = price;
    }


    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getPages() {
        return pages;
    }

    public int getPrice() {
        return price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book: " +
                "id = " + id +
                ", title = '" + title + '\'' +
                ", author = '" + author + '\'' +
                ", genre = '" + genre + '\'' +
                ", pages = " + pages +
                ", price = " + price;
    }
}
