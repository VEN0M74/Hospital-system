public class Appointment {
    private int appointmentID;
    private Patient patient; // Association with Patient class
    private String date;
    private String time;
    private String status;

    public Appointment(int appointmentID, Patient patient, String date, String time) {
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.date = date;
        this.time = time;
        this.status = "Scheduled"; // Default status
    }

    public int getAppointmentID() {
        return appointmentID;
    }


    public Patient getPatient() {
        return patient;
    }


    public String getDate() {
        return date;
    }


    public String getTime() {
        return time;
    }


    public String getStatus() {
        return status;
    }



    public void cancel() {
        this.status = "Cancelled";
        System.out.println("Appointment with ID " + appointmentID + " has been cancelled.");
    }

    public void reschedule(String newDate, String newTime) {
        this.date = newDate;
        this.time = newTime;
        this.status = "Rescheduled";
        System.out.println("Appointment rescheduled to " + newDate + " at " + newTime);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", patient=" + patient.getName() +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
