import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HospitalManagementGUI extends JFrame {
    private PatientManagementSystem pms;
    private WaitingList waitingList;
    private JTextArea displayArea;

    public HospitalManagementGUI() {
        pms = new PatientManagementSystem();
        waitingList = new WaitingList();

        setTitle("Hospital Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title Panel
        JLabel titleLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        JButton addPatientButton = new JButton("Add Patient");
        JButton scheduleAppointmentButton = new JButton("Schedule Appointment");
        JButton cancelAppointmentButton = new JButton("Cancel Appointment");
        JButton addPaymentButton = new JButton("Add Payment");
        JButton generateReportButton = new JButton("Generate Report");
        JButton displayPatientsButton = new JButton("Display Patients");
        JButton displayAppointmentsButton = new JButton("Display Appointments");
        JButton displayBillingButton = new JButton("Display Billing Records");
        JButton displayWaitingListButton = new JButton("Display Waiting List");
        JButton generateBillButton = new JButton("Generate Bill for a Patient");
        JButton rescheduleAppointmentButton = new JButton("Reschedule Appointment");
        JButton updateContactButton = new JButton("Update Contact Info");
        JButton searchPatientButton = new JButton("Search Patient");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(addPatientButton);
        buttonPanel.add(scheduleAppointmentButton);
        buttonPanel.add(cancelAppointmentButton);
        buttonPanel.add(addPaymentButton);
        buttonPanel.add(generateReportButton);
        buttonPanel.add(displayPatientsButton);
        buttonPanel.add(displayAppointmentsButton);
        buttonPanel.add(displayBillingButton);
        buttonPanel.add(displayWaitingListButton);
        buttonPanel.add(generateBillButton);
        buttonPanel.add(rescheduleAppointmentButton);
        buttonPanel.add(updateContactButton);
        buttonPanel.add(searchPatientButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners for Buttons
        addPatientButton.addActionListener(e -> addPatient());
        scheduleAppointmentButton.addActionListener(e -> scheduleAppointment());
        cancelAppointmentButton.addActionListener(e -> cancelAppointment());
        addPaymentButton.addActionListener(e -> addPayment());
        generateReportButton.addActionListener(e -> generateReport());
        displayPatientsButton.addActionListener(e -> displayPatientsGUI());
        displayAppointmentsButton.addActionListener(e -> displayAppointmentsGUI());
        displayBillingButton.addActionListener(e -> displayBillingRecordsGUI());
        displayWaitingListButton.addActionListener(e -> displayWaitingListGUI());
        generateBillButton.addActionListener(e -> generateBill());
        rescheduleAppointmentButton.addActionListener(e -> rescheduleAppointment());
        updateContactButton.addActionListener(e -> updateContactInfo());
        searchPatientButton.addActionListener(e -> searchPatient());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void addPatient() {
        String name = JOptionPane.showInputDialog(this, "Enter patient name:");
        int age = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter patient age:"));
        String email = JOptionPane.showInputDialog(this, "Enter patient email:");
        int priority = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter priority (higher number = higher priority):"));

        List<String> medicalHistory = List.of(JOptionPane.showInputDialog(this, "Enter medical history (comma-separated):").split(","));

        int patientID = pms.patientBST.getSize() + 1;
        Patient patient = new Patient(patientID, name, age, email, priority);
        medicalHistory.forEach(patient.getMedicalHistory()::add);

        pms.addPatient(patient);
        waitingList.addToWaitList(patient);

        JOptionPane.showMessageDialog(this, "Patient added successfully! ID: " + patientID);
    }

    private void scheduleAppointment() {
        int patientID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter patient ID:"));
        String date = JOptionPane.showInputDialog(this, "Enter appointment date (YYYY-MM-DD):");
        String time = JOptionPane.showInputDialog(this, "Enter appointment time (HH:MM):");

        pms.scheduleAppointment(patientID, date, time);
    }

    private void cancelAppointment() {
        int patientID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter patient ID to cancel appointment:"));
        pms.cancelAppointment(patientID);
        waitingList.removeFromWaitList(patientID);
    }

    private void addPayment() {
        int patientID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter patient ID:"));
        double amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter payment amount:"));

        Patient patient = pms.findPatient(patientID);
        if (patient != null) {
            Billing billing = pms.findBillingRecord(patient);
            billing.addPayment(amount);
            JOptionPane.showMessageDialog(this, "Payment added successfully! Outstanding Balance: $" + billing.getOutstandingBalance());
        } else {
            JOptionPane.showMessageDialog(this, "Patient not found.");
        }
    }

    private void generateReport() {
        String reportType = JOptionPane.showInputDialog(this, "Enter report type (PATIENT, APPOINTMENT, REVENUE):");
        switch (reportType.toUpperCase()) {
            case "PATIENT":
                int patientID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter patient ID:"));
                Patient patient = pms.findPatient(patientID);
                if (patient != null) {
                    JOptionPane.showMessageDialog(this, pms.generateReport("PATIENT", patient));
                }
                break;
            case "APPOINTMENT":
                JOptionPane.showMessageDialog(this, pms.generateReport("APPOINTMENT", pms.appointmentRecords));
                break;
            case "REVENUE":
                JOptionPane.showMessageDialog(this, pms.generateReport("REVENUE", pms.billingRecords));
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid report type.");
        }
    }

    private void displayPatientsGUI() {
        displayArea.setText("");
        List<Patient> patients = pms.patientList;
        if (patients.isEmpty()) {
            displayArea.setText("No patients available.");
        } else {
            for (Patient patient : patients) {
                displayArea.append(patient.getPatientInfo() + "\n");
            }
        }
    }

    private void displayAppointmentsGUI() {
        displayArea.setText("");
        for (Appointment appointment : pms.appointmentRecords) {
            displayArea.append(appointment.toString() + "\n");
        }
    }

    private void displayBillingRecordsGUI() {
        displayArea.setText("");
        for (Billing billing : pms.billingRecords) {
            displayArea.append(billing.getPaymentStatus() + "\n");
        }
    }

    private void displayWaitingListGUI() {
        displayArea.setText(""); // Clear the display area
        if (waitingList.queue.front == null) { // Access the front node of the queue
            displayArea.setText("No patients in the waiting list.");
        } else {
            displayArea.append("Waiting List (Ordered by Priority):\n");
            Queue.Node temp = waitingList.queue.front; // Assuming 'queue' is your Queue instance
            while (temp != null) {
                displayArea.append(temp.data.getPatientInfo() + "\n"); // Adjust as per Patient class
                temp = temp.next; // Move to the next node
            }
        }
    }





    private void generateBill() {
        displayArea.setText(""); // Clear the display area
        int patientID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter patient ID:"));
        Patient patient = pms.findPatient(patientID);
        if (patient != null) {
            Billing billing = pms.findBillingRecord(patient);
            double amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter billing amount:"));
            billing.setManualBillingAmount(amount);
            displayArea.append("Bill for Patient: " + patient.getName() + "\n");
            displayArea.append("Billing Amount: $" + billing.getOutstandingBalance() + "\n");
        } else {
            displayArea.setText("Patient not found.");
        }
    }


    private void rescheduleAppointment() {
        int appointmentID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Patient ID:"));
        Appointment appointment = pms.findAppointment(appointmentID);
        if (appointment != null) {
            String newDate = JOptionPane.showInputDialog(this, "Enter new date (YYYY-MM-DD):");
            String newTime = JOptionPane.showInputDialog(this, "Enter new time (HH:MM):");
            appointment.reschedule(newDate, newTime);
        } else {
            JOptionPane.showMessageDialog(this, "Appointment not found.");
        }
    }

    private void updateContactInfo() {
        int patientID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter patient ID:"));
        Patient patient = pms.findPatient(patientID);
        if (patient != null) {
            String newContact = JOptionPane.showInputDialog(this, "Enter new contact information:");
            patient.updateContactInfo(newContact);
        } else {
            JOptionPane.showMessageDialog(this, "Patient not found.");
        }
    }

    private void searchPatient() {
        int patientID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter patient ID:"));
        Patient patient = pms.findPatient(patientID);
        if (patient != null) {
            JOptionPane.showMessageDialog(this, patient);
        } else {
            JOptionPane.showMessageDialog(this, "Patient not found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HospitalManagementGUI gui = new HospitalManagementGUI();
            gui.setVisible(true);
        });
    }
}
