package Service;

import java.util.Scanner;
import DAO.DonationDAO;
import models.Donation;
import utils.DesignUtils;
import utils.LogUtils;

public class DonorService {
    private DonationDAO donationDAO = new DonationDAO();

    public void donateItem(int donorId, String username) {
        Scanner scanner = new Scanner(System.in);

        try {
            DesignUtils.clearScreen(1000);
            System.out.println("\n--- Donate Item ---");
            System.out.print("Enter item name: ");
            String itemName = scanner.nextLine();

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            System.out.print("Enter quantity(must be a digit): ");
            int quantity = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter pickup location: ");
            String pickupLocation = scanner.nextLine();

            Donation donation = new Donation(0, donorId, itemName, description, quantity, pickupLocation, "Available", null, null, username);

            if (donationDAO.addDonation(donation)) {
                System.out.println("Donation added successfully!");
            } else {
                System.out.println("Failed to add donation. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Please enter a valid number.");
            LogUtils.logError(e);
        } catch (Exception e) {
            System.out.println("An error occurred while donating the item. Please try again.");
            LogUtils.logError(e);
        }
    }

    public void viewMyDonatedItems(int donorId) {
        Scanner scanner = new Scanner(System.in);

        try {
            DesignUtils.clearScreen(1000);
            System.out.println("\n--- My Donated Items ---");
            var donations = donationDAO.getDonationsByDonor(donorId);

            if (donations.isEmpty()) {
                System.out.println("No donations found.\n");
            } else {
                for (Donation donation : donations) {
                    System.out.println("ID: " + donation.getId());
                    System.out.println("Item Name: " + donation.getItemName());
                    System.out.println("Description: " + donation.getDescription());
                    System.out.println("Quantity: " + donation.getQuantity());
                    System.out.println("Pickup Location: " + donation.getPickupLocation());
                    if ("Available".equals(donation.getStatus())) {
                        System.out.println("Status: Available");
                    } else {
                        System.out.println("Status: Reserved by " + donation.getRecipientUsername() + 
                            " Pickup Date and Time: " + donation.getPickupDatetime());
                    }
                    System.out.println("---------------------------");
                }
            }
            System.out.print("\nPress Enter to Continue...");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An error occurred while viewing your donations. Please try again.");
            LogUtils.logError(e);
        }
    }

    public void editDonatedItem(int donorId) {
        Scanner scanner = new Scanner(System.in);

        try {
            DesignUtils.clearScreen(1000);
            System.out.println("\n--- Edit Donated Items ---");
            var donations = donationDAO.getDonationsByDonor(donorId);

            if (donations.isEmpty()) {
                System.out.println("No donations found.\n");
                return;
            }

            System.out.println("Select an item to edit (by ID):");
            for (Donation donation : donations) {
                if ("Available".equals(donation.getStatus())) {
                    System.out.println("ID: " + donation.getId() + " | Item Name: " + donation.getItemName());
                }
            }

            System.out.print("Enter ID of the item to edit: ");
            int id = Integer.parseInt(scanner.nextLine());

            Donation donation = donationDAO.getDonationById(id);
            if (donation == null || !"Available".equals(donation.getStatus())) {
                System.out.println("Item not found or is not editable.");
                return;
            }

            System.out.print("Enter new item name (leave blank to keep current): ");
            String itemName = scanner.nextLine();
            if (!itemName.isEmpty()) {
                donation.setItemName(itemName);
            }

            System.out.print("Enter new description (leave blank to keep current): ");
            String description = scanner.nextLine();
            if (!description.isEmpty()) {
                donation.setDescription(description);
            }

            System.out.print("Enter new quantity (leave blank to keep current): ");
            String quantityInput = scanner.nextLine();
            if (!quantityInput.isEmpty()) {
                donation.setQuantity(Integer.parseInt(quantityInput));
            }

            System.out.print("Enter new pickup location (leave blank to keep current): ");
            String pickupLocation = scanner.nextLine();
            if (!pickupLocation.isEmpty()) {
                donation.setPickupLocation(pickupLocation);
            }

            if (donationDAO.updateDonation(donation)) {
                System.out.println("Donation updated successfully!");
            } else {
                System.out.println("Failed to update donation. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers for quantity.");
            LogUtils.logError(e);
        } catch (Exception e) {
            System.out.println("An error occurred while editing the donated item. Please try again.");
            LogUtils.logError(e);
        }
    }

    public void removeDonatedItem(int donorId) {
        Scanner scanner = new Scanner(System.in);

        try {
            var donations = donationDAO.getDonationsByDonor(donorId);

            DesignUtils.clearScreen(1000);
            System.out.println("\n--- Remove Donated Item ---");
            if (donations.isEmpty()) {
                System.out.println("No donations found.\n");
            } else {
                for (Donation donation : donations) {
                    System.out.println("ID: " + donation.getId());
                    System.out.println("Item Name: " + donation.getItemName());
                    System.out.println("Description: " + donation.getDescription());
                    System.out.println("Quantity: " + donation.getQuantity());
                    System.out.println("Pickup Location: " + donation.getPickupLocation());
                    if ("Available".equals(donation.getStatus())) {
                        System.out.println("Status: Available");
                    } else {
                        System.out.println("Status: Reserved by " + donation.getRecipientUsername() + 
                            " Pickup Date and Time: " + donation.getPickupDatetime());
                    }
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

            if (donation.getDonorId() != donorId) {
                System.out.println("You cannot remove an item that you did not donate.");
                return;
            }

            if ("Reserved".equals(donation.getStatus())) {
                System.out.println("Item is reserved and cannot be removed.");
                return;
            }

            if (donationDAO.removeDonation(id)) {
                System.out.println("Donation removed successfully.");
            } else {
                System.out.println("Failed to remove donation.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid ID.");
            LogUtils.logError(e);
        } catch (Exception e) {
            System.out.println("An error occurred while removing the donated item. Please try again.");
            LogUtils.logError(e);
        }
    }
}
