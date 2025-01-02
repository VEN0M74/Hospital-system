import java.util.*;

public class PatientManagementSystem {
    // Attributes
    public List<Patient> patientList;  // Stores all patients
    private Queue appointmentQueue;  // Queue for scheduled appointments
    private WaitingList waitingList;  // Stores patients waiting for appointments
    public List<Billing> billingRecords;  // Stores billing records for each patient
    public List<Appointment> appointmentRecords;  // Stores all appointments
    public PatientBST patientBST;

    // Constructor to initialize the system
    public PatientManagementSystem() {
        this.patientList = new ArrayList<>();
        this.appointmentQueue = new Queue();  // Using custom Queue for appointments
        this.waitingList = new WaitingList();
        this.billingRecords = new ArrayList<>();
        patientBST = new PatientBST();
        this.appointmentRecords = new ArrayList<>();
    }

    // Add a new patient to the system
    public void addPatient(Patient patient) {
        patientBST.addPatient(patient);
        patientList.add(patient);
    }

    // Assuming patientBST is an instance of PatientBST class
    public Patient findPatient(int patientID) {
        // Call the searchPatient method from PatientBST
        Patient patient = patientBST.searchPatient(patientID);

        // If the patient is found, return the patient
        if (patient != null) {
            return patient;
        }

        // If patient is not found in the BST, print an error message and return null
        System.out.println("Patient not found.");
        return null;
    }


    // Schedule an appointment for a patient
    public void scheduleAppointment(int patientID, String date, String time) {
        Patient patient = findPatient(patientID);
        if (patient != null) {
            Appointment appointment = new Appointment(generateAppointmentID(), patient, date, time);
            appointmentQueue.enqueue(patient);  // Add to the appointment queue
            appointmentRecords.add(appointment);
            System.out.println("Appointment scheduled for " + patient.getName() + " on " + date + " at " + time);
        }
    }


    // Cancel an appointment for a patient
    public void cancelAppointment(int patientID) {
        Patient patient = findPatient(patientID);
        if (patient != null) {
            for (int i = 0; i < appointmentRecords.size(); i++) {
                Appointment appointment = appointmentRecords.get(i);
                if (appointment.getPatient().getPatientID() == patientID && appointment.getStatus().equals("Scheduled")) {
                    appointment.cancel();
                    break;
                }
            }

        }
    }


    // Generate a report for patients, appointments, or revenue
    public String generateReport(String reportType, Object data) {
        ReportGenerator reportGenerator = new ReportGenerator(reportType, data);
        return reportGenerator.generateReport();
    }

    // Helper method to generate a unique appointment ID
    private int generateAppointmentID() {
        return appointmentRecords.size() + 1;  // Simple incrementing ID for appointments
    }

    // Find a patient's billing record
    public Billing findBillingRecord(Patient patient) {
        for (int i = 0; i < billingRecords.size(); i++) {
            Billing billing = billingRecords.get(i);
            if (billing.getPatient().getPatientID() == patient.getPatientID()) {
                return billing;
            }
        }

        Billing newBilling = new Billing(patient);
        billingRecords.add(newBilling);
        return newBilling;
    }

    // Display all patients in the system, excluding canceled ones
    // Display all patients in the system
    public void displayPatients() {

        // Display all patients using in-order traversal
        patientBST.displayPatients();

    }

    public Appointment findAppointment(int appointmentID) {
        for (int i = 0; i < appointmentRecords.size(); i++) {
            if (appointmentRecords.get(i).getAppointmentID() == appointmentID) {
                return appointmentRecords.get(i);
            }
        }
        return null; // Return null if not found
    }


    // Display all scheduled appointments
    public String displayAppointments() {
        for (int i = 0; i < appointmentRecords.size(); i++) {
            Appointment appointment = appointmentRecords.get(i);
            System.out.println(appointment.toString());
        }

        return null;
    }


    // Display all billing records
    public void displayBillingRecords() {
        for (int i = 0; i < billingRecords.size(); i++) {
            Billing billing = billingRecords.get(i);
            System.out.println(billing.getPaymentStatus());
        }

    }
}

