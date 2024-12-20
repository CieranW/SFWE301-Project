package PrescriptionProcessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrescriptionService {

    private List<Prescription> prescriptionList = new ArrayList<>();
    private File medicineListFile = new File("Medicine_List_v2.csv");

    public int readMedicineInteractionFile(File medicineListFile, List<String> currentMedications, int medicationId) {
        // Read csv file
        // Return number of interactions found
        int interactionCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(medicineListFile))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length == 9) {
                String med1 = values[0];
                int med1Id = Integer.parseInt(values[1]);
                String[] med2Array = values[4].split(";");
                String[] med2IdArray = values[5].split(";");
                List<String> med2List = Arrays.asList(med2Array);
                List<Integer> med2IdList = new ArrayList<>();
                for (String med2IdStr : med2IdArray) {
                med2IdList.add(Integer.parseInt(med2IdStr));
                }

                if (med1Id == medicationId || med2IdList.contains(medicationId)) {
                if (currentMedications.contains(med1) || !Collections.disjoint(currentMedications, med2List)) {
                    interactionCount++;
                }
                }
            }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return interactionCount;
    }

    public int readAllergyInteractionFile(List<String> allergies, int medicationId) {
        // Read csv file. Columns are Allergy, AllergyID, Medication, MedicationID
        // Return number of interactions found
        int interactionCount = 0;
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(medicineListFile))) {
            String header = br.readLine();
            // System.out.println("Skipped header: " + header);
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 9) {
                    int medicineId = Integer.parseInt(values[1]);
                    String[] allergyArray = values[2].split(";");
                    List<String> allergyList = Arrays.asList(allergyArray);

                    if (medicineId == medicationId) {
                        for (String allergyItem : allergyList) {
                            if (allergies.contains(allergyItem)) {
                                interactionCount++;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return interactionCount;
    }

    // Add new prescription order (8.3.1)
    public boolean addNewPresctiption(Prescription prescription) {
        // Validate prescription fields
        if (!checkPrescriptionFields(prescription)) {
            System.out.println("Error: Prescription fields are invalid.");
            return false;
        }

        if (checkDuplicate(prescription.getPatientId(), prescription.getMedicationId(), prescriptionList)) {
            System.out.println("Error: Duplicate prescription.");
            return false;
        }

        // Add to list
        prescriptionList.add(prescription);
        System.out.println("Prescription added successfully: " + prescription);
        return true;

    }

    // Get electronic prescription (8.3.2)
    public boolean readNewPrescription(Prescription prescription) {
        if (checkPrescriptionFields(prescription)) {
            addNewPresctiption(new Prescription(
                    prescription.getMedicationName(),
                    prescription.getMedicationId(),
                    prescription.getPatientName(),
                    prescription.getPatientId(),
                    prescription.getDosage(),
                    prescription.getNumDays(),
                    prescription.getDailyIntake(),
                    "Pending Approval",
                    prescription.getNotes()));

            return true;
        }
        return false;
    }

    // Check that all fields are filled (8.3.3)
    public static boolean checkPrescriptionFields(Prescription prescription) {
        // Check if all fields of the prescription are filled
        if (prescription.getMedicationName().isEmpty() ||
                prescription.getPatientName().isEmpty() ||
                prescription.getStatus().isEmpty() ||
                prescription.getNotes().isEmpty()) {
            return false; // Some fields are missing
        }

        // Add any other validation logic (e.g., checking for valid numbers)
        if (prescription.getMedicationId() <= 0 ||
                prescription.getPatientId() <= 0 ||
                prescription.getDosage() <= 0 ||
                prescription.getNumDays() <= 0 ||
                prescription.getDailyIntake() <= 0) {
            return false; // Invalid data
        }

        return true; // All fields are filled and valid
    }

    // Checks for medication interactions (8.3.5)
    public boolean checkMedicationInteractions(int medicationId, List<String> currentMedications) {
        // Reads the medication interaction file and checks for interactions
        int interactionCount = readMedicineInteractionFile(medicineListFile, currentMedications, medicationId);

        // If the count is greater than 0, interactions are found
        if (interactionCount > 0) {
            System.out.println("Interaction found: " + interactionCount);
            return true;
        } else {
            System.out.println("No interactions found.");
            return false;
        }
    }

    // Check allergies of patient (8.3.6)
    public void checkAllergies(List<String> allergies, int medicationId) {
        // Read allergies file and compare both patient allergies and medication allergies
        int allgeryCount = readAllergyInteractionFile(allergies, medicationId);

        // If the count is greater than 0, interactions are found
        if (allgeryCount > 0) {
            System.out.println("Allergy found: " + allgeryCount);
        } else {
            System.out.println("No allergies found.");
        }
    }

    // track current status of prescription (8.3.8)
    public void updateStatus(List<Prescription> prescriptionList, int prescriptionId, String newStatus) {
        for (Prescription currentPrescription : prescriptionList) {
            if (currentPrescription.getPrescriptionId() == prescriptionId) {
                currentPrescription.setStatus(newStatus);
            }
        }
    }

    // Check that there is enough inventory (8.3.9)
    public boolean checkInventory(int medicationId, int dosage) {
        // Access inventory stock amount
        // Calculate total mediciation patient needs
        // Ensure total number is less than inventory stock
        try (BufferedReader reader = new BufferedReader(new FileReader(medicineListFile))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                // Assume the CSV format is: MedicationID,CurrentInventory
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[1].trim());
                int currentInventory = Integer.parseInt(parts[6].trim());
                
                if (id == medicationId) {
                    // Check if the inventory can cover the required medication
                    return currentInventory >= dosage;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Log exception for debugging purposes
        }
        return false; // Return false if medication ID not found or error occurs
    }

    // Check expiration date (8.3.9)
    public boolean checkExpiration(int medicationId, int numDays) {
        // Compares current date with expiration date
        // Could also calculate the number of days left until expiration or use the number of days needed to take the medication as a reference
        // Returns true if the medication is not expired, false otherwise
        try (BufferedReader reader = new BufferedReader(new FileReader(medicineListFile))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                // Assume the CSV format is: MedicationID,ExpirationDate (YYYY-MM-DD)
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[1].trim());
                String expirationDateStr = parts[7].trim();
                
                if (id == medicationId) {
                    // Parse expiration date
                    LocalDate expirationDate = LocalDate.parse(expirationDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

                    // Get current date and prescription end date
                    LocalDate currentDate = LocalDate.now();
                    LocalDate prescriptionEndDate = currentDate.plusDays(numDays);

                    // Check validity
                    boolean isValid = !currentDate.isAfter(expirationDate) && !prescriptionEndDate.isAfter(expirationDate);
                    return isValid;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return false; // Return false if medication ID not found or error occurs
    }

    // Collect all prescription history of a patient (8.3.10)
    public List<Prescription> getPrescriptionHistory(int patientId, List<Prescription> prescriptionList) {
        // Uses patient ID to retrieve all prescriptions with matching patient ID from the prescription map
        // Puts all matches into a new prescription array, returns array
        List<Prescription> matchedPrescriptions = new ArrayList<>();

        // Iterate over the prescription map to find matches
        for (Prescription prescription : prescriptionList) {
            if (prescription.getPatientId() == patientId) {
                matchedPrescriptions.add(prescription); // Add to the list
            }
        }
        // Return the list of matched prescriptions
        return matchedPrescriptions;
    }

    // Notify patients (8.3.11)
    public void sendNotification(int patientId, int prescriptionId) {

    }

    // Check for controlled substances (8.3.12)
    public boolean checkControlledSubstance(int medicationId) {
    // Read the medicine list file and check if the medication is a controlled substance

    try (BufferedReader br = new BufferedReader(new FileReader(medicineListFile))) {
        String line;
        br.readLine(); // Skip header

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");

            // Validate row structure
            if (values.length < 9) {
                System.err.println("Skipping malformed row: " + Arrays.toString(values));
                continue;
            }

            try {
                int medId = Integer.parseInt(values[1].trim());
                int controlledSubstance = Integer.parseInt(values[8].trim());

                if (medId == medicationId) {
                    return controlledSubstance == 1;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid Medication ID in row: " + Arrays.toString(values));
                continue;
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
        e.printStackTrace();
    }

    System.out.println("Medication ID " + medicationId + " not found.");
    return false; // Default to false if medication ID is not found
}

    // Ensure no duplicate prescription (8.3.13)
    public boolean checkDuplicate(int patientId, int medicationId, List<Prescription> prescriptionList) {
        for (Prescription currentPrescription : prescriptionList) {
            if (currentPrescription.getPatientId() == patientId && currentPrescription.getMedicationId() == medicationId) {
                System.out.println("Duplicate prescription found: " + currentPrescription);
                return true;
            }
        }
        System.out.println("No duplicate prescriptions found.");
        return false;
    }

    // Add notes to prescription (8.3.14)
    public void addNotes(List<Prescription> prescriptionList, int prescriptionId, String notes) {
        for (Prescription currentPrescription : prescriptionList) {
            if (currentPrescription.getPrescriptionId() == prescriptionId) {
                currentPrescription.setNotes(notes);
            }
        }
    }

}
