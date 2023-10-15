package seedu.address.logic.parser;

import static seedu.address.logic.MessagesPrescription.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.address.logic.commands.ListTodayPrescriptionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListTodayPrescriptionCommand object
 */
public class ListTodayPrescriptionCommandParser implements ParserPrescription<ListTodayPrescriptionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListTodayPrescriptionCommand
     * and returns a ListTodayPrescriptionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListTodayPrescriptionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListTodayPrescriptionCommand.MESSAGE_USAGE));
        }

        return new ListTodayPrescriptionCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
