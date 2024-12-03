package PrescriptionProcessing;

public class Prescription {

    private int prescriptionId;
    private String medicationName;
    private int medicationId;
    private String patientName;
    private int patientId;
    private String dosage;
    private int numDays;
    private String status;
    private String notes;

    public Prescription(int prescriptionId, String medicationName, int medicationId, String patientName, int patientId, String dosage, int numDays, String status, String notes) {
        this.prescriptionId = prescriptionId;
        this.medicationName = medicationName;
        this.medicationId = medicationId;
        this.patientName = patientName;
        this.patientId = patientId;
        this.dosage = dosage;
        this.numDays = numDays;
        this.status = status;
        this.notes = notes;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
