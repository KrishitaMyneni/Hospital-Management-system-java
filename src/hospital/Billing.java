package hospital;

public class Billing {
    private Patient patient;
    private double amount;
    private String details;

    public Billing(Patient patient, double amount, String details) {
        this.patient = patient;
        this.amount = amount;
        this.details = details;
    }

    public Patient getPatient() { return patient; }
    public double getAmount() { return amount; }
    public String getDetails() { return details; }

    @Override
    public String toString() {
        return "Bill for " + patient.getName() + " | Amount: $" + amount + " | Details: " + details;
    }
}
