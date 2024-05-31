package ru.springdb;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

/**
 * Main class for running the Book Management application.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    final static int MENU_CREATE = 1;
    final static int MENU_DISPLAY = 2;
    final static int MENU_EDIT = 3;
    final static int MENU_DELETE = 4;
    final static int MENU_SEARCH = 5;
    final static int MENU_EXIT = 6;

    /**
     * Main method to run the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDao = context.getBean("bookDao", BookDao.class);

        int choice;

        do {
            printMenu();
            choice = getIntInput("Choose an option: ");

            switch (choice) {
                case MENU_CREATE:
                    createNewBook(bookDao);
                    break;
                case MENU_DISPLAY:
                    displayAllBooks(bookDao);
                    break;
                case MENU_EDIT:
                    editBookById(bookDao);
                    break;
                case MENU_DELETE:
                    deleteBookById(bookDao);
                    break;
                case MENU_SEARCH:
                    searchBooksByPrice(bookDao);
                    break;
                case MENU_EXIT:
                    context.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != MENU_EXIT);
    }

    /**
     * Prints the menu options.
     */
    private static void printMenu() {
        System.out.println("\n--- Book Management Menu ---");
        System.out.println("1. Create a new book");
        System.out.println("2. Display all books");
        System.out.println("3. Edit a book by identifier");
        System.out.println("4. Delete a book by identifier");
        System.out.println("5. Search for books whose price is lower than the entered amount");
        System.out.println("6. Exit");
    }

    /**
     * Creates a new book and inserts it into the database.
     *
     * @param bookDao the BookDao object
     */
    private static void createNewBook(BookDao bookDao) {
        int id;
        boolean isBookExist;
        do {
            id = getIntInput("Enter book ID: ");
            isBookExist = bookDao.isBookExist(id);
            if(isBookExist) System.out.println("Book with this id is exist!");
        } while (isBookExist);

        String title = getStringInput("Enter book title: ");
        String author = getStringInput("Enter book author: ");
        String genre = getStringInput("Enter book genre: ");
        int pages = getIntInput("Enter number of pages: ");
        int price = getIntInput("Enter book price: ");

        Book newBook = new Book(id, title, author, genre, pages, price);
        int result = bookDao.insert(newBook);
        if(result == 1) System.out.println("Book created successfully!");
        else System.out.println("Book wasn't created.");
    }

    /**
     * Displays all books in the database.
     *
     * @param bookDao the BookDao object
     */
    private static void displayAllBooks(BookDao bookDao) {
        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);
    }

    /**
     * Edits a book by its identifier.
     *
     * @param bookDao the BookDao object
     */
    private static void editBookById(BookDao bookDao) {
        int id = getIntInput("Enter book ID to edit: ");
        if(!bookDao.isBookExist(id)) {
            System.out.println("Book with this id is not exist!");
            return;
        }
        Book book = bookDao.findById(id);

        String title = getStringInput("Enter new title (leave blank to keep current): ");
        String author = getStringInput("Enter new author (leave blank to keep current): ");
        String genre = getStringInput("Enter new genre (leave blank to keep current): ");
        int pages = getIntInput("Enter new number of pages (enter 0 to keep current): ");
        int price = getIntInput("Enter new price (enter 0 to keep current): ");

        if (!title.isEmpty()) book.setTitle(title);
        if (!author.isEmpty()) book.setAuthor(author);
        if (!genre.isEmpty()) book.setGenre(genre);
        if (pages != 0) book.setPages(pages);
        if (price != 0) book.setPrice(price);

        int result = bookDao.update(book);
        if(result == 1) System.out.println("Book updated successfully!");
        else System.out.println("Book wasn't updated.");
    }

    /**
     * Deletes a book by its identifier.
     *
     * @param bookDao the BookDao object
     */
    private static void deleteBookById(BookDao bookDao) {
        int id = getIntInput("Enter book ID to delete: ");
        if(!bookDao.isBookExist(id)) {
            System.out.println("Book with this id is not exist!");
            return;
        }

        int result = bookDao.delete(id);
        if(result == 1) System.out.println("Book deleted successfully!");
        else System.out.println("Book wasn't deleted.");
    }

    /**
     * Searches for books whose price is lower than the specified amount.
     *
     * @param bookDao the BookDao object
     */
    private static void searchBooksByPrice(BookDao bookDao) {
        int price = getIntInput("Enter the maximum price: ");
        List<Book> books = bookDao.findByPriceLessThan(price);
        books.forEach(System.out::println);
    }

    /**
     * Gets integer input from the user with validation.
     *
     * @param prompt the prompt message to display
     * @return the inputted integer
     */
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if(value >= 0) return value;
                else System.out.println("Invalid input. Please enter a valid positive number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid positive number.");
            }
        }
    }

    /**
     * Gets string input from the user.
     *
     * @param prompt the prompt message to display
     * @return the inputted string
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
