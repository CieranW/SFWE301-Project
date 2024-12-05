package PrescriptionProcessing;

import Patient.Patient;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
// PrescriptionTest class
public class PrescriptionTest {

    // Test Procedure 1 (Manually Enter Prescription Details)
    public static String testEnterPrescription(List<Prescription> prescriptions, int prescriptionId, List<Patient> patients) {
        Scanner scanner = new Scanner(System.in);
        String medicationName, patientName, status, notes;
        int medicationId, patientId, dosage, numDays, dailyIntake;

        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Enter a new prescription. Enter relivant information into associated fields.");

                System.out.println("Medication Name: ");
                medicationName = scanner.nextLine();

                System.out.println("MedicationId: ");
                medicationId = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Patient Name: ");
                patientName = scanner.nextLine();
                //scanner.nextLine();

                System.out.println("Patient ID: ");
                patientId = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Dosage: ");
                dosage = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Number of Days: ");
                numDays = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Daily Intake: ");
                dailyIntake = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Status: ");
                status = scanner.nextLine();
                //scanner.nextLine();

                System.out.println("Notes: ");
                notes = scanner.nextLine();

                Prescription newPrescription = new Prescription(
                    patientName,
                    patientId,
                    medicationName,
                    medicationId,
                    dosage,
                    numDays,
                    dailyIntake,
                    status,
                    notes
                );

                newPrescription.setPrescriptionId(prescriptionId);
                prescriptionId = newPrescription.getPrescriptionId();
                
                // Call Test 3 to check for conflicting information
                conflictingInformation(patients, prescriptions, patientId, medicationId, numDays, prescriptionId, dosage);
                
                prescriptions.add(newPrescription); 
                validInput = true;

            } catch (InputMismatchException e) {
                System.out.println("Error: Incorrect variable type. \nPlease try again\n");
                scanner.nextLine();
            }
        }

        //scanner.close();
        return "New prescription saved successfully";
    }

    // Test 2 (Accept and Validate Electronic Prescriptions)
    public static boolean acceptEPrescription(){
        String prescriptionCsvFile = "ePrescription.csv";
        String prescriptionLine;
        String prescriptionCsvSplitBy = ",";
        
        // List to hold valid prescriptions
        List<Prescription> validPrescriptions = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(prescriptionCsvFile))) {
            // Skip the header row
            br.readLine();
    
            while ((prescriptionLine = br.readLine()) != null) {
                // Split each line by comma
                String[] data = prescriptionLine.split(prescriptionCsvSplitBy);
    
                // Create a Prescription object from CSV data
                Prescription prescription = new Prescription(
                    data[0],                   // Patient Name
                    Integer.parseInt(data[1]), // Patient ID
                    data[2],                   // Medication Name
                    Integer.parseInt(data[3]), // Medication ID
                    Integer.parseInt(data[4]), // Dosage
                    Integer.parseInt(data[5]), // Number of Days
                    Integer.parseInt(data[6]), // Daily Intake
                    data[7],                   // Status
                    data[8]                    // Notes
                );
    
                // Check if the prescription fields are valid
                if (PrescriptionService.checkPrescriptionFields(prescription)) {
                    // If valid, add to the list of valid prescriptions
                    validPrescriptions.add(prescription);
                } else {
                    // If invalid, print an error message and prompt the user to send a new file
                    System.out.println("Error: Prescription has missing or invalid fields.");
                    System.out.println("Please send a new file with complete and correct data.");
                    return false;
                }
            }
    
            // If all prescriptions are valid, proceed to save or process them
            // For now, print all valid prescriptions
            System.out.println("Valid prescriptions:");
            for (Prescription prescription : validPrescriptions) {
                System.out.println(prescription);
            }
    
            // You can add logic here to save the valid prescriptions or process them further
    
            return true; // Return true when all prescriptions are valid
    
        } catch (IOException e) {
            System.out.println("Error: Could not read the CSV file.");
            e.printStackTrace();
            return false; // Return false if there's an error reading the file
        }
    }

    // Test 3 (Conflicting Information in Prescriptions)
    // Will need patient allergies, current medications, and prescription details
    public static void conflictingInformation(List<Patient> patients, List<Prescription> prescriptions, int patientId, int medicationId, int numDays, int prescriptionId, int dosage) {
        // Check if patient is allergic to the medication
        // Check if prescription is duplicate
        // Check if inventory is sufficient (calculate with dosage)
        // Check if medication has interaction with other medication
        // Check if prescription is expired or will expire during the treatment period
        // Check if prescription is controlled substance
        System.out.println("Checking for conflicting information in prescriptions...");
        PrescriptionService prescriptionService = new PrescriptionService();
        
        for (Patient patient : patients) {
            if (patient.getPatientId() == patientId) {
                System.out.println("Patient: " + patient.getPatientName());
                        
                // Checks for allergies
                prescriptionService.checkAllergies(patient.getAllergies(), medicationId);
                
                // Checks for duplicate prescriptions
                prescriptionService.checkDuplicate(patientId, medicationId, prescriptions);

                // Checks for sufficient inventory
                prescriptionService.checkInventory(medicationId, dosage, numDays);

                // Checks for medication interactions
                prescriptionService.checkMedicationInteractions(medicationId, patient.getCurrentMedications());

                // Checks for prescription expiration
                prescriptionService.checkExpiration(medicationId, numDays);

                // Checks for controlled substances
                prescriptionService.checkControlledSubstance(medicationId);
            }
        }

    }
}
