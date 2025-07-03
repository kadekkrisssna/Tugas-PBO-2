package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.CustomerController;
import controller.BookingController;
import controller.ReviewController;
import model.Booking;
import model.Customer;
import model.Review;

import java.io.OutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomersHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> response = new HashMap<>();

        try {
            if (method.equals("GET")) {
                if (path.matches("/customers/?")) {
                    List<Customer> customers = CustomerController.getAllCustomers();
                    sendResponse(exchange, customers, "List of customers");

                } else if (path.matches("/customers/\\d+/?")) {
                    int id = Integer.parseInt(path.replaceAll("/customers/(\\d+)/?", "$1"));
                    Customer customer = CustomerController.getCustomerById(id);
                    if (customer != null) {
                        sendResponse(exchange, customer, "Customer detail");
                    } else {
                        sendError(exchange, 404, "Customer not found");
                    }

                } else if (path.matches("/customers/\\d+/bookings/?")) {
                    int customerId = Integer.parseInt(path.replaceAll("/customers/(\\d+)/bookings/?", "$1"));
                    List<Booking> bookings = BookingController.getBookingsByCustomer(customerId);
                    sendResponse(exchange, bookings, "Customer bookings");

                } else if (path.matches("/customers/\\d+/reviews/?")) {
                    int customerId = Integer.parseInt(path.replaceAll("/customers/(\\d+)/reviews/?", "$1"));
                    List<Review> reviews = ReviewController.getReviewsByCustomer(customerId);
                    sendResponse(exchange, reviews, "Customer reviews");

                } else {
                    sendError(exchange, 404, "Unknown route");
                }

            } else if (method.equals("POST")) {
                if (path.matches("/customers/?")) {
                    Customer customer = mapper.readValue(exchange.getRequestBody(), Customer.class);

                    if (!isValidCustomer(customer)) {
                        sendError(exchange, 400, "Missing or invalid customer data");
                        return;
                    }

                    boolean success = CustomerController.addCustomer(customer);
                    response.put("message", success ? "Customer registered successfully" : "Failed to register customer");
                    response.put("success", success);

                } else if (path.matches("/customers/\\d+/bookings/?")) {
                    int customerId = Integer.parseInt(path.replaceAll("/customers/(\\d+)/bookings/?", "$1"));
                    Booking booking = mapper.readValue(exchange.getRequestBody(), Booking.class);
                    booking.setCustomer(customerId);
                    boolean success = BookingController.addBooking(booking);
                    response.put("message", success ? "Booking created" : "Booking failed");
                    response.put("success", success);

                } else if (path.matches("/customers/\\d+/bookings/\\d+/reviews/?")) {
                    Review review = mapper.readValue(exchange.getRequestBody(), Review.class);
                    boolean success = ReviewController.addReview(review);
                    response.put("message", success ? "Review submitted" : "Review failed");
                    response.put("success", success);

                } else {
                    sendError(exchange, 404, "Unknown route");
                    return;
                }

            } else if (method.equals("PUT") && path.matches("/customers/\\d+/?")) {
                int id = Integer.parseInt(path.replaceAll("/customers/(\\d+)/?", "$1"));
                Customer existing = CustomerController.getCustomerById(id);
                if (existing == null) {
                    sendError(exchange, 404, "Customer not found");
                    return;
                }

                Customer customer = mapper.readValue(exchange.getRequestBody(), Customer.class);
                if (!isValidCustomer(customer)) {
                    sendError(exchange, 400, "Missing or invalid customer data");
                    return;
                }

                boolean success = CustomerController.updateCustomer(id, customer);
                response.put("message", success ? "Customer updated successfully" : "Failed to update customer");
                response.put("success", success);

            } else if (method.equals("DELETE") && path.matches("/customers/\\d+/?")) {
                int id = Integer.parseInt(path.replaceAll("/customers/(\\d+)/?", "$1"));
                Customer existing = CustomerController.getCustomerById(id);
                if (existing == null) {
                    sendError(exchange, 404, "Customer not found");
                    return;
                }

                boolean success = CustomerController.deleteCustomer(id);
                response.put("message", success ? "Customer deleted successfully" : "Failed to delete customer");
                response.put("success", success);

            } else {
                sendError(exchange, 404, "Unknown route");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendError(exchange, 500, "Internal server error: " + e.getMessage());
            return;
        }

        sendResponse(exchange, response, "Request successful");

    }

    private void sendResponse(HttpExchange exchange, Object data, String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("data", data);
        String json = mapper.writeValueAsString(response);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, json.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(json.getBytes());
        }
    }

    private void sendError(HttpExchange exchange, int statusCode, String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        String json = mapper.writeValueAsString(error);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, json.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(json.getBytes());
        }
    }

    private boolean isValidCustomer(Customer c) {
        if (c == null || c.getName() == null || c.getEmail() == null) return false;
        return isValidEmail(c.getEmail()) && (c.getPhone() == null || isValidPhone(c.getPhone()));
    }

    private boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email);
    }

    private boolean isValidPhone(String phone) {
        return Pattern.matches("^\\+?[0-9]{8,15}$", phone);
    }
}
