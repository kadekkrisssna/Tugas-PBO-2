package controller;

import database.DBConnection;
import model.Customer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    public static List<Customer> getAllCustomers() throws IOException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        } catch (Exception e) {
            throw new IOException("Failed to fetch customers: " + e.getMessage());
        }
        return customers;
    }

    public static boolean addCustomer(Customer customer) throws IOException {
        String sql = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new IOException("Failed to add customer: " + e.getMessage());
        }
    }

    public static boolean updateCustomer(int id, Customer customer) throws IOException {
        String sql = "UPDATE customers SET name = ?, email = ?, phone = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setInt(4, id);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new IOException("Failed to update customer: " + e.getMessage());
        }
    }

    public static boolean deleteCustomer(int id) throws IOException {
        String sql = "DELETE FROM customers WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new IOException("Failed to delete customer: " + e.getMessage());
        }
    }

    public static Customer getCustomerById(int id) throws IOException {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new IOException("Failed to fetch customer: " + e.getMessage());
        }
    }
}
