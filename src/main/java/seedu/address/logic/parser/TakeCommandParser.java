package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TakeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.prescription.ConsumptionCount;

/**
 * Parses input arguments and creates a new TakePrescriptionCommand object
 */
public class TakeCommandParser implements Parser<TakeCommand> {

    /**
     * Parses the given arguments to create a TakePrescriptionCommand.
     *
     * @param args User input representing the command.
     * @return A TakePrescriptionCommand for taking a specified number of doses from a prescription.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public TakeCommand parse(String args) throws ParseException {
        try {
            String[] argParts = args.trim().split("c/");

            if (argParts.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TakeCommand.MESSAGE_USAGE));
            }

            Index index = ParserUtil.parseIndex(argParts[0].trim());
            ConsumptionCount consumptionCount = ParserUtil.parseConsumptionCount(argParts[1].trim());
            int dosesToTake = Integer.parseInt(consumptionCount.getConsumptionCount());

            return new TakeCommand(index, dosesToTake);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TakeCommand.MESSAGE_USAGE));
        }
    }
}
