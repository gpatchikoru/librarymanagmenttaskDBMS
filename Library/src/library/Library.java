/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package library;

/**
 *
 * @author girishkumarpatchikoru
 */

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Library {
    private static final Logger LOGGER = Logger.getLogger(Library.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookManager bookManager = new BookManager();
        MemberManager memberManager = new MemberManager();

        while (true) {
            try {
                System.out.println("Library Management System");
                System.out.println("1. Add Book");
                System.out.println("2. List Books");
                System.out.println("3. Add Member");
                System.out.println("4. List Members");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        handleAddBook(scanner, bookManager);
                        break;
                    case 2:
                        bookManager.listBooks();
                        break;
                    case 3:
                        handleAddMember(scanner, memberManager);
                        break;
                    case 4:
                        memberManager.listMembers();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (InputMismatchException e) {
                LOGGER.log(Level.SEVERE, "Invalid input. Please enter a number.", e);
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static void handleAddBook(Scanner scanner, BookManager bookManager) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        if (author.isEmpty()) {
            System.out.println("Author cannot be empty.");
            return;
        }

        System.out.print("Enter book genre: ");
        String genre = scanner.nextLine();
        bookManager.addBook(title, author, genre);
    }

    private static void handleAddMember(Scanner scanner, MemberManager memberManager) {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        if (email.isEmpty() || !email.contains("@")) {
            System.out.println("Invalid email address.");
            return;
        }

        memberManager.addMember(name, email);
    }
}
