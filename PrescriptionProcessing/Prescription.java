package PrescriptionProcessing;

public class Prescription {

    private int prescriptionId;
    private int medicationId;
    private int patientId;
    private String dosage;
    private String status;
    private String notes;

    public Prescription(int prescriptionId, int medicationId, int patientId, String dosage, String status, String notes) {
        this.prescriptionId = prescriptionId;
        this.medicationId = medicationId;
        this.patientId = patientId;
        this.dosage = dosage;
        this.status = status;
        this.notes = notes;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
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
