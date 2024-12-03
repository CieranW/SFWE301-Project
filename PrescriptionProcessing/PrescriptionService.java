package PrescriptionProcessing;

import java.util.List;
import java.util.ArrayList;
import PrescriptionProcessing.Prescription;
import Patient.Patient;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrescriptionService {

    private List<Prescription> prescriptionList = new ArrayList<>();
    private File medicationInteractionFile;

    public int readMedicineInteractionFile(File medicationInteractionFile, List<String> currentMedications, int medicationId) {
        // Read csv file. Columns are Med1, Med1ID, Med2, Med2ID, Interaction
        // Return number of interactions found
        int interactionCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(medicationInteractionFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 5) {
                    String med1 = values[0];
                    int med1Id = Integer.parseInt(values[1]);
                    String med2 = values[2];
                    int med2Id = Integer.parseInt(values[3]);
                    String interaction = values[4];

                    if (med1Id == medicationId || med2Id == medicationId) {
                        if (currentMedications.contains(med1) || currentMedications.contains(med2)) {
                            interactionCount++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return interactionCount;
    }

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
        int interactionCount = readMedicineInteractionFile(medicationInteractionFile, currentMedications, medicationId);
        if (interactionCount > 0) {
            System.out.println("Interaction found: " + interactionCount);
            return true;
        } else {
            System.out.println("No interactions found.");
            return false;
        }
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
