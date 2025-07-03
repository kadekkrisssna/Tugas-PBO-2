package model;

public class RoomType {
    private int id;
    private int villa;
    private String name;
    private int quantity;
    private int capacity;
    private int price;
    private String bedSize;
    private int hasDesk;
    private int hasAc;
    private int hasTv;
    private int hasWifi;
    private int hasShower;
    private int hasHotwater;
    private int hasFridge;

    public RoomType() {}

    public RoomType(int id, int villa, String name, int quantity, int capacity, int price,
                    String bedSize, int hasDesk, int hasAc, int hasTv, int hasWifi,
                    int hasShower, int hasHotwater, int hasFridge) {
        this.id = id;
        this.villa = villa;
        this.name = name;
        this.quantity = quantity;
        this.capacity = capacity;
        this.price = price;
        this.bedSize = bedSize;
        this.hasDesk = hasDesk;
        this.hasAc = hasAc;
        this.hasTv = hasTv;
        this.hasWifi = hasWifi;
        this.hasShower = hasShower;
        this.hasHotwater = hasHotwater;
        this.hasFridge = hasFridge;
    }

    public int getId() { return id; }
    public int getVilla() { return villa; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getCapacity() { return capacity; }
    public int getPrice() { return price; }
    public String getBedSize() { return bedSize; }
    public int getHasDesk() { return hasDesk; }
    public int getHasAc() { return hasAc; }
    public int getHasTv() { return hasTv; }
    public int getHasWifi() { return hasWifi; }
    public int getHasShower() { return hasShower; }
    public int getHasHotwater() { return hasHotwater; }
    public int getHasFridge() { return hasFridge; }

    public void setId(int id) { this.id = id; }
    public void setVilla(int villa) { this.villa = villa; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setPrice(int price) { this.price = price; }
    public void setBedSize(String bedSize) { this.bedSize = bedSize; }
    public void setHasDesk(int hasDesk) { this.hasDesk = hasDesk; }
    public void setHasAc(int hasAc) { this.hasAc = hasAc; }
    public void setHasTv(int hasTv) { this.hasTv = hasTv; }
    public void setHasWifi(int hasWifi) { this.hasWifi = hasWifi; }
    public void setHasShower(int hasShower) { this.hasShower = hasShower; }
    public void setHasHotwater(int hasHotwater) { this.hasHotwater = hasHotwater; }
    public void setHasFridge(int hasFridge) { this.hasFridge = hasFridge; }
}
