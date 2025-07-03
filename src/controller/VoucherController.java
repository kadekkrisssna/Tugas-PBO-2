package controller;

import database.DBConnection;
import model.Voucher;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherController {

    public static List<Voucher> getAllVouchers() throws IOException {
        List<Voucher> vouchers = new ArrayList<>();
        String sql = "SELECT * FROM vouchers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vouchers.add(new Voucher(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getDouble("discount"),
                        rs.getString("start_date"),
                        rs.getString("end_date")
                ));
            }
        } catch (Exception e) {
            throw new IOException("Failed to fetch vouchers: " + e.getMessage());
        }
        return vouchers;
    }

    public static Voucher getVoucherById(int id) throws IOException {
        String sql = "SELECT * FROM vouchers WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Voucher(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getDouble("discount"),
                        rs.getString("start_date"),
                        rs.getString("end_date")
                );
            }
            return null;
        } catch (Exception e) {
            throw new IOException("Failed to fetch voucher: " + e.getMessage());
        }
    }

    public static boolean addVoucher(Voucher voucher) throws IOException {
        String sql = "INSERT INTO vouchers (code, description, discount, start_date, end_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, voucher.getCode());
            stmt.setString(2, voucher.getDescription());
            stmt.setDouble(3, voucher.getDiscount());
            stmt.setString(4, voucher.getStartDate());
            stmt.setString(5, voucher.getEndDate());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to add voucher: " + e.getMessage());
        }
    }

    public static boolean updateVoucher(int id, Voucher voucher) throws IOException {
        String sql = "UPDATE vouchers SET code = ?, description = ?, discount = ?, start_date = ?, end_date = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, voucher.getCode());
            stmt.setString(2, voucher.getDescription());
            stmt.setDouble(3, voucher.getDiscount());
            stmt.setString(4, voucher.getStartDate());
            stmt.setString(5, voucher.getEndDate());
            stmt.setInt(6, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to update voucher: " + e.getMessage());
        }
    }

    public static boolean deleteVoucher(int id) throws IOException {
        String sql = "DELETE FROM vouchers WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to delete voucher: " + e.getMessage());
        }
    }
}
