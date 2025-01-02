import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the Patient Management System
        PatientManagementSystem pms = new PatientManagementSystem();
        Scanner scanner = new Scanner(System.in);
        WaitingList waitingList= new WaitingList();


        while (true) {
            // Display the menu options
            System.out.println("\n--- Hosiptal Management System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Add Payment");
            System.out.println("5. Generate Report");
            System.out.println("6. Display Patients");
            System.out.println("7. Display Appointments");
            System.out.println("8. Display Billing Records");
            System.out.println("9. Display Waiting List");
            System.out.println("10. Generate Bill for a Patient");
            System.out.println("11. Reschedule Appointment");
            System.out.println("12. Update Contact Information");
            System.out.println("13. Search Patient");
            System.out.println("14. Exit");


            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    // Add new patient
                    System.out.print("Enter patient name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter patient age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter patient email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter priority (higher number means higher priority): ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    // Input medical history
                    List<String> medicalHistory = new ArrayList<>();
                    System.out.println("Enter medical history (type 'done' to finish):");
                    while (true) {
                        System.out.print("Medical History Entry: ");
                        String historyEntry = scanner.nextLine();
                        if (historyEntry.equalsIgnoreCase("done")) {
                            break;
                        }
                        medicalHistory.add(historyEntry);
                    }

                    // Create patient and add to system
                    int patientID = (pms.patientBST.getSize() + 1);  // Simple patient ID
                    Patient patient = new Patient(patientID, name, age, email, priority);
                    for (String entry : medicalHistory) {
                        patient.getMedicalHistory().add(entry); // Add history to patient
                    }
                    pms.addPatient(patient);
                    System.out.println("Patient added successfully. Your ID is " + patientID);
                    waitingList.addToWaitList(patient);
                    break;



                case 2:
                    // Schedule an appointment
                    System.out.print("Enter patient ID to schedule appointment: ");
                    int patientIDToSchedule = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter appointment date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter appointment time (HH:MM AM/PM): ");
                    String time = scanner.nextLine();

                    // Find the patient
                    Patient patientToSchedule = pms.findPatient(patientIDToSchedule);
                    if (patientToSchedule != null) {
                        // Schedule the appointment
                        Appointment appointment = new Appointment(pms.appointmentRecords.size() + 1, patientToSchedule, date, time);
                        pms.scheduleAppointment(patientIDToSchedule, date, time);

                        // Add the visit record for this patient
                        String visitRecord = "Appointment scheduled for " + date + " at " + time;
                        patientToSchedule.addVisitRecord(visitRecord);  // Add the visit record

                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;


                case 3:
                    // Cancel an appointment
                    System.out.print("Enter patient ID to cancel appointment: ");
                    int patientIDToCancel = scanner.nextInt();
                    pms.cancelAppointment(patientIDToCancel);
                    waitingList.removeFromWaitList(patientIDToCancel); // Removes the specific patient
                    break;


                case 4:
                    // Add payment for a patient
                    System.out.print("Enter patient ID to make payment: ");
                    int patientIDToPay = scanner.nextInt();
                    System.out.print("Enter payment amount: ");
                    double paymentAmount = scanner.nextDouble();

                    Patient patientPay = pms.findPatient(patientIDToPay);
                    if (patientPay != null) {
                        Billing billing = pms.findBillingRecord(patientPay);
                        billing.addPayment(paymentAmount);

                        // Display updated payment status and remaining balance
                        System.out.println("Payment added successfully!");
                        System.out.println("Outstanding Balance: $" + billing.getOutstandingBalance());
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 5:
                    // Generate and display report
                    System.out.print("Enter report type (PATIENT, APPOINTMENT, REVENUE): ");
                    String reportType = scanner.nextLine();
                    if (reportType.equalsIgnoreCase("PATIENT")) {
                        System.out.print("Enter patient ID for report: ");
                        int reportPatientID = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        Patient reportPatient = pms.findPatient(reportPatientID);
                        if (reportPatient != null) {
                            String patientReport = pms.generateReport("PATIENT", reportPatient);
                            System.out.println(patientReport);
                        }
                    } else if (reportType.equalsIgnoreCase("APPOINTMENT")) {
                        String appointmentReport = pms.generateReport("APPOINTMENT", pms.appointmentRecords);
                        System.out.println(appointmentReport);}
                    else if (reportType.equalsIgnoreCase("REVENUE")) {
                        String revenueReport = pms.generateReport("REVENUE", pms.billingRecords); // Pass billing records
                        System.out.println(revenueReport);
                    } else {
                        System.out.println("Invalid report type.");
                    }
                    break;

                case 6:
                    // Display all patients


                   // pms.displayPatients();
                    pms.displayPatients();  // Use BST's in-order traversal for display
                    break;

                case 7:
                    // Display all appointments
                    pms.displayAppointments();
                    break;

                case 8:
                    // Display billing records
                    pms.displayBillingRecords();
                    break;

                case 9:
                    // Display waiting list
                    waitingList.displayWaitList();
                    break;
                case 10:
                    // Generate bill for a patient
                    System.out.print("Enter patient ID to generate bill: ");
                    int patientIDToBill = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    // Find the patient
                    Patient patientToBill = pms.findPatient(patientIDToBill);
                    if (patientToBill != null) {
                        // Find or create billing record
                        Billing billing = pms.findBillingRecord(patientToBill);

                        // Allow manual input for the billing amount
                        System.out.print("Enter the billing amount for this patient: $");
                        double manualBillingAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline character
                        billing.setManualBillingAmount(manualBillingAmount); // Set manually entered billing amount

                        System.out.println("Bill generated successfully.");
                        System.out.println("Outstanding Balance: $" + billing.getOutstandingBalance());
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;



                case 11:
                    // Reschedule an appointment
                    System.out.print("Enter the appointment ID to reschedule: ");
                    int appointmentIDToReschedule = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    // Find the appointment
                    Appointment appointmentToReschedule = pms.findAppointment(appointmentIDToReschedule);
                    if (appointmentToReschedule != null) {
                        System.out.print("Enter the new appointment date (YYYY-MM-DD): ");
                        String newDate = scanner.nextLine();
                        System.out.print("Enter the new appointment time (HH:MM AM/PM): ");
                        String newTime = scanner.nextLine();

                        // Reschedule the appointment
                        appointmentToReschedule.reschedule(newDate, newTime);
                        System.out.println("Appointment rescheduled successfully.");
                    } else {
                        System.out.println("Appointment not found.");
                    }
                    break;

                case 12:
                    // Update contact information
                    System.out.print("Enter patient ID to update contact information: ");
                    int patientIDToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    Patient patientToUpdate = pms.findPatient(patientIDToUpdate);
                    if (patientToUpdate != null) {
                        System.out.print("Enter new contact information: ");
                        String newContactInfo = scanner.nextLine();
                        patientToUpdate.updateContactInfo(newContactInfo);
                        System.out.println("Contact information updated successfully for " + patientToUpdate.getName());
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 13:
                    // Search for a patient by ID
                    System.out.print("Enter patient ID to search: ");
                    int patientIDToSearch = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    // Find the patient
                    Patient patientToSearch = pms.findPatient(patientIDToSearch);
                    if (patientToSearch != null) {
                        System.out.println("Patient found: " + patientToSearch);
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 14:
                    // Exit
                    System.out.println("Exiting the system...");
                    scanner.close();
                    return;






                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
