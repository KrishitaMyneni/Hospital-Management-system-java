package hospital;

public class Patient {
    private String name;
    private int age;
    private String gender;
    private String contact;

    public Patient(String name, int age, String gender, String contact) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return name + " | Age: " + age + " | Gender: " + gender + " | Contact: " + contact;
    }
}
