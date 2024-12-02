package Patient;

import java.util.List;

public class Patient {
    
    private int patientId;
    private String name;
    private List<String> allergies;
    private List<String> currentMedications;
    private String phoneNumber;

    public Patient(int patientId, String name, List<String> allergies, List<String> currentMedications, String phoneNumber) {

        this.patientId = patientId;
        this.name = name;
        this.allergies = allergies;
        this.currentMedications = currentMedications;
        this.phoneNumber = phoneNumber;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getCurrentMedications() {
        return currentMedications;
    }

    public void setCurrentMedications(List<String> currentMedications) {
        this.currentMedications = currentMedications;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}