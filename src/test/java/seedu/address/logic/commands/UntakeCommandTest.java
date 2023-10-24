package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UntakeCommand.MESSAGE_INSUFFICIENT_CONSUMPTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRESCRIPTION;
import static seedu.address.testutil.TypicalPrescriptions.getTypicalPrescriptionList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PrescriptionList;
import seedu.address.model.UserPrefs;
import seedu.address.model.prescription.Name;
import seedu.address.model.prescription.Prescription;
import seedu.address.model.prescription.SameNamePredicate;
import seedu.address.testutil.PrescriptionBuilder;

public class UntakeCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrescriptionList(), getTypicalPrescriptionList(), new UserPrefs());
    }

    @Test
    public void execute_validDosesToUntake_success() throws CommandException {
        PrescriptionList prescriptionList = new PrescriptionList();
        PrescriptionList completedPrescriptionList = new PrescriptionList();
        Prescription prescriptionToUntake = new PrescriptionBuilder()
                .withConsumptionCount("100")
                .withStock("100")
                .build();
        prescriptionList.addPrescription(prescriptionToUntake);
        Model model = new ModelManager(prescriptionList, completedPrescriptionList, new UserPrefs());
        Model expectedModel = new ModelManager(model.getPrescriptionList(), model.getCompletedPrescriptionList(),
                new UserPrefs());

        int initialStock = Integer.parseInt(prescriptionToUntake.getTotalStock().get().toString());
        int dosesToUntake = 1; //Valid number of doses

        UntakeCommand untakePrescriptionCommand = new UntakeCommand(
                prescriptionToUntake.getName(), dosesToUntake);

        String expectedMessage = String.format(UntakeCommand.MESSAGE_SUCCESS,
                prescriptionToUntake.getName());
        Prescription expectedPrescription = expectedModel.getPrescriptionByName(prescriptionToUntake.getName());
        expectedPrescription.getTotalStock().get().incrementCount(dosesToUntake);
        expectedPrescription.getConsumptionCount().decrementCount(dosesToUntake);
        expectedModel.updateFilteredPrescriptionList(new SameNamePredicate(prescriptionToUntake.getName()));

        int newStock = Integer.parseInt(expectedModel.getPrescriptionByName(prescriptionToUntake.getName())
                .getTotalStock().get().toString());

        assertCommandSuccess(untakePrescriptionCommand, model, expectedMessage, expectedModel);

        assertEquals(initialStock + dosesToUntake, newStock);
    }

    @Test
    public void execute_insufficientConsumption_throwsCommandException() throws CommandException {
        Prescription prescriptionToUntake = model.getFilteredPrescriptionList()
                .get(INDEX_FIRST_PRESCRIPTION.getZeroBased());

        int initialConsumption = Integer.parseInt(prescriptionToUntake.getConsumptionCount().toString());
        int dosesToUntake = initialConsumption + 1; // More than currently consumed

        UntakeCommand untakePrescriptionCommand = new UntakeCommand(
                prescriptionToUntake.getName(), dosesToUntake);

        assertCommandFailure(untakePrescriptionCommand, model, MESSAGE_INSUFFICIENT_CONSUMPTION);

        // Ensure that the consumption is not modified
        int newConsumption = Integer.parseInt(model.getPrescriptionByName(prescriptionToUntake.getName())
                .getConsumptionCount().toString());
        assertEquals(initialConsumption, newConsumption);
    }

    @Test
    public void execute_invalidPrescription_throwsCommandException() {
        Prescription prescriptionToUntake = new PrescriptionBuilder().withName("Invalid Name").build();
        int dosesToUntake = 1; // A valid number of doses

        UntakeCommand untakePrescriptionCommand = new UntakeCommand(
                prescriptionToUntake.getName(), dosesToUntake);

        assertCommandFailure(untakePrescriptionCommand, model, TakeCommand.MESSAGE_PRESCRIPTION_NOT_FOUND);
    }

    @Test
    public void equals() {
        Name name1 = new Name("Aspirin");
        Name name2 = new Name("Panadol");
        int doses1 = 2;
        int doses2 = 1;

        UntakeCommand command1 = new UntakeCommand(name1, doses1);
        UntakeCommand command2 = new UntakeCommand(name1, doses1);
        UntakeCommand command3 = new UntakeCommand(name2, doses1);
        UntakeCommand command4 = new UntakeCommand(name1, doses2);

        // Same object
        assertTrue(command1.equals(command1));

        // Test for equality
        assertTrue(command1.equals(command2)); // Same name and doses
        assertTrue(command2.equals(command1)); // Test for symmetry
        assertFalse(command1.equals(command3)); // Different names
        assertFalse(command1.equals(command4)); // Different doses

        // Test for non-equality
        assertFalse(command1.equals(null)); // Null object
    }
}
