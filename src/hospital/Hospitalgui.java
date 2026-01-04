package hospital;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class Hospitalgui extends JFrame implements ActionListener {

    private HospitalManagementSystem hm = new HospitalManagementSystem();

    private JButton addPatientBtn, addDoctorBtn, addAppointmentBtn, addBillBtn;
    private JButton viewPatientsBtn, viewDoctorsBtn, viewAppointmentsBtn, viewBillsBtn;

    // === Theme Colors ===
    private final Color PINK_MAIN = new Color(226, 115, 140);
    private final Color PINK_BG = new Color(255, 182, 193);
    private final Color BLUE_MAIN = new Color(137, 207, 240);
    private final Color BLUE_BG = new Color(224, 243, 255);
    private final Color PRO_MAIN = new Color(60, 63, 65);
    private final Color PRO_BG = new Color(40, 44, 52);
    private final Color PRO_ALT = new Color(55, 60, 68);
    private final Color PRO_TEXT = Color.WHITE;

    private String currentTheme = "pink";

    private JPanel panel;
    private Color mainColor, bgColor, altColor, textColor;

    public Hospitalgui() {
        setTitle("Hospital Management System");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize main panel FIRST to avoid null issues 
        panel = new JPanel(new GridLayout(4, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        add(panel);

        Font btnFont = new Font("Arial", Font.BOLD, 16);

        addPatientBtn = new JButton("Add Patient");
        addDoctorBtn = new JButton("Add Doctor");
        addAppointmentBtn = new JButton("Add Appointment");
        addBillBtn = new JButton("Add Bill");
        viewPatientsBtn = new JButton("View Patients");
        viewDoctorsBtn = new JButton("View Doctors");
        viewAppointmentsBtn = new JButton("View Appointments");
        viewBillsBtn = new JButton("View Bills");

        JButton[] buttons = {addPatientBtn, addDoctorBtn, addAppointmentBtn, addBillBtn,
                viewPatientsBtn, viewDoctorsBtn, viewAppointmentsBtn, viewBillsBtn};

        for (JButton b : buttons) {
            b.setFont(btnFont);
            b.setFocusPainted(false);
            b.addActionListener(this);
            panel.add(b);
        }

        // Tiny theme switchers 
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        themePanel.setOpaque(false);

        JButton pinkBtn = createCircleButton(PINK_MAIN);
        pinkBtn.addActionListener(e -> setTheme("pink"));

        JButton blueBtn = createCircleButton(BLUE_MAIN);
        blueBtn.addActionListener(e -> setTheme("blue"));

        JButton proBtn = createCircleButton(PRO_MAIN);
        proBtn.addActionListener(e -> setTheme("professional"));

        themePanel.add(pinkBtn);
        themePanel.add(blueBtn);
        themePanel.add(proBtn);

        add(themePanel, BorderLayout.NORTH);

        // Now apply theme AFTER panel exists 
        setTheme("pink");

        preloadDoctors();
        setVisible(true);
    }

    private JButton createCircleButton(Color color) {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(20, 20));
        btn.setBackground(color);
        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        return btn;
    }

    private void setTheme(String theme) {
        currentTheme = theme;
        applyTheme();
    }

    private void applyTheme() {
        switch (currentTheme) {
            case "pink":
                mainColor = PINK_MAIN;
                bgColor = PINK_BG;
                altColor = new Color(255, 240, 245);
                textColor = Color.WHITE;
                break;
            case "blue":
                mainColor = BLUE_MAIN;
                bgColor = BLUE_BG;
                altColor = new Color(240, 250, 255);
                textColor = Color.DARK_GRAY;
                break;
            case "professional":
                mainColor = PRO_MAIN;
                bgColor = PRO_BG;
                altColor = PRO_ALT;
                textColor = PRO_TEXT;

                // Make input dialogs visible (white text on dark)
                UIManager.put("OptionPane.background", PRO_BG);
                UIManager.put("Panel.background", PRO_BG);
                UIManager.put("OptionPane.messageForeground", PRO_TEXT);
                UIManager.put("TextField.background", PRO_ALT);
                UIManager.put("TextField.foreground", PRO_TEXT);
                break;
        }

        // Apply colors globally
        getContentPane().setBackground(bgColor);
        if (panel != null) panel.setBackground(bgColor);

        Component[] comps = panel.getComponents();
        for (Component c : comps) {
            if (c instanceof JButton) {
                c.setBackground(mainColor);
                c.setForeground(textColor);
            }
        }

        UIManager.put("Button.background", mainColor);
        UIManager.put("Button.foreground", textColor);
        UIManager.put("OptionPane.background", bgColor);
        UIManager.put("Panel.background", bgColor);
    }

    private void preloadDoctors() {
        hm.addDoctor(new Doctor("Smith", "Cardiologist", "1234567890"));
        hm.addDoctor(new Doctor("Adams", "Neurologist", "0987654321"));
        hm.addDoctor(new Doctor("Lee", "Pediatrician", "1122334455"));
        hm.addDoctor(new Doctor("Patel", "Orthopedic", "6677889900"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPatientBtn) addPatient();
        else if (e.getSource() == addDoctorBtn) addDoctor();
        else if (e.getSource() == addAppointmentBtn) addAppointment();
        else if (e.getSource() == addBillBtn) addBill();
        else if (e.getSource() == viewPatientsBtn) showTable("Patients");
        else if (e.getSource() == viewDoctorsBtn) showTable("Doctors");
        else if (e.getSource() == viewAppointmentsBtn) showTable("Appointments");
        else if (e.getSource() == viewBillsBtn) showTable("Bills");
    }

    private void addPatient() {
        try {
            String name = JOptionPane.showInputDialog(this, "Patient Name:");
            if (name == null || name.isEmpty()) return;
            int age = Integer.parseInt(JOptionPane.showInputDialog(this, "Age:"));
            String gender = JOptionPane.showInputDialog(this, "Gender:");
            String contact = JOptionPane.showInputDialog(this, "Contact:");
            hm.addPatient(new Patient(name, age, gender, contact));
            JOptionPane.showMessageDialog(this, "Patient Added!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid age!");
        }
    }

    private void addDoctor() {
        String name = JOptionPane.showInputDialog(this, "Doctor Name:");
        String spec = JOptionPane.showInputDialog(this, "Specialization:");
        String contact = JOptionPane.showInputDialog(this, "Contact:");
        hm.addDoctor(new Doctor(name, spec, contact));
        JOptionPane.showMessageDialog(this, "Doctor Added!");
    }

    private void addAppointment() {
        if (hm.getPatients().isEmpty() || hm.getDoctors().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add patients and doctors first!");
            return;
        }

        String[] patientNames = hm.getPatients().stream().map(Patient::getName).toArray(String[]::new);
        String[] doctorNames = hm.getDoctors().stream().map(Doctor::getName).toArray(String[]::new);

        String patientName = (String) JOptionPane.showInputDialog(this, "Select Patient:", "Patient",
                JOptionPane.QUESTION_MESSAGE, null, patientNames, patientNames[0]);
        String doctorName = (String) JOptionPane.showInputDialog(this, "Select Doctor:", "Doctor",
                JOptionPane.QUESTION_MESSAGE, null, doctorNames, doctorNames[0]);
        String dateTime = JOptionPane.showInputDialog(this, "Date & Time (e.g., 2025-10-25 10:00):");

        Patient p = hm.getPatients().stream().filter(pt -> pt.getName().equals(patientName)).findFirst().orElse(null);
        Doctor d = hm.getDoctors().stream().filter(dc -> dc.getName().equals(doctorName)).findFirst().orElse(null);

        if (p != null && d != null) {
            hm.addAppointment(new Appointment(p, d, dateTime));
            JOptionPane.showMessageDialog(this, "Appointment Added!");
        }
    }

    private void showTable(String type) {
        String[] columns = {};
        Object[][] data = {};

        switch (type) {
            case "Patients":
                columns = new String[]{"Name", "Age", "Gender", "Contact"};
                data = hm.getPatients().stream()
                        .map(p -> new Object[]{p.getName(), p.getAge(), p.getGender(), p.getContact()})
                        .toArray(Object[][]::new);
                break;
            case "Doctors":
                columns = new String[]{"Name", "Specialization", "Contact"};
                data = hm.getDoctors().stream()
                        .map(d -> new Object[]{d.getName(), d.getSpecialization(), d.getContact()})
                        .toArray(Object[][]::new);
                break;
            case "Appointments":
                columns = new String[]{"Patient", "Doctor", "Date & Time"};
                data = hm.getAppointments().stream()
                        .map(a -> new Object[]{a.getPatient().getName(), a.getDoctor().getName(), a.getDateTime()})
                        .toArray(Object[][]::new);
                break;
            case "Bills":
                columns = new String[]{"Patient", "Amount", "Details"};
                data = hm.getBills().stream()
                        .map(b -> new Object[]{b.getPatient().getName(), b.getAmount(), b.getDetails()})
                        .toArray(Object[][]::new);
                break;
        }

        JTable table = new JTable(data, columns);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        table.setGridColor(mainColor);
        table.setSelectionBackground(mainColor);
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setBackground(mainColor);
        header.setFont(new Font("Arial", Font.BOLD, 16));

        // fix: set contrasting text + background based on theme
        if (currentTheme.equals("pink")) {
            table.setBackground(new Color(255, 240, 245));  // very light pink
            table.setForeground(Color.DARK_GRAY);
            header.setForeground(Color.WHITE);
        } else if (currentTheme.equals("blue")) {
            table.setBackground(Color.WHITE);
            table.setForeground(Color.DARK_GRAY);
            header.setForeground(Color.DARK_GRAY);
        } else if (currentTheme.equals("professional")) {
            table.setBackground(PRO_ALT);
            table.setForeground(Color.WHITE);
            header.setForeground(Color.WHITE);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(bgColor);

        JFrame tableFrame = new JFrame(type + " Table");
        tableFrame.setSize(600, 400);
        tableFrame.setLocationRelativeTo(this);
        tableFrame.add(scrollPane);
        tableFrame.setVisible(true);
    }

    private void addBill() {
        if (hm.getPatients().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add patients first!");
            return;
        }

        String[] patientNames = hm.getPatients().stream().map(Patient::getName).toArray(String[]::new);
        String patientName = (String) JOptionPane.showInputDialog(this, "Select Patient:", "Billing",
                JOptionPane.QUESTION_MESSAGE, null, patientNames, patientNames[0]);
        if (patientName == null) return;

        double consultationFee = 2000;
        double medicineCost = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Medicine Cost:"));
        double totalAmount = consultationFee + medicineCost;
        String details = "Consultation: 2000, Medicine: " + medicineCost;

        Patient p = hm.getPatients().stream().filter(pt -> pt.getName().equals(patientName)).findFirst().orElse(null);
        if (p != null) {
            hm.addBill(new Billing(p, totalAmount, details));
            JOptionPane.showMessageDialog(this, "Bill Added!\nTotal Amount: " + totalAmount);
        }
    }

    
    
    public static void main(String[] args) {
        new Hospitalgui();
    }
}
