package www.danapharma.kz;

public class PharmacyItem {

    String id;
    String first_name;
    String last_name;
    String email;
    String phone;
    String title;
    String address;
    String latlan;
    String price;

    public PharmacyItem(String id, String first_name, String last_name, String email, String phone, String title, String address, String latlan, String price) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.title = title;
        this.address = address;
        this.latlan = latlan;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getLatlan() {
        return latlan;
    }

    public String getPrice() {
        return price;
    }
}


