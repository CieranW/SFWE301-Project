import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import Patient.Patient;
import PrescriptionProcessing.Prescription;
import PrescriptionProcessing.PrescriptionTest;

public class Test {
    public static void main(String[] args) {

        // Method to initilize Prescription class with the current prescription.csv
        String prescriptionCsvFile = "prescription.csv";
        String prescriptionLine;
        String prescriptionCsvSplitBy = ",";
        int prescriptionId = 20000;
        List<Prescription> prescriptions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(prescriptionCsvFile))) {
            // Skip the header row
            br.readLine();

            while ((prescriptionLine = br.readLine()) != null) {
                // Split each line by comma
                String[] data = prescriptionLine.split(prescriptionCsvSplitBy);

                // Create a Prescription object and add it to the list
                Prescription newPrescription = new Prescription(
                    data[0],                   // Patient Name
                    Integer.parseInt(data[1]), // Patient ID
                    data[2],                   // Medication Name
                    Integer.parseInt(data[3]), // Medication ID
                    Integer.parseInt(data[4]), // Dosage
                    Integer.parseInt(data[5]), // Number of Days
                    Integer.parseInt(data[6]), // Daily Intake
                    data[7],                   // Status
                    data[8]                    // notes
                    );
                newPrescription.setPrescriptionId(prescriptionId);
                prescriptionId = newPrescription.getPrescriptionId();

                prescriptions.add(newPrescription);
            }

            // Print all prescriptions
            /*for (Prescription prescription : prescriptions) {
                System.out.println(prescription);
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Method to initilize Patient class with the current patient.csv
        String PatientCsvFile = "Patient.csv";
        String patientLine;
        String patientCsvSplitBy = ",";

        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PatientCsvFile))) {
            // Skip the header row
            br.readLine();

            while ((patientLine = br.readLine()) != null) {
                // Split each line by comma
                String[] data = patientLine.split(patientCsvSplitBy);

                // Create a Patient object and add it to the list
                patients.add(new Patient(
                        data[0], // Patient Name
                        Integer.parseInt(data[1]), // Patient ID
                        Arrays.asList(data[2].split(";")), // Allergies (List<String>)
                        Arrays.stream(data[3].split(";")) // Allergy IDs (List<Integer>)
                              .map(Integer::parseInt)
                              .collect(Collectors.toList()),
                        Arrays.asList(data[4].split(";")), // Current Medications (List<String>)
                        Arrays.stream(data[5].split(";")) // Medication IDs (List<Integer>)
                              .map(Integer::parseInt)
                              .collect(Collectors.toList())
                ));
            }

            // Print all patients
            // for (Patient patient : patients) {
            //     System.out.println(patient);
            // }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Test 1 - Manually Enter Prescription Details 
        // String newPrescriptionAdded = PrescriptionTest.testEnterPrescription();
        // System.out.println(newPrescriptionAdded);

        // Test 2 - Accept and Validate Electronic Prescriptions
        // boolean readCSV = PrescriptionTest.acceptEPrescription();
        // if (readCSV) {
        //     System.out.println("Prescriptions processed successfully.");
        // } else {
        //     System.out.println("There was an error processing the prescriptions. Please resend the CSV.");
        // }

        // Test 3 - Conflicting Information
        
        
        // Test 4 -
    }

}
