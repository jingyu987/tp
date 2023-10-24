package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSUMPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_STOCK;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PrescriptionList;
import seedu.address.model.prescription.NameContainsKeywordsPredicate;
import seedu.address.model.prescription.Prescription;
import seedu.address.testutil.EditPrescriptionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ASPIRIN = "Aspirin";
    public static final String VALID_NAME_PROPRANOLOL = "Propranolol";
    public static final String VALID_NAME_METHADONE = "Methadone";

    public static final String VALID_DOSAGE_ASPIRIN = "1";
    public static final String VALID_DOSAGE_PROPRANOLOL = "4";

    public static final String VALID_FREQUENCY_ASPIRIN = "Daily";
    public static final String VALID_FREQUENCY_PROPRANOLOL = "Weekly";

    public static final String VALID_START_DATE_ASPIRIN = "01/10/2023";
    public static final String VALID_START_DATE_PROPRANOLOL = "01/08/2023";

    public static final String VALID_END_DATE_ASPIRIN = "23/02/2024";
    public static final String VALID_END_DATE_PROPRANOLOL = "20/12/2024";

    public static final String VALID_EXPIRY_DATE_ASPIRIN = "12/12/2024";
    public static final String VALID_EXPIRY_DATE_PROPRANOLOL = "22/07/2024";

    public static final String VALID_STOCK_ASPIRIN = "100";
    public static final String VALID_STOCK_PROPRANOLOL = "500";

    public static final String VALID_CONSUMPTION_ASPIRIN = "1";
    public static final String VALID_CONSUMPTION_PROPRANOLOL = "2";

    public static final String VALID_NOTE_ASPIRIN = "Take before food";
    public static final String VALID_NOTE_PROPRANOLOL = "Take after food";
    // public static final String VALID_TAG_HUSBAND = "husband";
    // public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_ASPIRIN = " " + PREFIX_NAME + VALID_NAME_ASPIRIN;
    public static final String NAME_DESC_PROPRANOLOL = " " + PREFIX_NAME + VALID_NAME_PROPRANOLOL;
    public static final String NAME_DESC_METHADONE = " " + PREFIX_NAME + VALID_NAME_METHADONE;
    public static final String DOSAGE_DESC_ASPIRIN = " " + PREFIX_DOSAGE + VALID_DOSAGE_ASPIRIN;
    public static final String DOSAGE_DESC_PROPRANOLOL = " " + PREFIX_DOSAGE + VALID_DOSAGE_PROPRANOLOL;
    public static final String FREQUENCY_DESC_ASPIRIN = " " + PREFIX_FREQUENCY + VALID_FREQUENCY_ASPIRIN;
    public static final String FREQUENCY_DESC_PROPRANOLOL = " " + PREFIX_FREQUENCY + VALID_FREQUENCY_PROPRANOLOL;
    public static final String START_DATE_DESC_ASPIRIN = " " + PREFIX_START_DATE + VALID_START_DATE_ASPIRIN;
    public static final String START_DATE_DESC_PROPRANOLOL = " " + PREFIX_START_DATE + VALID_START_DATE_PROPRANOLOL;
    public static final String END_DATE_DESC_ASPIRIN = " " + PREFIX_END_DATE + VALID_END_DATE_ASPIRIN;
    public static final String END_DATE_DESC_PROPRANOLOL = " " + PREFIX_END_DATE + VALID_END_DATE_PROPRANOLOL;
    public static final String EXPIRY_DATE_DESC_ASPIRIN = " " + PREFIX_EXPIRY_DATE + VALID_EXPIRY_DATE_ASPIRIN;
    public static final String EXPIRY_DATE_DESC_PROPRANOLOL = " " + PREFIX_EXPIRY_DATE + VALID_EXPIRY_DATE_PROPRANOLOL;
    public static final String STOCK_DESC_ASPIRIN = " " + PREFIX_TOTAL_STOCK + VALID_STOCK_ASPIRIN;
    public static final String STOCK_DESC_PROPRANOLOL = " " + PREFIX_TOTAL_STOCK + VALID_STOCK_PROPRANOLOL;
    public static final String CONSUMPTION_COUNT_DESC_ASPIRIN = " " + PREFIX_CONSUMPTION + VALID_CONSUMPTION_ASPIRIN;
    public static final String CONSUMPTION_COUNT_DESC_PROPRANOLOL = " " + PREFIX_CONSUMPTION
            + VALID_CONSUMPTION_PROPRANOLOL;
    public static final String NOTE_DESC_ASPIRIN = " " + PREFIX_NOTE + VALID_NOTE_ASPIRIN;
    public static final String NOTE_DESC_PROPRANOLOL = " " + PREFIX_NOTE + VALID_NOTE_PROPRANOLOL;

    // public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    // public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "@sp!r!n"; // non-alphanumeric not allowed
    public static final String INVALID_DOSAGE_DESC = " " + PREFIX_DOSAGE + "a"; // alphabets not allowed
    public static final String INVALID_FREQUENCY_DESC = " " + PREFIX_FREQUENCY + "Forever"; // invalid value
    public static final String INVALID_START_DATE_DESC = " " + PREFIX_START_DATE + "1/1/2023"; // invalid date format
    public static final String INVALID_END_DATE_DESC = " " + PREFIX_END_DATE + "1/2/24"; // invalid date format
    public static final String INVALID_EXPIRY_DATE_DESC = " " + PREFIX_EXPIRY_DATE + "2024/1/1"; // invalid date format
    public static final String INVALID_STOCK_DESC = " " + PREFIX_TOTAL_STOCK + "a"; // alphabets not allowed
    public static final String INVALID_NOTE_DESC = " " + PREFIX_NOTE + "@invalid note"; // empty string not allowed
    // public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPrescriptionDescriptor DESC_ASPIRIN;
    public static final EditCommand.EditPrescriptionDescriptor DESC_PROPRANOLOL;

    static {
        DESC_ASPIRIN = new EditPrescriptionDescriptorBuilder().withName(VALID_NAME_ASPIRIN)
            .withDosage(VALID_DOSAGE_ASPIRIN).withFrequency(VALID_FREQUENCY_ASPIRIN)
            .withStartDate(VALID_START_DATE_ASPIRIN).withEndDate(VALID_END_DATE_ASPIRIN)
            .withExpiryDate(VALID_EXPIRY_DATE_ASPIRIN).withTotalStock(VALID_STOCK_ASPIRIN)
            .withConsumptionCount(VALID_CONSUMPTION_ASPIRIN).withNote(VALID_NOTE_ASPIRIN).build();
        DESC_PROPRANOLOL = new EditPrescriptionDescriptorBuilder().withName(VALID_NAME_PROPRANOLOL)
                .withDosage(VALID_DOSAGE_PROPRANOLOL).withFrequency(VALID_FREQUENCY_PROPRANOLOL)
                .withStartDate(VALID_START_DATE_PROPRANOLOL).withEndDate(VALID_END_DATE_PROPRANOLOL)
                .withExpiryDate(VALID_EXPIRY_DATE_PROPRANOLOL).withTotalStock(VALID_STOCK_PROPRANOLOL)
                .withConsumptionCount(VALID_CONSUMPTION_PROPRANOLOL).withNote(VALID_NOTE_PROPRANOLOL).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
        CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model,
     * CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
        String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the prescription list, filtered prescription list and selected prescription in {@code actualModel}
     *   remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel,
        String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PrescriptionList expectedPrescriptionList = new PrescriptionList(actualModel.getPrescriptionList());
        List<Prescription> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPrescriptionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        // assertEquals(expectedPrescriptionList, actualModel.getPrescriptionList());
        // assertEquals(expectedFilteredList, actualModel.getFilteredPrescriptionList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the prescription at the given {@code targetIndex} in the
     * {@code model}'s prescription list.
     */
    public static void showPrescriptionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPrescriptionList().size());

        Prescription prescription = model.getFilteredPrescriptionList().get(targetIndex.getZeroBased());
        final String[] splitName = prescription.getName().toString().split("\\s+");
        model.updateFilteredPrescriptionList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPrescriptionList().size());
    }

    /**
     * Updates {@code model}'s filtered completed list to show only the prescription at the given
     * {@code targetIndex} in the
     * {@code model}'s completed prescription list.
     */
    public static void showCompletedPrescriptionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCompletedPrescriptionList().size());

        Prescription prescription = model.getFilteredCompletedPrescriptionList().get(targetIndex.getZeroBased());
        final String[] splitName = prescription.getName().toString().split("\\s+");
        model.updateFilteredCompletedPrescriptionList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCompletedPrescriptionList().size());
    }

}
