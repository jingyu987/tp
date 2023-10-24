package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOSAGE_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREQUENCY_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPrescriptionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRESCRIPTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PRESCRIPTION;
import static seedu.address.testutil.TypicalPrescriptions.getTypicalPrescriptionList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPrescriptionDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PrescriptionList;
import seedu.address.model.UserPrefs;
import seedu.address.model.prescription.Prescription;
import seedu.address.testutil.EditPrescriptionDescriptorBuilder;
import seedu.address.testutil.PrescriptionBuilder;

public class EditCommandTest {
    private Model model = new ModelManager(getTypicalPrescriptionList(), getTypicalPrescriptionList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Prescription editedPrescription = new PrescriptionBuilder().build();
        EditPrescriptionDescriptor descriptor = new EditPrescriptionDescriptorBuilder(editedPrescription).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PRESCRIPTION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PRESCRIPTION_SUCCESS,
                Messages.format(editedPrescription));

        Model expectedModel = new ModelManager(new PrescriptionList(model.getPrescriptionList()),
                new PrescriptionList(getTypicalPrescriptionList()), new UserPrefs());
        expectedModel.setPrescription(model.getFilteredPrescriptionList().get(0), editedPrescription);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPrescription = Index.fromOneBased(model.getFilteredPrescriptionList().size());
        Prescription lastPrescription = model.getFilteredPrescriptionList().get(indexLastPrescription.getZeroBased());

        PrescriptionBuilder prescriptionInList = new PrescriptionBuilder(lastPrescription);
        Prescription editedPrescription = prescriptionInList.withName(VALID_NAME_PROPRANOLOL)
                .withDosage(VALID_DOSAGE_PROPRANOLOL)
                .withFrequency(VALID_FREQUENCY_PROPRANOLOL).build();

        EditPrescriptionDescriptor descriptor = new EditPrescriptionDescriptorBuilder().withName(VALID_NAME_PROPRANOLOL)
                .withDosage(VALID_DOSAGE_PROPRANOLOL).withFrequency(VALID_FREQUENCY_PROPRANOLOL).build();
        EditCommand editCommand = new EditCommand(indexLastPrescription, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PRESCRIPTION_SUCCESS,
                Messages.format(editedPrescription));

        Model expectedModel = new ModelManager(new PrescriptionList(model.getPrescriptionList()),
                new PrescriptionList(getTypicalPrescriptionList()), new UserPrefs());

        expectedModel.setPrescription(lastPrescription, editedPrescription);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PRESCRIPTION, new EditPrescriptionDescriptor());
        Prescription editedPrescription = model.getFilteredPrescriptionList()
                .get(INDEX_FIRST_PRESCRIPTION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PRESCRIPTION_SUCCESS,
                Messages.format(editedPrescription));

        Model expectedModel = new ModelManager(new PrescriptionList(model.getPrescriptionList()),
                new PrescriptionList(getTypicalPrescriptionList()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPrescriptionAtIndex(model, INDEX_FIRST_PRESCRIPTION);

        Prescription prescriptionInFilteredList = model.getFilteredPrescriptionList()
                .get(INDEX_FIRST_PRESCRIPTION.getZeroBased());
        Prescription editedPrescription = new PrescriptionBuilder(prescriptionInFilteredList)
                .withName("Paracetamol").build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PRESCRIPTION,
                new EditPrescriptionDescriptorBuilder().withName("Paracetamol").build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PRESCRIPTION_SUCCESS,
                Messages.format(editedPrescription));

        Model expectedModel = new ModelManager(new PrescriptionList(model.getPrescriptionList()),
                new PrescriptionList(getTypicalPrescriptionList()), new UserPrefs());
        expectedModel.setPrescription(model.getFilteredPrescriptionList().get(0), editedPrescription);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePrescriptionUnfilteredList_failure() {
        Prescription firstPrescription = model.getFilteredPrescriptionList()
                .get(INDEX_FIRST_PRESCRIPTION.getZeroBased());
        EditPrescriptionDescriptor descriptor = new EditPrescriptionDescriptorBuilder(firstPrescription).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PRESCRIPTION, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PRESCRIPTION);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPrescriptionAtIndex(model, INDEX_FIRST_PRESCRIPTION);

        // edit prescription in filtered list into a duplicate in prescription list
        Prescription prescriptionInList = model.getPrescriptionList().getPrescriptionList()
                .get(INDEX_SECOND_PRESCRIPTION.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PRESCRIPTION,
                new EditPrescriptionDescriptorBuilder(prescriptionInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PRESCRIPTION);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPrescriptionList().size() + 1);
        EditPrescriptionDescriptor descriptor = new EditPrescriptionDescriptorBuilder()
                .withName(VALID_NAME_PROPRANOLOL).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PRESCRIPTION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of prescription list
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPrescriptionAtIndex(model, INDEX_FIRST_PRESCRIPTION);
        Index outOfBoundIndex = INDEX_SECOND_PRESCRIPTION;
        // ensures that outOfBoundIndex is still in bounds of prescription list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPrescriptionList().getPrescriptionList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPrescriptionDescriptorBuilder().withName(VALID_NAME_PROPRANOLOL).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PRESCRIPTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PRESCRIPTION, DESC_ASPIRIN);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PRESCRIPTION, DESC_ASPIRIN)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PRESCRIPTION, DESC_PROPRANOLOL)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPrescriptionDescriptor editPrescriptionDescriptor = new EditPrescriptionDescriptor();
        EditCommand editCommand = new EditCommand(index, editPrescriptionDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPrescriptionDescriptor="
                + editPrescriptionDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }
}
