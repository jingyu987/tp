package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSUMPTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRESCRIPTION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TakeCommand;

public class TakeCommandParserTest {

    private TakeCommandParser parser = new TakeCommandParser();

    @Test
    public void parse_validArgs_success() {
        // Valid arguments with a valid Index and dosage
        assertParseSuccess(parser, "1 " + PREFIX_CONSUMPTION + "2",
                new TakeCommand(Index.fromOneBased(1), 2));
    }

    @Test
    public void parse_notIntegerDosage_failure() {
        // Invalid dosage (not an integer)
        assertParseFailure(parser, "1 "
                + PREFIX_CONSUMPTION + "abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TakeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIntegerDosage_failure() {
        // Invalid dosage (not an integer)
        assertParseFailure(parser, " " + INDEX_FIRST_PRESCRIPTION
                + PREFIX_CONSUMPTION + "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TakeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPreamble_failure() {
        // Empty input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        TakeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespacePreamble_failure() {
        // Whitespace input
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        TakeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_failure() {
        // Missing Index
        assertParseFailure(parser, " " + PREFIX_CONSUMPTION + "2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            TakeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingDosage_failure() {
        // Missing dosage
        assertParseFailure(parser, " " + INDEX_FIRST_PRESCRIPTION, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            TakeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraValues_failure() {
        // Random values
        assertParseFailure(parser, "ABCDEFGH",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        TakeCommand.MESSAGE_USAGE));

        // Random prefixes
        assertParseFailure(parser, "mn/ABCD d/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            TakeCommand.MESSAGE_USAGE));
    }
}
