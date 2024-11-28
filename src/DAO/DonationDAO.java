package DAO;

import java.sql.*;
import java.util.List;

import Models.Donation;

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

    public boolean addDonation(Donation donation) {
        String query = "INSERT INTO donations (donor_id, item_name, description, quantity, pickup_location, status, donor_username) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return add(query, donation.getDonorId(), donation.getItemName(), donation.getDescription(),
                   donation.getQuantity(), donation.getPickupLocation(), donation.getStatus(), donation.getDonorUsername());
    }

    public Donation getDonationById(int id) {
        String query = "SELECT * FROM donations WHERE id = ?";
        return getById(query, id);
    }

    public List<Donation> getDonationsByDonor(int donorId) {
        String query = "SELECT * FROM donations WHERE donor_id = ?";
        return getList(query, donorId);
    }

    public List<Donation> getAvailableDonations() {
        String query = "SELECT * FROM donations WHERE status = 'Available'";
        return getList(query);
    }

    public List<Donation> getDonationsByRecipient(String recipientUsername) {
        String query = "SELECT * FROM donations WHERE recipient_username = ?";
        return getList(query, recipientUsername);
    }

    public boolean updateDonation(Donation donation) {
        String query = "UPDATE donations SET item_name = ?, description = ?, quantity = ?, pickup_location = ?, " +
                       "status = ?, recipient_username = ?, pickup_datetime = ? WHERE id = ?";
        return update(query, donation.getItemName(), donation.getDescription(), donation.getQuantity(),
                      donation.getPickupLocation(), donation.getStatus(), donation.getRecipientUsername(),
                      donation.getPickupDatetime(), donation.getId());
    }

    public boolean removeDonation(int id) {
        String query = "DELETE FROM donations WHERE id = ?";
        return remove(query, id);
    }
}
