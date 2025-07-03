package controller;

import database.DBConnection;
import model.Villa;
import model.RoomType;
import model.Booking;
import model.Review;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VillaController {

    // ========== VILLA ==========

    public static List<Villa> getAllVillas() throws IOException {
        List<Villa> villas = new ArrayList<>();
        String sql = "SELECT * FROM villas";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Villa villa = new Villa(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("address")
                );
                villas.add(villa);
            }
        } catch (Exception e) {
            throw new IOException("Failed to fetch villas: " + e.getMessage());
        }

        return villas;
    }

    public static Villa getVillaById(int id) throws IOException {
        String sql = "SELECT * FROM villas WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Villa(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("address")
                );
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new IOException("Failed to fetch villa by ID: " + e.getMessage());
        }
    }

    public static boolean addVilla(Villa villa) throws IOException {
        String sql = "INSERT INTO villas (name, description, address) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, villa.getName());
            pstmt.setString(2, villa.getDescription());
            pstmt.setString(3, villa.getAddress());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to add villa: " + e.getMessage());
        }
    }

    public static boolean updateVilla(int id, Villa villa) throws IOException {
        String sql = "UPDATE villas SET name = ?, description = ?, address = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, villa.getName());
            pstmt.setString(2, villa.getDescription());
            pstmt.setString(3, villa.getAddress());
            pstmt.setInt(4, id);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to update villa: " + e.getMessage());
        }
    }

    public static boolean deleteVilla(int id) throws IOException {
        String sql = "DELETE FROM villas WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to delete villa: " + e.getMessage());
        }
    }

    // ========== ROOM ==========

    public static List<RoomType> getRoomsByVilla(int villaId) throws IOException {
        List<RoomType> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room_types WHERE villa = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, villaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RoomType room = new RoomType(
                        rs.getInt("id"),
                        rs.getInt("villa"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getInt("capacity"),
                        rs.getInt("price"),
                        rs.getString("bed_size"),
                        rs.getInt("has_desk"),
                        rs.getInt("has_ac"),
                        rs.getInt("has_tv"),
                        rs.getInt("has_wifi"),
                        rs.getInt("has_shower"),
                        rs.getInt("has_hotwater"),
                        rs.getInt("has_fridge")
                );
                rooms.add(room);
            }
        } catch (Exception e) {
            throw new IOException("Failed to fetch rooms: " + e.getMessage());
        }

        return rooms;
    }

    public static boolean addRoomToVilla(int villaId, RoomType room) throws IOException {
        String sql = "INSERT INTO room_types (villa, name, quantity, capacity, price, bed_size, has_desk, has_ac, has_tv, has_wifi, has_shower, has_hotwater, has_fridge) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, villaId);
            pstmt.setString(2, room.getName());
            pstmt.setInt(3, room.getQuantity());
            pstmt.setInt(4, room.getCapacity());
            pstmt.setInt(5, room.getPrice());
            pstmt.setString(6, room.getBedSize());
            pstmt.setInt(7, room.getHasDesk());
            pstmt.setInt(8, room.getHasAc());
            pstmt.setInt(9, room.getHasTv());
            pstmt.setInt(10, room.getHasWifi());
            pstmt.setInt(11, room.getHasShower());
            pstmt.setInt(12, room.getHasHotwater());
            pstmt.setInt(13, room.getHasFridge());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to add room: " + e.getMessage());
        }
    }

    public static boolean updateRoom(int roomId, RoomType room) throws IOException {
        String sql = "UPDATE room_types SET name = ?, quantity = ?, capacity = ?, price = ?, bed_size = ?, has_desk = ?, has_ac = ?, has_tv = ?, has_wifi = ?, has_shower = ?, has_hotwater = ?, has_fridge = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, room.getName());
            pstmt.setInt(2, room.getQuantity());
            pstmt.setInt(3, room.getCapacity());
            pstmt.setInt(4, room.getPrice());
            pstmt.setString(5, room.getBedSize());
            pstmt.setInt(6, room.getHasDesk());
            pstmt.setInt(7, room.getHasAc());
            pstmt.setInt(8, room.getHasTv());
            pstmt.setInt(9, room.getHasWifi());
            pstmt.setInt(10, room.getHasShower());
            pstmt.setInt(11, room.getHasHotwater());
            pstmt.setInt(12, room.getHasFridge());
            pstmt.setInt(13, roomId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to update room: " + e.getMessage());
        }
    }

    public static boolean deleteRoom(int roomId) throws IOException {
        String sql = "DELETE FROM room_types WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to delete room: " + e.getMessage());
        }
    }

    // ========== BOOKING ==========

    public static List<Booking> getBookingsByVilla(int villaId) throws IOException {
        String sql = "SELECT b.* FROM bookings b " +
                "JOIN room_types r ON b.room_type = r.id " +
                "WHERE r.villa = ?";

        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, villaId);
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
            throw new IOException("Failed to get bookings by villa: " + e.getMessage());
        }
        return bookings;
    }

    // ========== REVIEW ==========

    public static List<Review> getReviewsByVilla(int villaId) throws IOException {
        String sql = "SELECT r.* FROM reviews r " +
                "JOIN bookings b ON r.booking = b.id " +
                "JOIN room_types rt ON b.room_type = rt.id " +
                "WHERE rt.villa = ?";

        List<Review> reviews = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, villaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setBooking(rs.getInt("booking"));
                review.setStar(rs.getInt("star"));
                review.setTitle(rs.getString("title"));
                review.setContent(rs.getString("content"));
                reviews.add(review);
            }
        } catch (Exception e) {
            throw new IOException("Failed to get reviews by villa: " + e.getMessage());
        }
        return reviews;
    }

    // ========== AVAILABILITY ==========

    public static List<Villa> getAvailableVillas(String ciDate, String coDate) throws IOException {
        String sql = """
            SELECT DISTINCT v.*
            FROM villas v
            JOIN room_types rt ON v.id = rt.villa
            WHERE rt.id NOT IN (
                SELECT b.room_type FROM bookings b
                WHERE NOT (
                    b.checkout_date <= ? OR b.checkin_date >= ?
                )
            )
        """;

        List<Villa> villas = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ciDate);
            stmt.setString(2, coDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                villas.add(new Villa(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("address")
                ));
            }
        } catch (Exception e) {
            throw new IOException("Failed to get available villas: " + e.getMessage());
        }

        return villas;
    }
}
