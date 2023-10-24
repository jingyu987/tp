package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DOSAGE_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.DOSAGE_DESC_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.FREQUENCY_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.FREQUENCY_DESC_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DOSAGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FREQUENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_PROPRANOLOL;
import static seedu.address.logic.commands.CommandTestUtil.STOCK_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.STOCK_DESC_PROPRANOLOL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPrescriptions.ASPIRIN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.prescription.Date;
import seedu.address.model.prescription.Dosage;
import seedu.address.model.prescription.Frequency;
import seedu.address.model.prescription.Name;
import seedu.address.model.prescription.Prescription;
import seedu.address.testutil.PrescriptionBuilder;


public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Prescription expectedPrescription = new PrescriptionBuilder(ASPIRIN).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_ASPIRIN + DOSAGE_DESC_ASPIRIN
                        + FREQUENCY_DESC_ASPIRIN + START_DATE_DESC_ASPIRIN + END_DATE_DESC_ASPIRIN
                        + EXPIRY_DATE_DESC_ASPIRIN + STOCK_DESC_ASPIRIN + NOTE_DESC_ASPIRIN,
                new AddCommand(expectedPrescription));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + DOSAGE_DESC_ASPIRIN
                        + FREQUENCY_DESC_ASPIRIN + START_DATE_DESC_ASPIRIN + END_DATE_DESC_ASPIRIN
                        + EXPIRY_DATE_DESC_ASPIRIN + STOCK_DESC_ASPIRIN + NOTE_DESC_ASPIRIN,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DOSAGE_DESC_PROPRANOLOL + FREQUENCY_DESC_PROPRANOLOL
                + START_DATE_DESC_PROPRANOLOL + END_DATE_DESC_PROPRANOLOL
                + EXPIRY_DATE_DESC_PROPRANOLOL + STOCK_DESC_PROPRANOLOL
                + NOTE_DESC_PROPRANOLOL, Name.MESSAGE_CONSTRAINTS);

        // invalid dosage
        assertParseFailure(parser, NAME_DESC_PROPRANOLOL + INVALID_DOSAGE_DESC + FREQUENCY_DESC_PROPRANOLOL
                + START_DATE_DESC_PROPRANOLOL + END_DATE_DESC_PROPRANOLOL
                + EXPIRY_DATE_DESC_PROPRANOLOL + STOCK_DESC_PROPRANOLOL
                + NOTE_DESC_PROPRANOLOL, Dosage.MESSAGE_CONSTRAINTS);

        // invalid frequency
        assertParseFailure(parser, NAME_DESC_PROPRANOLOL + DOSAGE_DESC_PROPRANOLOL
                + INVALID_FREQUENCY_DESC + START_DATE_DESC_PROPRANOLOL
                + END_DATE_DESC_PROPRANOLOL + EXPIRY_DATE_DESC_PROPRANOLOL
                + STOCK_DESC_PROPRANOLOL + NOTE_DESC_PROPRANOLOL,
                Frequency.MESSAGE_CONSTRAINTS);

        // invalid start date
        assertParseFailure(parser, NAME_DESC_PROPRANOLOL + DOSAGE_DESC_PROPRANOLOL
                + FREQUENCY_DESC_PROPRANOLOL + INVALID_START_DATE_DESC
                + END_DATE_DESC_PROPRANOLOL + EXPIRY_DATE_DESC_PROPRANOLOL
                + STOCK_DESC_PROPRANOLOL + NOTE_DESC_PROPRANOLOL,
                Date.MESSAGE_CONSTRAINTS);
    }
}
