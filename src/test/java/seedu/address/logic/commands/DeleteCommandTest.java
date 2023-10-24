package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static javafx.collections.FXCollections.observableArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PRESCRIPTION_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.CompletedPrescriptions.getCompletedPrescriptionList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRESCRIPTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PRESCRIPTION;
import static seedu.address.testutil.TypicalPrescriptions.getTypicalPrescriptionList;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PrescriptionList;
import seedu.address.model.ReadOnlyPrescriptionList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.prescription.Name;
import seedu.address.model.prescription.Prescription;
import seedu.address.testutil.PrescriptionBuilder;



/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePrescriptionCommand}.
 */
public class DeleteCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrescriptionList(),
                getCompletedPrescriptionList(),
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
    public void constructor_nullPrescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCommand(null));
    }

    @Test
    public void execute_prescriptionAcceptedByModel_deleteSuccessful() throws Exception {
        DeleteCommandTest.ModelStubAcceptingPrescriptionDeleted modelStub = new ModelStubAcceptingPrescriptionDeleted();
        Index validIndex = Index.fromOneBased(1);
        Prescription validPrescription = new PrescriptionBuilder().build();
        CommandResult commandResult = new DeleteCommand(validIndex).execute(modelStub);

        assertEquals(String.format(DeleteCommand.MESSAGE_DELETE_PRESCRIPTION_SUCCESS,
                Messages.format(validPrescription)),
                commandResult.getFeedbackToUser());

        assertEquals(Collections.emptyList(), modelStub.prescriptionsAdded);
    }

    @Test
    public void execute_outOfBoundary_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(5);
        DeleteCommand deleteCommand = new DeleteCommand(invalidIndex);
        ModelStub modelStub = new ModelStubAcceptingPrescriptionDeleted();

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PRESCRIPTION_DISPLAYED_INDEX, () -> deleteCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstPrescriptionCommand =
                new DeleteCommand(INDEX_FIRST_PRESCRIPTION);
        DeleteCommand deleteSecondPrescriptionCommand =
                new DeleteCommand(INDEX_SECOND_PRESCRIPTION);

        // same object -> returns true
        assertTrue(deleteFirstPrescriptionCommand.equals(deleteFirstPrescriptionCommand));

        // same values -> returns true
        DeleteCommand deleteFirstPrescriptionCommandCopy =
                new DeleteCommand(INDEX_FIRST_PRESCRIPTION);
        assertTrue(deleteFirstPrescriptionCommand.equals(deleteFirstPrescriptionCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstPrescriptionCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstPrescriptionCommand.equals(null));

        // different deleteCommand -> returns false
        assertFalse(deleteFirstPrescriptionCommand.equals(deleteSecondPrescriptionCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deletePrescriptionCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{toDelete=" + targetIndex + "}";
        assertEquals(expected, deletePrescriptionCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getPrescriptionListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPrescriptionListFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getCompletedPrescriptionListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCompletedPrescriptionListFilePath(Path completedPrescriptionListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPrescription(Prescription prescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPrescriptionList(ReadOnlyPrescriptionList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPrescriptionList getPrescriptionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPrescription(Prescription prescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePrescription(Prescription target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPrescription(Prescription target, Prescription editedPrescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Prescription> getFilteredPrescriptionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Prescription getPrescriptionByName(Name prescriptionName) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void updateFilteredPrescriptionList(Predicate<Prescription> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCompletedPrescriptionList(ReadOnlyPrescriptionList completedPrescriptionList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPrescriptionList getCompletedPrescriptionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCompletedPrescription(Prescription completedPrescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCompletedPrescription(Prescription target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCompletedPrescription(Prescription completedPrescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCompletedPrescription(Prescription target, Prescription editedPrescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Prescription> getFilteredCompletedPrescriptionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Prescription getCompletedPrescriptionByName(Name completedPrescriptionName) throws CommandException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCompletedPrescriptionList(Predicate<Prescription> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single prescription.
     */
    private class ModelStubWithPrescription extends DeleteCommandTest.ModelStub {
        private final Prescription prescription;

        ModelStubWithPrescription(Prescription prescription) {
            requireNonNull(prescription);
            this.prescription = prescription;
        }



        @Override
        public boolean hasPrescription(Prescription prescription) {
            requireNonNull(prescription);
            return this.prescription.isSamePrescription(prescription);
        }
    }

    /**
     * A Model stub that has dummy prescriptions to be removed
     */
    private class ModelStubAcceptingPrescriptionDeleted extends ModelStub {
        // populate the prescriptionsAdded with dummy prescriptions
        final ArrayList<Prescription> prescriptionsAdded = new ArrayList<>(Arrays.asList(
                new PrescriptionBuilder().build()
        ));

        @Override
        public boolean hasPrescription(Prescription prescription) {
            requireNonNull(prescription);
            return prescriptionsAdded.stream().anyMatch(prescription::isSamePrescription);
        }

        @Override
        public void deletePrescription(Prescription prescription) {
            requireNonNull(prescription);
            prescriptionsAdded.remove(prescription);
        }

        @Override
        public ReadOnlyPrescriptionList getPrescriptionList() {
            return new PrescriptionList();
        }

        @Override
        public ObservableList<Prescription> getFilteredPrescriptionList() {
            return observableArrayList(prescriptionsAdded);
        }
    }
}
