package menus;

import Design.DesignUtils;
import Service.RecipientService;

public class RecipientMenu extends BaseMenu {
    private RecipientService recipientService = new RecipientService();
    private String recipientUsername;

    public RecipientMenu(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    @Override
    public void displayMenu() {
        DesignUtils.printHeader("R E C I P I E N T", recipientUsername);
        System.out.println("\n--- Recipient Menu ---\n");
        System.out.println("1. View Available Items");
        System.out.println("2. Reserve Item");
        System.out.println("3. View My Reserved Items");
        System.out.println("4. Remove Reserved Item");
        System.out.println("5. Back to Main Menu");
    }

    @Override
    public void handleChoice(int choice) {
        switch (choice) {
            case 1: 
                recipientService.viewAvailableItems();
                break;
            case 2: 
                recipientService.reserveItem(recipientUsername);
                break;
            case 3: 
                recipientService.viewMyReservedItems(recipientUsername);
                break;
            case 4: 
                recipientService.removeReservedItem(recipientUsername);
                break;
            default: 
                System.out.println("Invalid choice. Please try again.");
        }
    }

    @Override
    protected int getExitChoice() {
        return 5;
    }
}
