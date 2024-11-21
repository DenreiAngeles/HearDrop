package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import Design.DesignUtils;

public class HearDropDB {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DBNAME = "HearDrop";
    private static String USER = "root";
    private static String PASSWORD = "dormantpig1";
    static Scanner scan = new Scanner(System.in);

    public static Connection getConnectionToServer() throws SQLException {
        String url = "jdbc:mysql://localhost:3306";
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    public static Connection getConnection() {
        try {
            return attemptConnection(URL+DBNAME, USER, PASSWORD); 
        } catch (SQLException e) {
            System.out.println("Error: Unable to establish a connection. Please check your credentials.");
        }
        return null;
    }

    private static Connection attemptConnection(String URL, String USER, String PASSWORD) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);  
        } catch (SQLException e) {
            System.out.println("Error: Unable to establish a database connection. Please check your credentials.");
            return connectionDetails();
        }
        return conn;
    }

    private static Connection connectionDetails() {
        while (true) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Please ensure the details of your MySQL (username & password) are correct.");
            System.out.println("----------------------------------------------------------------------------");
            System.out.print("Enter your MySQL Username (default: root):\t ");
            String username = scan.nextLine();
            
            System.out.print("Enter your MySQL Password:\t\t\t ");
            String password = scan.nextLine();

            USER = username;
            PASSWORD = password;

            try {
                return attemptConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Incorrect credentials. Please try again.");
            }
        }
    }

    public static void setupDatabase() {
        try (Connection conn = getConnectionToServer(); Statement stmt = conn.createStatement()) {
    
            String createDatabase = "CREATE DATABASE IF NOT EXISTS " + "HearDrop";
            System.out.println("Executing SQL: " + createDatabase);
            stmt.execute(createDatabase);
    
            String useDatabase = "USE HearDrop";
            System.out.println("Executing SQL: " + useDatabase);
            stmt.execute(useDatabase);
    
            String createUsersTable = "CREATE TABLE IF NOT EXISTS Users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password VARCHAR(50) NOT NULL)";
            
            String createDonationsTable = "CREATE TABLE IF NOT EXISTS Donations (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "donor_id INT, " +
                    "item_name VARCHAR(100), " +
                    "description TEXT, " +
                    "quantity INT, " +
                    "pickup_location VARCHAR(100), " +
                    "status VARCHAR(50) DEFAULT 'Available', " +
                    "recipient_username VARCHAR(50), " +
                    "pickup_datetime DATETIME, " +
                    "donor_username VARCHAR(50), " +
                    "FOREIGN KEY (donor_id) REFERENCES Users(id))";
    
            stmt.execute(createUsersTable);
            stmt.execute(createDonationsTable);
    
            System.out.println("=========================");
            System.out.println("Database setup completed!");
            System.out.println("=========================\n");
            System.out.print("Please Wait...");
            DesignUtils.clearScreen(2000);
    
        } catch (SQLException e) {
            System.out.println("Error: Failed to set up the database. Please check your database connection.");
        }
    }
}
