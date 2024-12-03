package PrescriptionProcessing;

// Importing required classes
import Patient.Patient;
import PrescriptionProcessing.ElectronicPrescription;
import PrescriptionProcessing.Prescription;
import PrescriptionProcessing.PrescriptionService;

// Importing required libraries
import java.util.ArrayList;
import java.util.List;

// PrescriptionTest class
public class PrescriptionTest {

    // Main method
    public static void main(String[] args) {
        // Creating an object of PrescriptionService
        PrescriptionService prescriptionService = new PrescriptionService();
        // Creating an object of Prescription
        Prescription prescription = new Prescription(1, 1, 1, "10mg", "Pending", "Take with food");
        // Adding a new prescription
        prescriptionService.addNewPresctiption(prescription);
        // Creating an object of ElectronicPrescription
        ElectronicPrescription ePrescription = new ElectronicPrescription(2, 2, 2, "20mg", "Take before bed");
        // Importing electronic prescription
        prescriptionService.importElectronicPrescription(ePrescription);
        // Creating a list of current medications
        List<String> currentMedications = new ArrayList<>();
        currentMedications.add("Medication A");
        currentMedications.add("Medication B");
        // Checking medication interactions
        prescriptionService.checkMedicationInteractions(1, currentMedications);
    }
}
