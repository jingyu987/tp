package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPrescriptionAtIndex;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PRESCRIPTION_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRESCRIPTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PRESCRIPTION;
import static seedu.address.testutil.TypicalPrescriptions.getTypicalPrescriptionList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.prescription.Prescription;


/**
 * Contains integration tests (interaction with the Model) for {@code DeleteCommand}.
 */
public class DeleteCommandIntegrationTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrescriptionList(),
                getTypicalPrescriptionList(),
                new UserPrefs());
        expectedModel = new ModelManager(model.getPrescriptionList(),
                model.getCompletedPrescriptionList(),
                new UserPrefs());
    }
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Prescription prescriptionToDelete = model.getFilteredPrescriptionList()
                .get(INDEX_FIRST_PRESCRIPTION.getZeroBased());
        DeleteCommand deletePrescriptionCommand = new DeleteCommand(INDEX_FIRST_PRESCRIPTION);

        String expectedMessage = String.format(MESSAGE_DELETE_PRESCRIPTION_SUCCESS,
                Messages.format(prescriptionToDelete));

        expectedModel.deletePrescription(prescriptionToDelete);

        assertCommandSuccess(deletePrescriptionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPrescriptionList().size() + 1);
        DeleteCommand deletePrescriptionCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deletePrescriptionCommand, model,
                Messages.MESSAGE_INVALID_PRESCRIPTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPrescriptionAtIndex(model, INDEX_FIRST_PRESCRIPTION);

        Prescription prescriptionToDelete = model.getFilteredPrescriptionList()
                .get(INDEX_FIRST_PRESCRIPTION.getZeroBased());
        DeleteCommand deletePrescriptionCommand = new DeleteCommand(INDEX_FIRST_PRESCRIPTION);

        String expectedMessage = String.format(MESSAGE_DELETE_PRESCRIPTION_SUCCESS,
                Messages.format(prescriptionToDelete));

        expectedModel.deletePrescription(prescriptionToDelete);
        showNoPrescription(expectedModel);

        assertCommandSuccess(deletePrescriptionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPrescriptionAtIndex(model, INDEX_FIRST_PRESCRIPTION);

        Index outOfBoundIndex = INDEX_SECOND_PRESCRIPTION;
        // ensures that outOfBoundIndex is still in bounds of prescriptions list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPrescriptionList().getPrescriptionList().size());

        DeleteCommand deletePrescriptionCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deletePrescriptionCommand, model,
                Messages.MESSAGE_INVALID_PRESCRIPTION_DISPLAYED_INDEX);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPrescription(Model model) {
        model.updateFilteredPrescriptionList(p -> false);

        assertTrue(model.getFilteredPrescriptionList().isEmpty());
    }

}
