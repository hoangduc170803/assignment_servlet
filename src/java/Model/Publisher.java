package Model;

public class Publisher {
private int publisher_id;
private String name;
private String address;
private String contact;

    public Publisher() {
    }

    public Publisher(int publisher_id, String name, String address, String contact) {
        this.publisher_id = publisher_id;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


}
