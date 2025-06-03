public class Entry {
    private Volunteer volunteer;
    private String title;
    private String description;
    private double hours;
    private String approved;
    private String _id;

    public Entry(Volunteer volunteer, String title, String description, double hours) {
        this.volunteer = volunteer;
        this.title = title;
        this.description = description;
        this.hours = hours;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getHours() {
        return hours;
    }

    public String getApproved() {
        return approved;
    }

    public String get_id() {
        return _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}
