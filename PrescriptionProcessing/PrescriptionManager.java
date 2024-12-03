package PrescriptionProcessing;

import java.io.*;
import java.util.*;

public class PrescriptionManager {
    public static void main(String[] args) {
        String csvFile = "Prescription.csv"; // Path to your file
        String line;
        String csvSplitBy = ","; // CSV delimiter
        HashMap<Integer, Prescription> prescriptionMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read the header line
            br.readLine();

            // Read each line
            while ((line = br.readLine()) != null) {
                // Split line into fields
                String[] fields = line.split(csvSplitBy);

                // Parse fields (ensure alignment with CSV structure)
                int prescriptionID = Integer.parseInt(fields[0].trim());
                String patientName = fields[1].trim();
                int patientID = Integer.parseInt(fields[2].trim());
                String medicationName = fields[3].trim();
                int medicationID = Integer.parseInt(fields[4].trim());
                String dosage = fields[5].trim();
                int numberOfDays = Integer.parseInt(fields[6].trim());
                String notes = fields[7].trim();
                String status = fields[8].trim();

                // Create a Prescription object
                Prescription prescription = new Prescription(prescriptionID, patientName, patientID,
                medicationName, medicationID, dosage,
                numberOfDays, notes, status);

                // Add to the map
                prescriptionMap.put(prescriptionID, prescription);
            }

            // Example: Print all prescriptions
            for (Map.Entry<Integer, Prescription> entry : prescriptionMap.entrySet()) {
                System.out.println(entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }
}
