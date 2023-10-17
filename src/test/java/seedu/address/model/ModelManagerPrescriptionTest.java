package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ModelPrescription.PREDICATE_SHOW_ALL_PRESCRIPTIONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPrescriptions.ASPIRIN;
import static seedu.address.testutil.TypicalPrescriptions.PROPRANOLOL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.prescription.NameContainsKeywordsPredicate;
import seedu.address.testutil.PrescriptionListBuilder;

public class ModelManagerPrescriptionTest {

    private ModelManagerPrescription modelManager = new ModelManagerPrescription();

    @Test
    public void constructor() {
        assertEquals(new UserPrefsPrescription(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new PrescriptionList(), new PrescriptionList(modelManager.getPrescriptionList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefsPrescription userPrefs = new UserPrefsPrescription();
        userPrefs.setPrescriptionListFilePath(Paths.get("prescription/list/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefsPrescription oldUserPrefs = new UserPrefsPrescription(userPrefs);
        userPrefs.setPrescriptionListFilePath(Paths.get("new/prescription/list/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setPrescriptionListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPrescriptionListFilePath(null));
    }

    @Test
    public void setPrescriptionListFilePath_validPath_setsPrescriptionListFilePath() {
        Path path = Paths.get("prescription/list/file/path");
        modelManager.setPrescriptionListFilePath(path);
        assertEquals(path, modelManager.getPrescriptionListFilePath());
    }

    @Test
    public void hasPrescription_nullPrescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPrescription(null));
    }

    @Test
    public void hasPrescription_prescriptionNotInPrescriptionList_returnsFalse() {
        assertFalse(modelManager.hasPrescription(ASPIRIN));
    }

    @Test
    public void hasPrescription_prescriptionInPrescriptionList_returnsTrue() {
        modelManager.addPrescription(ASPIRIN);
        assertTrue(modelManager.hasPrescription(ASPIRIN));
    }

    @Test
    public void getFilteredPrescriptionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPrescriptionList().remove(0));
    }

    @Test
    public void equals() {
        PrescriptionList prescriptionList = new PrescriptionListBuilder()
            .withPrescription(ASPIRIN).withPrescription(PROPRANOLOL).build();
        PrescriptionList differentPrescriptionList = new PrescriptionList();
        UserPrefsPrescription userPrefs = new UserPrefsPrescription();

        // same values -> returns true
        modelManager = new ModelManagerPrescription(prescriptionList, userPrefs);
        ModelManagerPrescription modelManagerCopy = new ModelManagerPrescription(prescriptionList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManagerPrescription(differentPrescriptionList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ASPIRIN.getName().fullName.split("\\s+");
        modelManager.updateFilteredPrescriptionList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManagerPrescription(prescriptionList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPrescriptionList(PREDICATE_SHOW_ALL_PRESCRIPTIONS);

        // different userPrefs -> returns false
        UserPrefsPrescription differentUserPrefs = new UserPrefsPrescription();
        differentUserPrefs.setPrescriptionListFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManagerPrescription(prescriptionList, differentUserPrefs)));
    }
}