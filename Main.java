import java.util.Scanner;

import DAO.UserDAO;
import Database.HearDropDB;
import utils.DesignUtils;
import menus.*;
import models.User;
import utils.LogUtils;

public class Main {
    private static UserDAO userDAO = new UserDAO();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            HearDropDB.setupDatabase();
        } catch (Exception e) {
            System.out.println("Failed to set up the database. Please contact the system administrator.");
            LogUtils.logError(e);
            return;
        }

        while (true) {
            try {
                DesignUtils.printMainMenuHeader();
                System.out.println();
                System.out.println("(1) Register");
                System.out.println("(2) Login");
                System.out.println("(3) Exit");
                System.out.print("Enter your choice: ");

                String choiceInput = scanner.nextLine();
                int choice;

                try {
                    choice = Integer.parseInt(choiceInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        registerUser();
                        break;
                    case 2:
                        loginUser();
                        break;
                    case 3:
                        System.out.println("Thank you for using HearDrop!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                LogUtils.logError(e);
            }
        }
    }

    private static void registerUser() {
        try {
            System.out.println("\033[3mNote: Username must be 1 to 15 characters long.\033[0m");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            while (username.isEmpty() || username.length() > 15) {
                System.out.println("Invalid username. It must be between 1 and 15 characters.");
                System.out.print("Enter username: ");
                username = scanner.nextLine();
            }

            System.out.println("\033[3mNote: Password must be atleast 6 characters long.\033[0m");
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            while (password.isEmpty() || password.length() < 6) {
                System.out.println("Invalid password. It must be at least 6 characters.");
                System.out.print("Enter password: ");
                password = scanner.nextLine();
            }

            User user = new User(username, password);

            if (userDAO.registerUser(user)) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Registration failed. Username might already be taken.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while registering. Please try again.");
            LogUtils.logError(e);
        }
    }

    private static void loginUser() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            while (username.isEmpty()) {
                System.out.println("Username cannot be empty. Please enter your username.");
                System.out.print("Enter username: ");
                username = scanner.nextLine();
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            while (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please enter your password.");
                System.out.print("Enter password: ");
                password = scanner.nextLine();
            }

            User user = userDAO.loginUser(username, password);
            if (user == null) {
                System.out.println("Invalid credentials. Please try again.");
                return;
            }

            System.out.println("Login successful!");
            DesignUtils.clearScreen(1000);
            System.out.println("------------------------");
            System.out.println("Please Choose your Role:");
            System.out.println("------------------------\n");
            System.out.println("1. Donor");
            System.out.println("2. Recipient");

            System.out.print("Enter your role: ");
            String roleInput = scanner.nextLine();
            int role;

            try {
                role = Integer.parseInt(roleInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Returning to main menu.");
                return;
            }

            switch (role) {
                case 1:
                    BaseMenu donorMenu = new DonorMenu(user.getId(), user.getUsername());
                    donorMenu.show();
                    break;
                case 2:
                    BaseMenu recipientMenu = new RecipientMenu(user.getUsername());
                    recipientMenu.show();
                    break;
                default:
                    System.out.println("Invalid choice. Returning to main menu.");
                    DesignUtils.clearScreen(1000);
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login. Please try again.");
            LogUtils.logError(e);
        }
    }
}
