public class Volunteer {
    private String name;
    private String email;
    private String phone;
    private double hours;
    private String _id;

    public Volunteer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    // Getters & setters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public double getHours() { return hours; }
    public String getId() { return _id; }
}
