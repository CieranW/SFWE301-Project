package Patient;

import java.util.List;

public class Patient {
    
    private int patientId;
    private String patientName;
    private List<String> allergies;
    private List<Integer> allergyId;
    private List<String> currentMedications;
    private List<Integer> currentMedsId;

    public Patient(String patientName, int patientId, List<String> allergies, List<Integer> allergyId, List<String> currentMedications, List<Integer> currentMedsId) {

        this.patientId = patientId;
        this.patientName = patientName;
        this.allergies = allergies;
        this.allergyId = allergyId;
        this.currentMedications = currentMedications;
        this.currentMedsId = currentMedsId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<Integer> getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(List<Integer> allergyId) {
        this.allergyId = allergyId;
    }

    public List<String> getCurrentMedications() {
        return currentMedications;
    }

    public void setCurrentMedications(List<String> currentMedications) {
        this.currentMedications = currentMedications;
    }

    public List<Integer> getCurrentMedsId() {
        return currentMedsId;
    }

    public void setCurrentMedsId(List<Integer> currentMedsId) {
        this.currentMedsId = currentMedsId;
    }

    @Override
    public String toString() {
        return "Patient{" +
               "patientName='" + patientName + '\'' +
               ", patientId=" + patientId +
               ", allergies=" + allergies +
               ", allergyIds=" + allergyId +
               ", currentMedications=" + currentMedications +
               ", medicationIds=" + currentMedsId +
               '}';
    }
}