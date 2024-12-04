package PrescriptionProcessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Importing required classes
import Patient.Patient;
import PrescriptionProcessing.Prescription;
import PrescriptionProcessing.PrescriptionService;


// PrescriptionTest class
public class PrescriptionTest {

    // Method to initilize Prescription class with the current prescription.csv
    public static void main(String[] args) {
        String prescriptionCsvFile = "prescription.csv";
        String line;
        String csvSplitBy = ",";

        List<Prescription> prescriptions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(prescriptionCsvFile))) {
            // Skip the header row
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Split each line by comma
                String[] data = line.split(csvSplitBy);

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
    }
}
