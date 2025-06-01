public class Volunteer {
    private String name;
    private String email;
    private String phone;
    private String _id;

    public Volunteer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getId() { return _id; }
}
