import Patient.Patient;
import PrescriptionProcessing.Prescription;
import PrescriptionProcessing.PrescriptionTest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                // Set the prescription ID and calculate the new ID
                newPrescription.setPrescriptionId(prescriptionId);
                prescriptionId = newPrescription.getPrescriptionId();

                prescriptions.add(newPrescription);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Test 1 - Manually Enter Prescription Details 
        String newPrescriptionAdded = PrescriptionTest.testEnterPrescription(prescriptions, prescriptionId, patients);
        System.out.println(newPrescriptionAdded);

        // Test 2 - Accept and Validate Electronic Prescriptions
        boolean readCSV = PrescriptionTest.acceptEPrescription();
        if (readCSV) {
            System.out.println("Prescriptions processed successfully.");
        } else {
            System.out.println("There was an error processing the prescriptions. Please resend the CSV.");
        }

        // Test 3 - Conflicting Information
        // Pass patient, prescription and medication lists to the method to check for conflicts 
        // Test 3 is called in Test 1
        
        // Test 4 - Updating Status of Prescriptions
        // Pass the list of prescriptions to the method to update the status
        PrescriptionTest.updatePrescriptionStatus(prescriptions);

        // Test 5 - Record all patient prescriptions
        // Pass the list of patients and prescriptions to the method to record all patient prescriptions
        PrescriptionTest.recordAllPatientPrescriptions(prescriptions);

        // Test 6 - Add notes to prescriptions
        // Pass the list of prescriptions to the method to add notes to prescriptions
        PrescriptionTest.addNotesToPrescriptions(prescriptions);

        // Output to a new csv file, one for prescriptions
        String outputPrescriptionCsvFile = "updated_prescriptions.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPrescriptionCsvFile))) {
            // Write the header row
            bw.write("Prescription ID,Patient Name,Patient ID,Medication Name,Medication ID,Dosage,Number of Days,Daily Intake,Status,Notes");
            bw.newLine();

            // Write each prescription to the file
            for (Prescription prescription : prescriptions) {
            bw.write(String.join(",",
                String.valueOf(prescription.getPrescriptionId()),
                prescription.getPatientName(),
                String.valueOf(prescription.getPatientId()),
                prescription.getMedicationName(),
                String.valueOf(prescription.getMedicationId()),
                String.valueOf(prescription.getDosage()),
                String.valueOf(prescription.getNumDays()),
                String.valueOf(prescription.getDailyIntake()),
                prescription.getStatus(),
                prescription.getNotes()
            ));
            bw.newLine();
            }

            System.out.println("Updated prescriptions have been written to " + outputPrescriptionCsvFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
