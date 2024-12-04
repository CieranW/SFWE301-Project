package PrescriptionProcessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Importing required classes
import Patient.Patient;
import PrescriptionProcessing.Prescription;
import PrescriptionProcessing.PrescriptionService;


// PrescriptionTest class
public class PrescriptionTest {

    public static void main(String[] args) {

        // Method to initilize Prescription class with the current prescription.csv
        String prescriptionCsvFile = "prescription.csv";
        String prescriptionLine;
        String prescriptionCsvSplitBy = ",";

        List<Prescription> prescriptions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(prescriptionCsvFile))) {
            // Skip the header row
            br.readLine();

            while ((prescriptionLine = br.readLine()) != null) {
                // Split each line by comma
                String[] data = prescriptionLine.split(prescriptionCsvSplitBy);

                // Create a Prescription object and add it to the list
                prescriptions.add(new Prescription(
                        Integer.parseInt(data[0]), // Prescription ID
                        data[1],                   // Patient Name
                        Integer.parseInt(data[2]), // Patient ID
                        data[3],                   // Medication Name
                        Integer.parseInt(data[4]), // Medication ID
                        Integer.parseInt(data[5]), // Dosage
                        Integer.parseInt(data[6]), // Number of Days
                        Integer.parseInt(data[7]), // Daily Intake
                        data[8],                   // Status
                        data[9]                    // notes
                ));
            }

            // Print all prescriptions
            for (Prescription prescription : prescriptions) {
                System.out.println(prescription);
            }

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
            for (Patient patient : patients) {
                System.out.println(patient);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
