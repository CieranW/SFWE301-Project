package PrescriptionProcessing;

import java.io.File;
import java.util.Arrays;
import java.util.List;

// Importing required classes
import Patient.Patient;
import PrescriptionProcessing.Prescription;
import PrescriptionProcessing.PrescriptionService;


// PrescriptionTest class
public class PrescriptionTest {

    // Main method
    public static void main(String[] args) {

        // New instance of PrescriptionService
        PrescriptionService service = new PrescriptionService();

        // Create some mock prescriptions and other required data
        Prescription prescription1 = new Prescription(20001, "Alprazolam", 30001, "Tonya Miller", 10008, 1, 10, "Take twice a day", "Pending Approval");
        Prescription prescription2 = new Prescription(20002, "Amoxicillin", 30002, "Carla Ramirez", 10026, 2, 14, "Take only at night", "Pending Approval");
        
        // Test 1: Add New Prescription
        boolean addResult = service.addNewPresctiption(prescription1);
        System.out.println("Add Prescription Result: " + addResult);
        
        // Test 2: Check for medication interactions (mock some current medications)
        List<String> currentMedications = Arrays.asList("Aspirin", "Lisinopril");
        boolean interactionResult = service.checkMedicationInteractions(30001, currentMedications); // Check interactions with Alprazolam
        System.out.println("Medication Interaction Result: " + interactionResult);
        
        // Test 3: Check for allergies (mock some allergies)
        List<String> allergies = Arrays.asList("lactose", "gluten");
        boolean allergyResult = service.checkAllergies(allergies, 30001, new File("path_to_allergies_file.csv")); // You need a mock file here
        System.out.println("Allergy Interaction Result: " + allergyResult);
        
        // Test 4: Track status of a prescription
        String status = service.trackStatus(20001, "Approved");
        System.out.println("Track Prescription Status: " + status);
        
        // Test 5: Check Inventory (you need a mock inventory file)
        boolean inventoryCheck = service.checkInventory(new File("path_to_inventory_file.csv"), 30001, 1, 10);
        System.out.println("Inventory Check Result: " + inventoryCheck);
        
        // Test 6: Check expiration (again, you need a mock inventory file with expiration date)
        boolean expirationCheck = service.checkExpiration(new File("path_to_inventory_file.csv"), 30001, 10);
        System.out.println("Expiration Check Result: " + expirationCheck);
        
        // Test 7: Get Prescription History (for patient ID 10008)
        List<Prescription> history = service.getPrescriptionHistory(10008);
        System.out.println("Prescription History: " + history);
        
        // Test 8: Check for controlled substance
        boolean controlledSubstanceCheck = service.checkControlledSubstance(30001, new File("path_to_medicine_list_file.csv"));
        System.out.println("Controlled Substance Check Result: " + controlledSubstanceCheck);
        
        // Test 9: Check for duplicate prescription
        boolean duplicateCheck = service.checkDuplicate(10008, 20001, 30001); // Duplicate check for same patient and prescription ID
        System.out.println("Duplicate Check Result: " + duplicateCheck);
        
        // Test 10: Add notes to prescription
        boolean addNotesResult = service.addNotes(20001, "Patient needs to follow up in 2 weeks.");
        System.out.println("Add Notes Result: " + addNotesResult);
    }
}
