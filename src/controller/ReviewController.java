package controller;

import database.DBConnection;
import model.Review;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewController {

    public static List<Review> getReviewsByCustomer(int customerId) throws IOException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.booking, r.star, r.title, r.content FROM reviews r JOIN bookings b ON r.booking = b.id WHERE b.customer = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
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
            throw new IOException("Failed to get reviews: " + e.getMessage());
        }

        return reviews;
    }

    public static boolean addReview(Review review) throws IOException {
        String sql = "INSERT INTO reviews (booking, star, title, content) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getBooking());
            stmt.setInt(2, review.getStar());
            stmt.setString(3, review.getTitle());
            stmt.setString(4, review.getContent());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new IOException("Failed to add review: " + e.getMessage());
        }
    }
}
