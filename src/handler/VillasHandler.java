package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.VillaController;
import model.Villa;
import model.RoomType;
import model.Booking;
import model.Review;

import java.io.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VillasHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> response = new HashMap<>();

        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getRawQuery();

            switch (method) {
                case "GET":
                    if (path.matches("/villas/\\d+/rooms/?")) {
                        int villaId = extractId(path, "/villas/(\\d+)/rooms/?");
                        List<RoomType> rooms = VillaController.getRoomsByVilla(villaId);
                        sendResponse(exchange, rooms, "List of rooms in villa");
                        return;

                    } else if (path.matches("/villas/\\d+/bookings/?")) {
                        int villaId = extractId(path, "/villas/(\\d+)/bookings/?");
                        List<Booking> bookings = VillaController.getBookingsByVilla(villaId);
                        sendResponse(exchange, bookings, "Bookings for villa ID " + villaId);
                        return;

                    } else if (path.matches("/villas/\\d+/reviews/?")) {
                        int villaId = extractId(path, "/villas/(\\d+)/reviews/?");
                        List<Review> reviews = VillaController.getReviewsByVilla(villaId);
                        sendResponse(exchange, reviews, "Reviews for villa ID " + villaId);
                        return;

                    } else if (path.matches("/villas/\\d+/?")) {
                        int villaId = extractId(path, "/villas/(\\d+)/?");
                        Villa villa = VillaController.getVillaById(villaId);
                        if (villa != null) {
                            sendResponse(exchange, villa, "Villa detail");
                        } else {
                            sendError(exchange, 404, "Villa not found");
                        }
                        return;

                    } else if (path.matches("/villas/?")) {
                        if (query != null && query.contains("ci_date") && query.contains("co_date")) {
                            Map<String, String> params = parseQuery(query);
                            String ci = params.get("ci_date");
                            String co = params.get("co_date");
                            List<Villa> available = VillaController.getAvailableVillas(ci, co);
                            sendResponse(exchange, available, "Available villas");
                        } else {
                            List<Villa> villas = VillaController.getAllVillas();
                            sendResponse(exchange, villas, "List of all villas");
                        }
                        return;
                    }
                    break;

                case "POST":
                    if (path.matches("/villas/?")) {
                        Villa villa = mapper.readValue(exchange.getRequestBody(), Villa.class);
                        if (!isValidVilla(villa)) {
                            sendError(exchange, 400, "Invalid or incomplete villa data");
                            return;
                        }

                        boolean success = VillaController.addVilla(villa);
                        sendResponse(exchange, Map.of("success", success), success ? "Villa created successfully" : "Villa creation failed");
                        return;

                    } else if (path.matches("/villas/\\d+/rooms/?")) {
                        int villaId = extractId(path, "/villas/(\\d+)/rooms/?");
                        RoomType room = mapper.readValue(exchange.getRequestBody(), RoomType.class);
                        if (!isValidRoom(room)) {
                            sendError(exchange, 400, "Invalid or incomplete room data");
                            return;
                        }

                        boolean success = VillaController.addRoomToVilla(villaId, room);
                        sendResponse(exchange, Map.of("success", success), success ? "Room added successfully" : "Room addition failed");
                        return;
                    }
                    break;

                case "PUT":
                    if (path.matches("/villas/\\d+/?")) {
                        int id = extractId(path, "/villas/(\\d+)/?");
                        Villa villa = mapper.readValue(exchange.getRequestBody(), Villa.class);
                        if (!isValidVilla(villa)) {
                            sendError(exchange, 400, "Invalid or incomplete villa data");
                            return;
                        }

                        boolean success = VillaController.updateVilla(id, villa);
                        if (success) {
                            sendResponse(exchange, Map.of("success", true), "Villa updated successfully");
                        } else {
                            sendError(exchange, 404, "Villa not found");
                        }
                        return;

                    } else if (path.matches("/villas/\\d+/rooms/\\d+/?")) {
                        int roomId = extractId(path, "/villas/\\d+/rooms/(\\d+)/?");
                        RoomType room = mapper.readValue(exchange.getRequestBody(), RoomType.class);
                        if (!isValidRoom(room)) {
                            sendError(exchange, 400, "Invalid or incomplete room data");
                            return;
                        }

                        boolean success = VillaController.updateRoom(roomId, room);
                        if (success) {
                            sendResponse(exchange, Map.of("success", true), "Room updated successfully");
                        } else {
                            sendError(exchange, 404, "Room not found");
                        }
                        return;
                    }
                    break;

                case "DELETE":
                    if (path.matches("/villas/\\d+/?")) {
                        int id = extractId(path, "/villas/(\\d+)/?");
                        boolean success = VillaController.deleteVilla(id);
                        if (success) {
                            sendResponse(exchange, Map.of("success", true), "Villa deleted successfully");
                        } else {
                            sendError(exchange, 404, "Villa not found");
                        }
                        return;

                    } else if (path.matches("/villas/\\d+/rooms/\\d+/?")) {
                        int roomId = extractId(path, "/villas/\\d+/rooms/(\\d+)/?");
                        boolean success = VillaController.deleteRoom(roomId);
                        if (success) {
                            sendResponse(exchange, Map.of("success", true), "Room deleted successfully");
                        } else {
                            sendError(exchange, 404, "Room not found");
                        }
                        return;
                    }
                    break;
            }

            sendError(exchange, 404, "Endpoint not found");

        } catch (Exception e) {
            e.printStackTrace();
            sendError(exchange, 500, "Internal Server Error: " + e.getMessage());
        }
    }

    private int extractId(String path, String regex) {
        return Integer.parseInt(path.replaceAll(regex, "$1"));
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query != null) {
            for (String pair : query.split("&")) {
                String[] parts = pair.split("=");
                if (parts.length == 2) {
                    map.put(parts[0], parts[1]);
                }
            }
        }
        return map;
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

    private boolean isValidVilla(Villa v) {
        return v != null && v.getName() != null && v.getDescription() != null && v.getAddress() != null;
    }

    private boolean isValidRoom(RoomType r) {
        return r != null && r.getName() != null && r.getPrice() > 0;
    }
}
