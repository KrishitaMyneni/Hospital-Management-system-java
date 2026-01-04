package hospital;

import java.util.ArrayList;

public class HospitalManagementSystem {

    public ArrayList<Patient> patients = new ArrayList<>();
    public ArrayList<Doctor> doctors = new ArrayList<>();
    public ArrayList<Appointment> appointments = new ArrayList<>();
    public ArrayList<Billing> bills = new ArrayList<>();
   

    // Add methods
    public void addPatient(Patient p) { patients.add(p); }
    public void addDoctor(Doctor d) { doctors.add(d); }
    public void addAppointment(Appointment a) { appointments.add(a); }
    public void addBill(Billing b) { bills.add(b); }
   

    // Getter methods for GUI
    public ArrayList<Patient> getPatients() { return patients; }
    public ArrayList<Doctor> getDoctors() { return doctors; }
    public ArrayList<Appointment> getAppointments() { return appointments; }
    public ArrayList<Billing> getBills() { return bills; }
    
}
