package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.EditCommand.EditPrescriptionDescriptor;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSUMPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_STOCK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditPrescriptionCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DOSAGE, PREFIX_FREQUENCY, PREFIX_START_DATE,
                        PREFIX_END_DATE, PREFIX_EXPIRY_DATE, PREFIX_TOTAL_STOCK, PREFIX_CONSUMPTION, PREFIX_NOTE);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DOSAGE, PREFIX_FREQUENCY, PREFIX_START_DATE,
                PREFIX_END_DATE, PREFIX_EXPIRY_DATE, PREFIX_TOTAL_STOCK, PREFIX_CONSUMPTION, PREFIX_NOTE);

        EditPrescriptionDescriptor editPrescriptionDescriptor = new EditPrescriptionDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPrescriptionDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_DOSAGE).isPresent()) {
            editPrescriptionDescriptor.setDosage(ParserUtil.parseDosage(argMultimap.getValue(PREFIX_DOSAGE).get()));
        }

        if (argMultimap.getValue(PREFIX_FREQUENCY).isPresent()) {
            editPrescriptionDescriptor.setFrequency(
                    ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQUENCY).get()));
        }

        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            editPrescriptionDescriptor.setStartDate(
                    ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_START_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            editPrescriptionDescriptor.setEndDate(ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_END_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_EXPIRY_DATE).isPresent()) {
            editPrescriptionDescriptor.setExpiryDate(
                    ParserUtil.parseExpiryDate(argMultimap.getValue(PREFIX_EXPIRY_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_TOTAL_STOCK).isPresent()) {
            editPrescriptionDescriptor.setTotalStock(
                    ParserUtil.parseTotalStock(argMultimap.getValue(PREFIX_TOTAL_STOCK).get()));
        }

        if (argMultimap.getValue(PREFIX_CONSUMPTION).isPresent()) {
            editPrescriptionDescriptor.setConsumptionCount(
                    ParserUtil.parseConsumptionCount(argMultimap.getValue(PREFIX_CONSUMPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            editPrescriptionDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get()));
        }

        if (!editPrescriptionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPrescriptionDescriptor);
    }
}
