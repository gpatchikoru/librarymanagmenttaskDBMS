/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

/**
 *
 * @author girishkumarpatchikoru
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookManager {
    private static final String INSERT_BOOK_SQL = "INSERT INTO books (title, author, genre) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_BOOKS_SQL = "SELECT * FROM books";
    private static final Logger LOGGER = Logger.getLogger(BookManager.class.getName());

    public void addBook(String title, String author, String genre) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }
        if (author == null || author.trim().isEmpty()) {
            System.out.println("Author cannot be empty.");
            return;
        }

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOK_SQL)) {

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, genre);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book added successfully.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding book: " + e.getMessage(), e);
            System.out.println("An error occurred while adding the book. Please try again.");
        }
    }

    public void listBooks() {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKS_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No books are available.");
                return;
            }
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");

                System.out.printf("ID: %d, Title: %s, Author: %s, Genre: %s%n", id, title, author, genre);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error listing books: " + e.getMessage(), e);
            System.out.println("An error occurred while listing books. Please try again.");
        }
    }
}
