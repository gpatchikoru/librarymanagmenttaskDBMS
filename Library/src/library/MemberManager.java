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

public class MemberManager {
    private static final String INSERT_MEMBER_SQL = "INSERT INTO members (name, email) VALUES (?, ?)";
    private static final String SELECT_ALL_MEMBERS_SQL = "SELECT * FROM members";
    private static final Logger LOGGER = Logger.getLogger(MemberManager.class.getName());

    public void addMember(String name, String email) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            System.out.println("Invalid email address.");
            return;
        }

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_MEMBER_SQL)) {

            statement.setString(1, name);
            statement.setString(2, email);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Member added successfully.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding member: " + e.getMessage(), e);
            System.out.println("An error occurred while adding the member. Please try again.");
        }
    }

    public void listMembers() {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_MEMBERS_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No members are registered.");
                return;
            }
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                System.out.printf("ID: %d, Name: %s, Email: %s%n", id, name, email);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error listing members: " + e.getMessage(), e);
            System.out.println("An error occurred while listing members. Please try again.");
        }
    }
}
