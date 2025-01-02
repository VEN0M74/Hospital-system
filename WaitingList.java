public class WaitingList {
    public Queue queue;  // Using the custom Queue class

    public WaitingList() {

        queue = new Queue();  // Initialize the custom queue
    }

    // Method to add a patient to the waiting list
    public void addToWaitList(Patient patient) {
        queue.enqueue(patient);  // Adds patient to the queue
        System.out.println(patient.getName() + " has been added to the waiting list with priority " + patient.getPriority() + ".");
    }

    // Remove a specific patient from the waiting list
    public Patient removeFromWaitList(int patientIDToCancel) {
        Patient removedPatient = queue.removeById(patientIDToCancel);
        if (removedPatient != null) {
            System.out.println(removedPatient.getName() + " has been removed from the waiting list.");
        }
        return removedPatient;
    }


    // Method to display the waiting list
    public String displayWaitList() {
        if (queue.front == null) {
            System.out.println("The waiting list is empty.");
        } else {
            System.out.println("Waiting List (Ordered by Priority):");
            queue.display();  // Display the queue, which is already ordered by priority
        }
        return null;
    }
}
