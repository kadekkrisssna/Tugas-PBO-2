package model;

/**
 * Model untuk tabel 'bookings'
 * Mewakili 1 transaksi booking oleh customer untuk 1 room_type
 */
public class Booking {
    private int id;
    private int customer;
    private int roomType;
    private String checkinDate;
    private String checkoutDate;
    private int price;
    private Integer voucher; // bisa null
    private int finalPrice;
    private String paymentStatus;
    private int hasCheckedin;
    private int hasCheckedout;

    public Booking() {}

    public Booking(int id, int customer, int roomType, String checkinDate, String checkoutDate,
                   int price, Integer voucher, int finalPrice, String paymentStatus,
                   int hasCheckedin, int hasCheckedout) {
        this.id = id;
        this.customer = customer;
        this.roomType = roomType;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.price = price;
        this.voucher = voucher;
        this.finalPrice = finalPrice;
        this.paymentStatus = paymentStatus;
        this.hasCheckedin = hasCheckedin;
        this.hasCheckedout = hasCheckedout;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomer() { return customer; }
    public void setCustomer(int customer) { this.customer = customer; }

    public int getRoomType() { return roomType; }
    public void setRoomType(int roomType) { this.roomType = roomType; }

    public String getCheckinDate() { return checkinDate; }
    public void setCheckinDate(String checkinDate) { this.checkinDate = checkinDate; }

    public String getCheckoutDate() { return checkoutDate; }
    public void setCheckoutDate(String checkoutDate) { this.checkoutDate = checkoutDate; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public Integer getVoucher() { return voucher; }
    public void setVoucher(Integer voucher) { this.voucher = voucher; }

    public int getFinalPrice() { return finalPrice; }
    public void setFinalPrice(int finalPrice) { this.finalPrice = finalPrice; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public int getHasCheckedin() { return hasCheckedin; }
    public void setHasCheckedin(int hasCheckedin) { this.hasCheckedin = hasCheckedin; }

    public int getHasCheckedout() { return hasCheckedout; }
    public void setHasCheckedout(int hasCheckedout) { this.hasCheckedout = hasCheckedout; }
}
