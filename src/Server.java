
import handler.VillasHandler;
import handler.CustomersHandler;
import handler.VouchersHandler;
//import handler.voucher.*;
//import handler.villa.*;
//import handler.customer.*;


import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Handler utama
        server.createContext("/villas", new VillasHandler());
        server.createContext("/customers", new CustomersHandler());
        server.createContext("/vouchers", new VouchersHandler());

//        // Handler terpisah untuk masing-masing method voucher
//        server.createContext("/vouchers_post", new PostVoucherHandler());
//        server.createContext("/vouchers_put", new PutVoucherHandler());
//        server.createContext("/vouchers_delete", new DeleteVoucherHandler());
//        server.createContext("/vouchers_get", new GetVouchersHandler());
//
//        server.createContext("/villas_get", new GetVillasHandler());
//        server.createContext("/villas_post", new PostVillaHandler());
//        server.createContext("/villas_put", new PutVillaHandler());
//        server.createContext("/villas_delete", new DeleteVillaHandler());
//
//        server.createContext("/customers_get", new GetCustomersHandler());
//        server.createContext("/customers_post", new PostCustomerHandler());
//        server.createContext("/customers_put", new PutCustomerHandler());
//        server.createContext("/customers_delete", new DeleteCustomerHandler());

        server.setExecutor(null);
        server.start();

        System.out.printf("Server started and listening on port: %d%n", port);
    }
}
