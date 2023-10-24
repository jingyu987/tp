package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UntakeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.prescription.ConsumptionCount;

/**
 * Parses input arguments and creates a new UntakePrescriptionCommand object
 */
public class UntakeCommandParser implements Parser<UntakeCommand> {

    /**
     * Parses the given arguments to create an UntakePrescriptionCommand.
     *
     * @param args User input representing the command.
     * @return An UntakePrescriptionCommand for untaking a specified number of doses from a prescription.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public UntakeCommand parse(String args) throws ParseException {
        try {
            String[] argParts = args.trim().split("c/");

            if (argParts.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntakeCommand.MESSAGE_USAGE));
            }

            Index index = ParserUtil.parseIndex(argParts[0].trim());
            ConsumptionCount consumptionCount = ParserUtil.parseConsumptionCount(argParts[1].trim());
            int dosesToUntake = Integer.parseInt(consumptionCount.getConsumptionCount());

            return new UntakeCommand(index, dosesToUntake);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntakeCommand.MESSAGE_USAGE));
        }
    }
}
