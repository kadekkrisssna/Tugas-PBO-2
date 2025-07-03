package controller;

import database.DBConnection;
import model.Booking;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingController {

    public static List<Booking> getBookingsByCustomer(int customerId) throws IOException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE customer = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setCustomer(rs.getInt("customer"));
                booking.setRoomType(rs.getInt("room_type"));
                booking.setCheckinDate(rs.getString("checkin_date"));
                booking.setCheckoutDate(rs.getString("checkout_date"));
                booking.setPrice(rs.getInt("price"));
                booking.setVoucher(rs.getObject("voucher") != null ? rs.getInt("voucher") : null);
                booking.setFinalPrice(rs.getInt("final_price"));
                booking.setPaymentStatus(rs.getString("payment_status"));
                booking.setHasCheckedin(rs.getInt("has_checkedin"));
                booking.setHasCheckedout(rs.getInt("has_checkedout"));
                bookings.add(booking);
            }
        } catch (Exception e) {
            throw new IOException("Failed to get bookings: " + e.getMessage());
        }

        return bookings;
    }

    public static boolean addBooking(Booking booking) throws IOException {
        String sql = "INSERT INTO bookings (customer, room_type, checkin_date, checkout_date, price, voucher, final_price, payment_status, has_checkedin, has_checkedout) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getCustomer());
            stmt.setInt(2, booking.getRoomType());
            stmt.setString(3, booking.getCheckinDate());
            stmt.setString(4, booking.getCheckoutDate());
            stmt.setInt(5, booking.getPrice());

            if (booking.getVoucher() != null)
                stmt.setInt(6, booking.getVoucher());
            else
                stmt.setNull(6, Types.INTEGER);

            stmt.setInt(7, booking.getFinalPrice());
            stmt.setString(8, booking.getPaymentStatus());
            stmt.setInt(9, booking.getHasCheckedin());
            stmt.setInt(10, booking.getHasCheckedout());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to add booking: " + e.getMessage());
        }
    }
}
