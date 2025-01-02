import java.util.*;

public class Billing {
    private Patient patient;
    private double billingAmount;
    private List<Double> paymentHistory;

    // Constructor
    public Billing(Patient patient) {

        this.patient = patient;
        this.billingAmount = 0.0;
        this.paymentHistory = new ArrayList<>();
    }

    // Getter for patient
    public Patient getPatient() {

        return patient;
    }

    public void setManualBillingAmount(double amount) {
        if (amount < 0) {
            System.out.println("Invalid billing amount. Must be non-negative.");
            return;
        }
        this.billingAmount = amount;
        System.out.println("Billing amount for " + patient.getName() + " set to $" + amount);
    }

    public double getTotalPayments() {
        double totalPayments = 0.0;
        for (int i = 0; i < paymentHistory.size(); i++) {
            totalPayments =  totalPayments+ paymentHistory.get(i);
        }
        return totalPayments;
    }

    public double getOutstandingBalance() {
        return billingAmount; // The billing amount decreases as payments are made.
    }


    // Add payment and update the balance
    public void addPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount. Must be greater than zero.");
            return;
        }
        if (amount > billingAmount) {
            System.out.println("Payment exceeds the current billing amount. Payment not processed.");
            return;
        }
        paymentHistory.add(amount);
        billingAmount -= amount;
        System.out.println("Payment of $" + amount + " added for patient: " + patient.getName());
    }

    // Get the payment status (outstanding balance)
    public String getPaymentStatus() {
        return "Outstanding Balance for " + patient.getName() + " (ID: " + patient.getPatientID() + "): $" + billingAmount;
    }
}


