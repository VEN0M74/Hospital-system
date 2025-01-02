public class Queue {

        static class Node {
            Patient data;
            Node next;

            public Node(Patient data) {
                this.data = data;
                this.next = null;
            }
        }

        Node front, rear;

        public Queue() {
            front = rear = null;
        }

        // Enqueue method to add a Patient to the queue based on priority
        public void enqueue(Patient patient) {
            Node newNode = new Node(patient);

            // If the queue is empty, add the new node
            if (front == null) {
                front = rear = newNode;
            } else {
                // If the queue is not empty, find the right position based on priority
                Node current = front;
                Node previous = null;

                // Traverse the queue to find the correct position to insert the patient
                while (current != null && current.data.getPriority() >= patient.getPriority()) {
                    previous = current;
                    current = current.next;
                }

                // Insert at the front if it's the highest priority
                if (previous == null) {
                    newNode.next = front;
                    front = newNode;
                } else {
                    // Insert after the 'previous' node
                    previous.next = newNode;
                    newNode.next = current;
                }
            }
        }

    // Method to remove a patient by ID
    public Patient removeById(int patientID) {
        if (front == null) {
            System.out.println("Queue is empty.");
            return null;
        }

        Node current = front;
        Node previous = null;

        // Traverse the queue to find the patient
        while (current != null && current.data.getPatientID() != patientID) {
            previous = current;
            current = current.next;
        }

        if (current == null) {
            System.out.println("Patient with ID " + patientID + " not found.");
            return null;
        }

        // Remove the node
        if (previous == null) {
            // Removing the front node
            front = front.next;
            if (front == null) { // Queue becomes empty
                rear = null;
            }
        } else {
            previous.next = current.next;
            if (current.next == null) { // Removing the rear node
                rear = previous;
            }
        }

        return current.data;
    }


    // Display method to print all Patients in the queue
        public void display() {
            if (front == null) {
                System.out.println("Queue is empty.");
            } else {
                Node temp = front;  // Create a temporary node to traverse the queue
                while (temp != null) {
                    System.out.println(temp.data.getPatientInfo());  // Display patient info
                    temp = temp.next;  // Move to the next node
                }
            }
        }
    }


