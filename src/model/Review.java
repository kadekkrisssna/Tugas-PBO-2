package model;

public class Review {
    private int booking;
    private int star;
    private String title;
    private String content;

    public Review() {}

    public Review(int booking, int star, String title, String content) {
        this.booking = booking;
        this.star = star;
        this.title = title;
        this.content = content;
    }

    public int getBooking() {
        return booking;
    }

    public void setBooking(int booking) {
        this.booking = booking;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
