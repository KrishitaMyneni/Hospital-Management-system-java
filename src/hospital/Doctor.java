package hospital;

public class Doctor {
    private String name;
    private String specialization;
    private String contact;

    public Doctor(String name, String specialization, String contact) {
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }

    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return "Dr. " + name + " | " + specialization + " | Contact: " + contact;
    }
}
