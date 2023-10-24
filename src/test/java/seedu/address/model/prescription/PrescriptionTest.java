package seedu.address.model.prescription;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_PROPRANOLOL;
import static seedu.address.testutil.CompletedPrescriptions.ERGOTAMINE;
import static seedu.address.testutil.TypicalPrescriptions.ASPIRIN;
import static seedu.address.testutil.TypicalPrescriptions.PROPRANOLOL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PrescriptionBuilder;

public class PrescriptionTest {

    /*
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Prescription prescription = new PrescriptionBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> prescription.getTags().remove(0));
    }
     */

    @Test
    public void isEnded() {
        // completed prescription
        assertTrue(ERGOTAMINE.isEnded());

        // incomplete prescription
        assertFalse(ASPIRIN.isEnded());

        // prescription with no end date
        Prescription completedPrescription = new PrescriptionBuilder()
                .build();
        assertFalse(completedPrescription.isEnded());
    }
    @Test
    public void isSamePrescription() {
        // same object -> returns true
        assertTrue(ASPIRIN.isSamePrescription(ASPIRIN));

        // null -> returns false
        assertFalse(ASPIRIN.isSamePrescription(null));

        // same name and start date, all other attributes different -> returns true
        Prescription editedAspirin = new PrescriptionBuilder(ASPIRIN).withDosage("1").withEndDate("01/01/2027").build();
        assertTrue(ASPIRIN.isSamePrescription(editedAspirin));

        // different name, all other attributes same -> returns false
        editedAspirin = new PrescriptionBuilder(ASPIRIN).withName(VALID_NAME_PROPRANOLOL).build();
        assertFalse(ASPIRIN.isSamePrescription(editedAspirin));

        // name differs in case, all other attributes same -> returns false
        Prescription editedPropranolol = new PrescriptionBuilder(PROPRANOLOL)
            .withName(VALID_NAME_PROPRANOLOL.toLowerCase()).build();
        assertFalse(PROPRANOLOL.isSamePrescription(editedPropranolol));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_PROPRANOLOL + " ";
        editedPropranolol = new PrescriptionBuilder(PROPRANOLOL).withName(nameWithTrailingSpaces).build();
        assertFalse(PROPRANOLOL.isSamePrescription(editedPropranolol));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Prescription aspirinCopy = new PrescriptionBuilder(ASPIRIN).build();
        assertTrue(ASPIRIN.equals(aspirinCopy));

        // same object -> returns true
        assertTrue(ASPIRIN.equals(ASPIRIN));

        // null -> returns false
        assertFalse(ASPIRIN.equals(null));

        // different type -> returns false
        assertFalse(ASPIRIN.equals(5));

        // different prescription -> returns false
        assertFalse(ASPIRIN.equals(PROPRANOLOL));

        // different name -> returns false
        Prescription editedAspirin = new PrescriptionBuilder(ASPIRIN).withName(VALID_NAME_PROPRANOLOL).build();
        assertFalse(ASPIRIN.equals(editedAspirin));

        // different start date -> returns false
        editedAspirin = new PrescriptionBuilder(ASPIRIN).withStartDate(VALID_START_DATE_PROPRANOLOL).build();
        assertFalse(ASPIRIN.equals(editedAspirin));

        // different tags -> returns false
        // editedAspirin = new PrescriptionBuilder(ASPIRIN).withTags(VALID_TAG_HUSBAND).build();
        // assertFalse(ASPIRIN.equals(editedAspirin));
    }

    @Test
    public void toStringMethod() {
        String expected = Prescription.class.getCanonicalName()
                    + "{name=" + ASPIRIN.getName()
                    + ", dosage=" + ASPIRIN.getDosage()
                    + ", frequency=" + ASPIRIN.getFrequency()
                    + ", startDate=" + ASPIRIN.getStartDate()
                    + ", endDate=" + ASPIRIN.getEndDate()
                    + ", expiryDate=" + ASPIRIN.getExpiryDate()
                    + ", totalStock=" + ASPIRIN.getTotalStock()
                    + ", consumptionCount=" + ASPIRIN.getConsumptionCount()
                    + ", isCompleted=" + ASPIRIN.getIsCompleted()
                    + ", note=" + ASPIRIN.getNote() + "}";
        assertEquals(expected, ASPIRIN.toString());
    }
}
