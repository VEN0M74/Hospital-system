import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int patientID;
    private String name;
    private int priority;
    private int age;
    private String contactInfo;
    private List<String> medicalHistory;
    private List<String> visitRecords;
    private List<Double> charges;
    private List<Double> payments;
    private double totalPayments;
    private double totalCharges;


    public Patient(int patientID, String name, int age, String contactInfo, int priority) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.contactInfo = contactInfo;
        this.priority = priority;
        this.medicalHistory = new ArrayList<>();
        this.visitRecords = new ArrayList<>();


    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    // Getters and Setters
    public int getPatientID() {
        return patientID;
    }


    public String getName() {
        return name; }


    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory; }

    public List<String> getVisitRecords() {
        return visitRecords; }

    // Methods
    public void updateContactInfo(String newContactInfo) {
        setContactInfo(newContactInfo);
    }

    public void addVisitRecord(String visitRecord) {
        visitRecords.add(visitRecord);
    }

    public String getPatientInfo() {
        return "Patient ID: " + patientID + ", Name: " + name + ", Age: " + age + ", Contact Info: " + contactInfo;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientID=" + patientID +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", contactInfo='" + contactInfo + '\'' +
                ", medicalHistory=" + medicalHistory +
                ", visitRecords=" + visitRecords +
                '}';
    }

    public void setEmail(String newEmail) {
    }

    public void setPhoneNumber(String newPhone) {
    }

    public int getAge() {
        return age;
    }

    public char[] getContactInfo() {
        return null;
    }

    public List<Double> getCharges() {
        return charges;
    }

    public List<Double> getPayments() {
        return payments;
    }

    public double getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(double totalCharges) {
        this.totalCharges = totalCharges;
    }

    public double getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(double totalPayments) {
        this.totalPayments = totalPayments;
    }
}

// Binary Search Tree for Patients
class PatientBST {

    private class Node {
        Patient patient;
        Node left, right;

        Node(Patient patient) {
            this.patient = patient;
        }
    }

    private Node root;

    // Add a new patient to the BST
    public void addPatient(Patient patient) {
        root = addRecursive(root, patient);
    }

    private Node addRecursive(Node current, Patient patient) {
        if (current == null) {
            return new Node(patient);
        }// method recursive

        if (patient.getPatientID() < current.patient.getPatientID()) {
            current.left = addRecursive(current.left, patient);
        } else if (patient.getPatientID() > current.patient.getPatientID()) {
            current.right = addRecursive(current.right, patient);
        }

        return current;
    }

    // Search for a patient by ID
    public Patient searchPatient(int patientID) {
        return searchRecursive(root, patientID);
    }

    private Patient searchRecursive(Node current, int patientID) {
        if (current == null) {
            return null;
        }

        if (patientID == current.patient.getPatientID()) {
            return current.patient;
        }
        if (patientID<current.patient.getPatientID()) {
            return searchRecursive(current.left, patientID);
        }
        else {
            return searchRecursive(current.right, patientID);

        }




    }
    public int getSize() {
        return getSizeRecursive(root);
    }

    private int getSizeRecursive(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + getSizeRecursive(node.left) + getSizeRecursive(node.right);
    }


    // In-order traversal to display all patients
    public void displayPatients() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println(node.patient);
            inOrderTraversal(node.right);
        }
    }
}