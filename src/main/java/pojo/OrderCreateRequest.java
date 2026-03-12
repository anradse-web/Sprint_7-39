package pojo;

import java.util.List;


public class OrderCreateRequest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    // Не обязательное поле
    private List<String> color;

    public OrderCreateRequest() {}

    public OrderCreateRequest(String firstName, String lastName, String address,
                              String metroStation, String phone, int rentTime,
                              String deliveryDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
    }

    public OrderCreateRequest withColors(List<String> colors) {
        OrderCreateRequest copy = new OrderCreateRequest();
        copy.setFirstName(this.firstName);
        copy.setLastName(this.lastName);
        copy.setAddress(this.address);
        copy.setMetroStation(this.metroStation);
        copy.setPhone(this.phone);
        copy.setRentTime(this.rentTime);
        copy.setDeliveryDate(this.deliveryDate);
        copy.setComment(this.comment);
        copy.setColor(colors);
        return copy;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }
}
