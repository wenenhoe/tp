package meditracker.command;

import meditracker.DailyMedicationManager;
import meditracker.exception.ArgumentNotFoundException;
import meditracker.medication.Medication;
import meditracker.medication.MedicationManager;
import meditracker.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModifyCommandTest {
    @Test
    void execute_inOrderArgument_expectMedicationModified() throws ArgumentNotFoundException {
        MedicationManager medicationManager = new MedicationManager();
        Medication medication = new Medication(
                "Medication_A",
                60.0,
                500.0,
                null,
                null,
                null,
                "01/07/25",
                "morning",
                "cause_dizziness",
                "");
        medicationManager.addMedication(medication);
        DailyMedicationManager dailyMedicationManager = new DailyMedicationManager(medicationManager);

        String newName = "Medication_B";
        String inputString = "modify -l 1 -n " + newName;
        ModifyCommand command = new ModifyCommand(inputString);
        Ui ui = new Ui();
        command.execute(medicationManager, dailyMedicationManager, ui);

        Medication updatedMedication = medicationManager.getMedication(1);
        assertTrue(updatedMedication.getName().equals(newName));
    }

    @Test
    void execute_outOfOrderArgument_expectMedicationModified() throws ArgumentNotFoundException {
        MedicationManager medicationManager = new MedicationManager();
        Medication medication = new Medication(
                "Medication_A",
                60.0,
                500.0,
                null,
                null,
                null,
                "01/07/25",
                "morning",
                "cause_dizziness",
                "");
        medicationManager.addMedication(medication);
        DailyMedicationManager dailyMedicationManager = new DailyMedicationManager(medicationManager);

        String newName = "Medication_B";
        String inputString = String.format("modify -n %s -l 1", newName);
        ModifyCommand command = new ModifyCommand(inputString);
        Ui ui = new Ui();
        command.execute(medicationManager, dailyMedicationManager, ui);

        Medication updatedMedication = medicationManager.getMedication(1);
        assertTrue(updatedMedication.getName().equals(newName));
    }
}
