package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.VoucherController;
import model.Voucher;

import java.io.OutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VouchersHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> response = new HashMap<>();

        try {
            if (method.equals("GET")) {
                if (path.matches("/vouchers/?")) {
                    List<Voucher> vouchers = VoucherController.getAllVouchers();
                    response.put("message", "List of vouchers");
                    response.put("data", vouchers);

                } else if (path.matches("/vouchers/\\d+/?")) {
                    int id = Integer.parseInt(path.replaceAll("/vouchers/(\\d+)/?", "$1"));
                    Voucher voucher = VoucherController.getVoucherById(id);
                    if (voucher != null) {
                        response.put("message", "Voucher detail");
                        response.put("data", voucher);
                    } else {
                        sendError(exchange, 404, "Voucher not found");
                        return;
                    }
                }

            } else if (method.equals("POST") && path.matches("/vouchers/?")) {
                Voucher voucher = mapper.readValue(exchange.getRequestBody(), Voucher.class);
                if (!isValidVoucher(voucher)) {
                    sendError(exchange, 400, "Invalid or incomplete voucher data");
                    return;
                }

                boolean success = VoucherController.addVoucher(voucher);
                if (!success) {
                    sendError(exchange, 500, "Failed to create voucher");
                    return;
                }
                response.put("message", "Voucher created");
                response.put("success", true);

            } else if (method.equals("PUT") && path.matches("/vouchers/\\d+/?")) {
                int id = Integer.parseInt(path.replaceAll("/vouchers/(\\d+)/?", "$1"));
                Voucher existing = VoucherController.getVoucherById(id);
                if (existing == null) {
                    sendError(exchange, 404, "Voucher not found");
                    return;
                }

                Voucher voucher = mapper.readValue(exchange.getRequestBody(), Voucher.class);
                if (!isValidVoucher(voucher)) {
                    sendError(exchange, 400, "Invalid or incomplete voucher data");
                    return;
                }

                boolean success = VoucherController.updateVoucher(id, voucher);
                if (!success) {
                    sendError(exchange, 500, "Failed to update voucher");
                    return;
                }
                response.put("message", "Voucher updated");
                response.put("success", true);

            } else if (method.equals("DELETE") && path.matches("/vouchers/\\d+/?")) {
                int id = Integer.parseInt(path.replaceAll("/vouchers/(\\d+)/?", "$1"));
                Voucher existing = VoucherController.getVoucherById(id);
                if (existing == null) {
                    sendError(exchange, 404, "Voucher not found");
                    return;
                }

                boolean success = VoucherController.deleteVoucher(id);
                if (!success) {
                    sendError(exchange, 500, "Failed to delete voucher");
                    return;
                }
                response.put("message", "Voucher deleted");
                response.put("success", true);

            } else {
                sendError(exchange, 404, "Unknown route");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendError(exchange, 500, "Internal server error: " + e.getMessage());
            return;
        }

        sendResponse(exchange, response);
    }

    private void sendResponse(HttpExchange exchange, Map<String, Object> data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
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

    private boolean isValidVoucher(Voucher v) {
        if (v == null || v.getCode() == null || v.getDescription() == null || v.getStartDate() == null
                || v.getEndDate() == null || v.getDiscount() <= 0.0) return false;

        // Validate date format (yyyy-MM-dd hh:mm:ss)
        return isValidDate(v.getStartDate()) && isValidDate(v.getEndDate());
    }

    private boolean isValidDate(String date) {
        try {
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
