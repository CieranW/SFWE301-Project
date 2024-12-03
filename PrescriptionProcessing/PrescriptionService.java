package PrescriptionProcessing;

import java.util.List;
import java.util.ArrayList;
import PrescriptionProcessing.Prescription;
import Patient.Patient;

public class PrescriptionService {

    private List<Prescription> prescriptionList = new ArrayList<>();

    // add new prescription order (8.3.1)
    public boolean addNewPresctiption(Prescription prescription) {
        //validate prescription fields
        if (!checkPrescriptionFields(prescription)) {
            System.out.println("Error: Prescription fields are invalid.");
            return false;
        }

        if (checkDuplicate(prescription.getPatientId(), prescription.getPrescriptionId(), prescription.getMedicationId())) {
            System.out.println("Error: Duplicate prescription.");
            return false;
        }

        // add to list
        prescriptionList.add(prescription);
        System.out.println("Prescription added successfully: " + prescription);
        return true;

    }

    // get electronic prescription (8.3.2)
    public boolean readNewPrescription(Prescription prescription) {
        if (checkPrescriptionFields(prescription)) {
            addNewPresctiption(new Prescription(
                    prescription.getPrescriptionId(),
                    prescription.getMedicationId(),
                    prescription.getPatientId(),
                    prescription.getDosage(),
                    "Pending",
                    prescription.getNotes()));

            return true;
        }
        return false;
    }

    // check that all fields are filled (8.3.3)
    public boolean checkPrescriptionFields(Prescription prescription) {

        return prescription.getPrescriptionId() != 0
                && prescription.getPatientId() != 0
                && prescription.getDosage() != null
                && prescription.getNotes() != null;
    }

    // checks for medication interactions (8.3.5)
    public boolean checkMedicationInteractions(int medicationId, List<String> currentMedications) {
        return true;

        //need to create a file that has known medication interactions and method to fetch that list
    }

    // check allergies of patient (8.3.6)
    public boolean checkAllergies(Patient patient, int medicationId) {
        return true;

        //need to create list of known allergic reactions with medications and a method to get that list
    }

    // track current status of prescription (8.3.8)
    public String trackStatus(int prescriptionId, String status) {
        //TODO: create 
    }

    // check that there is enough inventory (8.3.9)
    public boolean checkInventory(int medicationId, int quantity) {

    }

    // check expiration date (8.3.9)
    public boolean checkExpiration(int medicationId) {

    }

    // collect all prescription history of a patient (8.3.10)
    public void getPrescriptionHistory(int patientId) {

    }

    // notify patients (8.3.11)
    public void sendNotification(int patientId, int prescriptionId) {

    }

    // ensure no duplicate prescription (8.3.13)
    public boolean checkDuplicate(int patientId, int prescriptionId, int medicationId) {
        for (Prescription currentPrescription : prescriptionList) {
            if (existingPrescription.getPatientId() == patientId
                    && existingPrescription.getPrescriptionId() == prescriptionId
                    && existingPrescription.getMedicationId() == medicationId) {
                return true; // duplicate is found
            }
        }

        return false; // no duplicate found
    }

    // record pickup confirmation
    public boolean confirmPickup(int prescriptionId) {

    }

}
