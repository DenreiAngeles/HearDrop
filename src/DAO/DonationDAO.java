package DAO;

import models.Donation;
import java.sql.*;
import java.util.List;

public class DonationDAO extends BaseDAO<Donation> {

    @Override
    protected Donation mapResultSetToObject(ResultSet rs) throws SQLException {
        return new Donation(
            rs.getInt("id"),
            rs.getInt("donor_id"),
            rs.getString("item_name"),
            rs.getString("description"),
            rs.getInt("quantity"),
            rs.getString("pickup_location"),
            rs.getString("status"),
            rs.getString("recipient_username"),
            rs.getString("pickup_datetime"),
            rs.getString("donor_username")
        );
    }

    // Method to add a donation
    public boolean addDonation(Donation donation) {
        String query = "INSERT INTO donations (donor_id, item_name, description, quantity, pickup_location, status, donor_username) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return add(query, donation.getDonorId(), donation.getItemName(), donation.getDescription(),
                   donation.getQuantity(), donation.getPickupLocation(), donation.getStatus(), donation.getDonorUsername());
    }

    // Method to get a donation by ID
    public Donation getDonationById(int id) {
        String query = "SELECT * FROM donations WHERE id = ?";
        return getById(query, id);
    }

    // Method to get donations by donor ID
    public List<Donation> getDonationsByDonor(int donorId) {
        String query = "SELECT * FROM donations WHERE donor_id = ?";
        return getList(query, donorId);
    }

    // Method to get available donations
    public List<Donation> getAvailableDonations() {
        String query = "SELECT * FROM donations WHERE status = 'Available'";
        return getList(query);
    }

    // Method to get donations reserved by a recipient
    public List<Donation> getDonationsByRecipient(String recipientUsername) {
        String query = "SELECT * FROM donations WHERE recipient_username = ?";
        return getList(query, recipientUsername);
    }

    // Method to update a donation
    public boolean updateDonation(Donation donation) {
        String query = "UPDATE donations SET item_name = ?, description = ?, quantity = ?, pickup_location = ?, " +
                       "status = ?, recipient_username = ?, pickup_datetime = ? WHERE id = ?";
        return update(query, donation.getItemName(), donation.getDescription(), donation.getQuantity(),
                      donation.getPickupLocation(), donation.getStatus(), donation.getRecipientUsername(),
                      donation.getPickupDatetime(), donation.getId());
    }

    // Method to remove a donation
    public boolean removeDonation(int id) {
        String query = "DELETE FROM donations WHERE id = ?";
        return remove(query, id);
    }
}
