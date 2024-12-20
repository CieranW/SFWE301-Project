package PrescriptionProcessing;

public class Prescription {

    private int prescriptionId;
    private String medicationName;
    private int medicationId;
    private String patientName;
    private int patientId;
    private int dosage;
    private int numDays;
    private int dailyIntake;
    private String status;
    private String notes;

    public Prescription(String patientName, int patientId, String medicationName, int medicationId, int dosage, int numDays, int dailyIntake, String status, String notes) {
        this.medicationName = medicationName;
        this.medicationId = medicationId;
        this.patientName = patientName;
        this.patientId = patientId;
        this.dosage = dosage;
        this.numDays = numDays;
        this.dailyIntake = dailyIntake;
        this.status = status;
        this.notes = notes;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = updatePrescriptionID(prescriptionId);
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

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public int getDailyIntake() {
        return dailyIntake;
    }

    public void setDailyIntake(int dailyIntake) {
        this.dailyIntake = dailyIntake;
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

    @Override
    public String toString() {
        return "Prescription{" +
               "prescriptionId=" + prescriptionId +
               ", patientName='" + patientName + '\'' +
               ", patientId=" + patientId +
               ", medicationName='" + medicationName + '\'' +
               ", medicationId=" + medicationId +
               ", dosage='" + dosage + '\'' +
               ", daysToTake=" + numDays +
               ", notes='" + notes + '\'' +
               ", status='" + status + '\'' +
               '}';
    }

    public int updatePrescriptionID(int prescriptionID) {
        prescriptionID += 1;
        return prescriptionID;
    }

    public int calculateDosage(int numDays, int dailyIntake) {
        return numDays * dailyIntake;
    }

}
