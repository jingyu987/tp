package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TakeCommand.MESSAGE_INSUFFICIENT_STOCK;
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

public class TakeCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrescriptionList(), getTypicalPrescriptionList(), new UserPrefs());
    }

    @Test
    public void execute_validDosesToTake_success() throws CommandException {
        PrescriptionList prescriptionList = new PrescriptionList();
        PrescriptionList completedPrescriptionList = new PrescriptionList();
        Prescription prescriptionToTake = new PrescriptionBuilder()
                .withConsumptionCount("0")
                .withStock("100")
                .build();
        prescriptionList.addPrescription(prescriptionToTake);
        Model model = new ModelManager(prescriptionList, completedPrescriptionList, new UserPrefs());
        Model expectedModel = new ModelManager(model.getPrescriptionList(), model.getCompletedPrescriptionList(),
                new UserPrefs());

        int initialStock = Integer.parseInt(prescriptionToTake.getTotalStock().get().toString());
        int dosesToTake = 1; //Valid number of doses

        TakeCommand takePrescriptionCommand = new TakeCommand(
                prescriptionToTake.getName(), dosesToTake);

        String expectedMessage = String.format(TakeCommand.MESSAGE_SUCCESS,
                                  prescriptionToTake.getName());
        Prescription expectedPrescription = expectedModel.getPrescriptionByName(prescriptionToTake.getName());
        expectedPrescription.getTotalStock().get().decrementCount(dosesToTake);
        expectedPrescription.getConsumptionCount().incrementCount(dosesToTake);
        expectedModel.updateFilteredPrescriptionList(new SameNamePredicate(prescriptionToTake.getName()));

        int newStock = Integer.parseInt(expectedModel.getPrescriptionByName(prescriptionToTake.getName())
                .getTotalStock().get().toString());

        assertCommandSuccess(takePrescriptionCommand, model, expectedMessage, expectedModel);

        assertEquals(initialStock - dosesToTake, newStock);
    }

    @Test
    public void execute_insufficientStock_throwsCommandException() throws CommandException {
        Prescription prescriptionToTake = model.getFilteredPrescriptionList()
                .get(INDEX_FIRST_PRESCRIPTION.getZeroBased());

        int initialStock = Integer.parseInt(prescriptionToTake.getTotalStock().get().toString());
        int dosesToTake = initialStock + 1; // More than available stock

        TakeCommand takePrescriptionCommand = new TakeCommand(
                prescriptionToTake.getName(), dosesToTake);

        assertCommandFailure(takePrescriptionCommand, model, MESSAGE_INSUFFICIENT_STOCK);

        // Ensure that the stock is not modified
        int newStock = Integer.parseInt(model.getPrescriptionByName(prescriptionToTake.getName())
                .getTotalStock().get().toString());
        assertEquals(initialStock, newStock);
    }

    @Test
    public void execute_invalidPrescription_throwsCommandException() {
        Prescription prescriptionToTake = new PrescriptionBuilder().withName("Invalid Name").build();
        int dosesToTake = 1; // A valid number of doses

        TakeCommand takePrescriptionCommand = new TakeCommand(
                prescriptionToTake.getName(), dosesToTake);

        assertCommandFailure(takePrescriptionCommand, model, TakeCommand.MESSAGE_PRESCRIPTION_NOT_FOUND);
    }

    @Test
    public void equals() {
        Name name1 = new Name("Aspirin");
        Name name2 = new Name("Panadol");
        int doses1 = 2;
        int doses2 = 1;

        TakeCommand command1 = new TakeCommand(name1, doses1);
        TakeCommand command2 = new TakeCommand(name1, doses1);
        TakeCommand command3 = new TakeCommand(name2, doses1);
        TakeCommand command4 = new TakeCommand(name1, doses2);

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
