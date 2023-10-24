package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCompletedCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCompletedCommand object
 */
public class ListCompletedCommandParser implements Parser<ListCompletedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCompletedCommand
     * and returns a ListCompletedCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCompletedCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCompletedCommand.MESSAGE_USAGE));
        }

        return new ListCompletedCommand();
    }
}
