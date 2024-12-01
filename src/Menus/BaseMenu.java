package Menus;

import java.util.Scanner;

public abstract class BaseMenu {
    protected static Scanner scanner = new Scanner(System.in);

    public abstract void displayMenu();

    public abstract void handleChoice(int choice);

    public void show() {
        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == getExitChoice()) {
                return;
            }

            handleChoice(choice);
        }
    }

    protected abstract int getExitChoice();
}
