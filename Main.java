import java.util.Scanner;

import DAO.UserDAO;
import Database.HearDropDB;
import Design.DesignUtils;
import menus.DonorMenu;
import menus.RecipientMenu;
import models.User;

public class Main {
    private static UserDAO userDAO = new UserDAO();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        HearDropDB.setupDatabase();

        while (true) {
            DesignUtils.printMainMenuHeader();
            System.out.println();
            System.out.println("(1) Register");
            System.out.println("(2) Login");
            System.out.println("(3) Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

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
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);

        if (userDAO.registerUser(user)) {
            System.out.println("User registered successfully!");

        } else {
            System.out.println("Registration failed. Username might already be taken.");
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

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
        int role = Integer.parseInt(scanner.nextLine());

        switch (role) {
            case 1:
                DonorMenu donorMenu = new DonorMenu(user.getId(), user.getUsername());
                donorMenu.show();
                break;
            case 2: 
                RecipientMenu recipientMenu = new RecipientMenu(user.getUsername());
                recipientMenu.show();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                DesignUtils.clearScreen(1000);
        }
    }
}
