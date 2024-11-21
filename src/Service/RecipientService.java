package Service;

import java.util.List;
import java.util.Scanner;
import DAO.DonationDAO;
import models.Donation;
import Design.DesignUtils;

public class RecipientService {
    private DonationDAO donationDAO = new DonationDAO();


    public void viewAvailableItems() {
        Scanner scanner = new Scanner(System.in);

        DesignUtils.clearScreen(1000);
        System.out.println("\n--- View Available Items ---");
        List<Donation> donations = donationDAO.getAvailableDonations();

        if (donations.isEmpty()) {
            System.out.println("No available items.");
        } else {
            for (Donation donation : donations) {
                System.out.println("ID: " + donation.getId());
                System.out.println("Item Name: " + donation.getItemName());
                System.out.println("Description: " + donation.getDescription());
                System.out.println("Quantity: " + donation.getQuantity());
                System.out.println("Pickup Location: " + donation.getPickupLocation());
                System.out.println("Donor: " + donation.getDonorUsername()); 
                System.out.println("---------------------------");
            }
        }
        System.out.print("Press Enter to Continue...");
        scanner.nextLine();
    }


    public void reserveItem(String recipientUsername) {
        Scanner scanner = new Scanner(System.in);

        DesignUtils.clearScreen(1000);
        System.out.println("\n--- Reserve Item ---");
        List<Donation> donations = donationDAO.getAvailableDonations();
        if (donations.isEmpty()) {
            System.out.println("No available items.");
        } else {
            for (Donation donation : donations) {
                System.out.println("ID: " + donation.getId());
                System.out.println("Item Name: " + donation.getItemName());
                System.out.println("Description: " + donation.getDescription());
                System.out.println("Quantity: " + donation.getQuantity());
                System.out.println("Pickup Location: " + donation.getPickupLocation());
                System.out.println("Donor: " + donation.getDonorUsername());
                System.out.println("---------------------------");
            }
        }

        System.out.print("Enter ID of the item to reserve: ");
        int id = Integer.parseInt(scanner.nextLine());

        Donation donation = donationDAO.getDonationById(id);
        if (donation == null || !"Available".equals(donation.getStatus())) {
            System.out.println("Item not available for reservation.");
            return;
        }

        System.out.print("Enter pickup date (yyyy-mm-dd): ");
        String pickupDate = scanner.nextLine();

        System.out.print("Enter the pickup time (HH:mm):");
        String pickupTime = scanner.nextLine();

        String fullDateTime = pickupDate + " " + pickupTime + ":00";

        donation.setStatus("Reserved");
        donation.setRecipientUsername(recipientUsername);
        donation.setPickupDatetime(fullDateTime);

        if (donationDAO.updateDonation(donation)) {
            System.out.println("Item reserved successfully!");
        } else {
            System.out.println("Failed to reserve item. Please try again.");
        }
    }


    public void viewMyReservedItems(String recipientUsername) {
        Scanner scanner = new Scanner(System.in);

        DesignUtils.clearScreen(1000);
        System.out.println("\n--- My Reserved Items ---");
        var donations = donationDAO.getDonationsByRecipient(recipientUsername);

        if (donations.isEmpty()) {
            System.out.println("No reserved items found.");
            System.out.print("Press Enter to Continue...");
            scanner.nextLine();
        } else {
            for (Donation donation : donations) {
                System.out.println("ID: " + donation.getId());
                System.out.println("Item Name: " + donation.getItemName());
                System.out.println("Description: " + donation.getDescription());
                System.out.println("Quantity: " + donation.getQuantity());
                System.out.println("Pickup Location: " + donation.getPickupLocation());
                System.out.println("Pickup Date and Time: " + donation.getPickupDatetime());
                System.out.println("Donor: " + donation.getDonorUsername());
                System.out.println("---------------------------");
            }
            System.out.print("Press Enter to Continue...");
            scanner.nextLine();
        }
    }

    public void removeReservedItem(String recipientUsername) {
        Scanner scanner = new Scanner(System.in);

        DesignUtils.clearScreen(1000);
        System.out.println("\n--- Remove Reserved Item ---");
        var donations = donationDAO.getDonationsByRecipient(recipientUsername);

        if (donations.isEmpty()) {
            System.out.println("No reserved items found.");
            System.out.print("Press Enter to Continue...");
            scanner.nextLine();
        } else {
            for (Donation donation : donations) {
                System.out.println("ID: " + donation.getId());
                System.out.println("Item Name: " + donation.getItemName());
                System.out.println("Description: " + donation.getDescription());
                System.out.println("Quantity: " + donation.getQuantity());
                System.out.println("Pickup Location: " + donation.getPickupLocation());
                System.out.println("Pickup Date and Time: " + donation.getPickupDatetime());
                System.out.println("Donor: " + donation.getDonorUsername());
                System.out.println("---------------------------");
            }
        }

        System.out.print("Enter the ID of the item to remove: ");
        int id = Integer.parseInt(scanner.nextLine());

        Donation donation = donationDAO.getDonationById(id);
        if (donation == null) {
            System.out.println("Item not found.");
            return;
        }

        if (!recipientUsername.equals(donation.getRecipientUsername())) {
            System.out.println("You cannot remove an item that you did not reserve.");
            return;
        }

        donation.setStatus("Available");
        donation.setRecipientUsername(null);
        donation.setPickupDatetime(null);     

        if (donationDAO.updateDonation(donation)) {
            System.out.println("Reserved item removed successfully.");
        } else {
            System.out.println("Failed to remove reserved item.");
        }
    }
}
