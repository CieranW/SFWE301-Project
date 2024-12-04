package PrescriptionProcessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 8) {
                    String med1 = values[0];
                    int med1Id = Integer.parseInt(values[1]);
                    String med2 = values[4];
                    int med2Id = Integer.parseInt(values[5]);

                    if (med1Id == medicationId || med2Id == medicationId) {
                        if (currentMedications.contains(med1) || currentMedications.contains(med2)) {
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

    public int readAllergyInteractionFile(File medicineListFile, List<String> allergies, int medicationId) {
        // Read csv file. Columns are Allergy, AllergyID, Medication, MedicationID
        // Return number of interactions found
        int interactionCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(medicineListFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 8) {
                    String allergy = values[2];
                    int allergyId = Integer.parseInt(values[3]);

                    if (allergyId == medicationId) {
                        if (allergies.contains(allergy)) {
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

    // Add new prescription order (8.3.1)
    public boolean addNewPresctiption(Prescription prescription) {
        // Validate prescription fields
        if (!checkPrescriptionFields(prescription)) {
            System.out.println("Error: Prescription fields are invalid.");
            return false;
        }

        if (checkDuplicate(prescription.getPatientId(), prescription.getPrescriptionId(), prescription.getMedicationId())) {
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
                    prescription.getPrescriptionId(),
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
    public boolean checkPrescriptionFields(Prescription prescription) {
        return prescription.getPrescriptionId() != 0
                && prescription.getPatientId() != 0
                && prescription.getDosage() != 0
                && prescription.getNotes() != null;
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
    public boolean checkAllergies(List<String> allergies, int medicationId, File allergiesFile) {
        // Read allergies file and compare both patient allergies and medication allergies
        int allgeryCount = readAllergyInteractionFile(medicineListFile, allergies, medicationId);

        // If the count is greater than 0, interactions are found
        if (allgeryCount > 0) {
            System.out.println("Interaction found: " + allgeryCount);
            return true;
        } else {
            System.out.println("No interactions found.");
            return false;
        }
    }

    // track current status of prescription (8.3.8)
    public String trackStatus(int prescriptionId, String status) {
        for (Prescription currentPrescription : prescriptionList) {
            if (currentPrescription.getPrescriptionId() == prescriptionId) {
                currentPrescription.setStatus(status);
                return status;
            }
        }

        return null;
    }

    // Check that there is enough inventory (8.3.9)
    public boolean checkInventory(File medicineListFile, int medicationId, int dosage, int numDays) {
        // Access inventory stock amount
        // Calculate total mediciation patient needs
        // Ensure total number is less than inventory stock
        try (BufferedReader reader = new BufferedReader(new FileReader(medicineListFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assume the CSV format is: MedicationID,CurrentInventory
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[1].trim());
                int currentInventory = Integer.parseInt(parts[6].trim());
                
                if (id == medicationId) {
                    // Calculate the total medication required
                    int totalMedicationRequired = dosage * numDays;

                    // Check if the inventory can cover the required medication
                    return currentInventory >= totalMedicationRequired;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Log exception for debugging purposes
        }
        return false; // Return false if medication ID not found or error occurs
    }

    // Check expiration date (8.3.9)
    public boolean checkExpiration(File medicineListFile, int medicationId, int numDays) {
        // Compares current date with expiration date
        // Could also calculate the number of days left until expiration or use the number of days needed to take the medication as a reference
        // Returns true if the medication is not expired, false otherwise
        try (BufferedReader reader = new BufferedReader(new FileReader(medicineListFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assume the CSV format is: MedicationID,ExpirationDate (YYYY-MM-DD)
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[1].trim());
                String expirationDateStr = parts[7].trim();
                
                if (id == medicationId) {
                    // Parse the expiration date
                    LocalDate expirationDate = LocalDate.parse(expirationDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

                    // Get the current date
                    LocalDate currentDate = LocalDate.now();

                    // Calculate the last valid date for the prescription
                    LocalDate prescriptionEndDate = currentDate.plusDays(numDays);

                    // Check if the medication will remain valid for the entire duration
                    return !currentDate.isAfter(expirationDate) && !prescriptionEndDate.isAfter(expirationDate);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return false; // Return false if medication ID not found or error occurs
    }

    // Collect all prescription history of a patient (8.3.10)
    public Prescription getPrescriptionHistory(int patientId, HashMap<Integer, Prescription> prescriptionMap) {
        // Uses patient ID to retrieve all prescriptions with matching patient ID from the prescription map
        // Puts all matches into a new prescription array, returns array
        for (Prescription prescription : prescriptionMap.values()) {
            if (prescription.getPatientId() == patientId) {
                return prescription;
            }
        }
        return null; // Return null if no matching prescription is found
    }

    // Notify patients (8.3.11)
    public void sendNotification(int patientId, int prescriptionId) {

    }

    // Check for controlled substances (8.3.12)
    public boolean checkControlledSubstance(int medicationId, File medicineListFile) {
        // Read the medicine list file and check if the medication is a controlled substance
        try (BufferedReader br = new BufferedReader(new FileReader(medicineListFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 14) {
                    int medId = Integer.parseInt(values[1]);
                    String controlledSubstance = values[9];

                    if (medId == medicationId) {
                        return controlledSubstance.equalsIgnoreCase("Yes");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return false;
    }

    // Ensure no duplicate prescription (8.3.13)
    public boolean checkDuplicate(int patientId, int prescriptionId, int medicationId) {
        for (Prescription currentPrescription : prescriptionList) {
            if (currentPrescription.getPatientId() == patientId
                    && currentPrescription.getMedicationId() == medicationId || currentPrescription.getPrescriptionId() == prescriptionId) {
                return true;
            }
        }
        return false;
    }

    // Add notes to prescription (8.3.14)
    public boolean addNotes(int prescriptionId, String notes) {
        for (Prescription currentPrescription : prescriptionList) {
            if (currentPrescription.getPrescriptionId() == prescriptionId) {
                currentPrescription.setNotes(notes);
                return true;
            }
        }
        return false;
    }

}
