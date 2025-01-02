import java.util.*;

public class ReportGenerator {

    // Attributes
    private String reportType;
    private Object data;

    // Constructor to initialize reportType and data
    public ReportGenerator(String reportType, Object data) {
        this.reportType = reportType;
        this.data = data;
    }

    // Generate the report based on the reportType
    public String generateReport() {
        switch (reportType) {
            case "PATIENT":
                return generatePatientReport((Patient) data);
            case "APPOINTMENT":
                return generateAppointmentReport((List<Appointment>) data);
            case "REVENUE":
                return generateRevenueReport((List<Billing>) data);  // Example: Revenue based on appointments
            default:
                return "Invalid report type";
        }
    }

    // Generate report for a patient
    public String generatePatientReport(Patient patient) {
        String report = "Patient Report\n===============\n";
        report = report + patient.getPatientInfo() + "\n";


        report = report + "Medical History:\n";
        List<String> medicalHistory = patient.getMedicalHistory();
        for (int i = 0; i < medicalHistory.size(); i++) {
            report = report + "- " + medicalHistory.get(i) + "\n";
        }

        // Visit Records
        report += "Visit Records:\n";
        List<String> visitRecords = patient.getVisitRecords();
        for (int i = 0; i < visitRecords.size(); i++) {
            report = report + visitRecords.get(i) + "\n";
        }

        return report;
    }


    // Generate report for appointments
    public String generateAppointmentReport(List<Appointment> appointments) {
        String report = "Appointment Report\n";
        report = report + "=================\n";

// Sort appointments by date using Quick Sort
        List<Appointment> sortedAppointments = new ArrayList<>(appointments);
        sortAppointments(sortedAppointments);
//

        for (int i = 0; i < sortedAppointments.size(); i++) {
            Appointment appo = sortedAppointments.get(i);
            report = report + String.format("ID: %d, Patient: %s, Date: %s, Time: %s, Status: %s\n",
                    appo.getAppointmentID(), appo.getPatient().getName(), appo.getDate(), appo.getTime(), appo.getStatus());
        }

        return report;
    }

    public String generateRevenueReport(List<Billing> billingRecords) {
        StringBuilder report = new StringBuilder("Revenue Report\n===============\n");

        double totalRevenue = 0;
        for (int i = 0; i < billingRecords.size(); i++) {
            Billing billing = billingRecords.get(i); // Access each Billing object using the index
            double payments = billing.getTotalPayments();
            totalRevenue += payments;
            report.append(String.format("Patient: %s, Payments Made: $%.2f\n",
                    billing.getPatient().getName(), payments));
        }

        report.append(String.format("\nTotal Revenue: $%.2f", totalRevenue));
        return report.toString();
    }

    // Quick Sort for Appointments (Sorting by appointment date)
    private void sortAppointments(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = 0; j < appointments.size() - i - 1; j++) {
                if (appointments.get(j).getDate().compareTo(appointments.get(j + 1).getDate()) > 0) {
                    // Swap appointments
                    Appointment temp = appointments.get(j);
                    appointments.set(j, appointments.get(j + 1));
                    appointments.set(j + 1, temp);
                }
            }
        }
    }


}





