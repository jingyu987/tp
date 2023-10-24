package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSUMPTION;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.prescription.Prescription;
import seedu.address.model.prescription.SameNamePredicate;
import seedu.address.model.prescription.Stock;

/**
 * Represents a command to untake a specified number of doses of a prescription.
 */
public class UntakeCommand extends Command {
    public static final String COMMAND_WORD = "untake";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Untakes a specified number of doses of a prescription.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CONSUMPTION + "number_of_doses (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_CONSUMPTION + "2";

    public static final String MESSAGE_SUCCESS = "Doses untaken from: %1$s";
    public static final String MESSAGE_INSUFFICIENT_CONSUMPTION = "Cannot untake more than what you have consumed.";

    private final Index targetIndex;
    private final int dosesToUntake;

    /**
     * Creates an UntakePrescriptionCommand to untake the specified number of doses from a prescription.
     *
     * @param targetIndex      The index of the prescription in the prescription list.
     * @param dosesToTake      Number of doses to untake.
     */
    public UntakeCommand(Index targetIndex, int dosesToTake) {
        this.targetIndex = targetIndex;
        this.dosesToUntake = dosesToTake;
    }

    /**
     * Executes the UntakePrescriptionCommand to untake the specified doses from the prescription.
     *
     * @param model The model of the prescription list.
     * @return A CommandResult with the result of the execution.
     * @throws CommandException If there are errors in executing the command.
     */

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Prescription> lastShownList = model.getFilteredPrescriptionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRESCRIPTION_DISPLAYED_INDEX);
        }

        Prescription prescription = lastShownList.get(targetIndex.getZeroBased());
        Optional<Stock> totalStock = prescription.getTotalStock();

        if (Integer.parseInt(prescription.getConsumptionCount().getConsumptionCount()) - dosesToUntake < 0) {
            throw new CommandException(MESSAGE_INSUFFICIENT_CONSUMPTION);
        }

        if (totalStock.isPresent()) {
            totalStock.get().incrementCount(dosesToUntake);
        }
        prescription.getConsumptionCount().decrementCount(dosesToUntake);

        if (prescription.getDosage().isPresent()
                && Integer.parseInt(prescription.getConsumptionCount().toString())
                >= Integer.parseInt(prescription.getDosage().get().toString())) {
            prescription.setIsCompleted(true);
        } else {
            prescription.setIsCompleted(false);
        }

        Predicate<Prescription> isSameName = new SameNamePredicate(prescription.getName());
        model.updateFilteredPrescriptionList(isSameName);

        return new CommandResult(String.format(MESSAGE_SUCCESS, prescription.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof UntakeCommand)) {
            return false;
        }

        UntakeCommand otherCommand = (UntakeCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && dosesToUntake == otherCommand.dosesToUntake;
    }
}
