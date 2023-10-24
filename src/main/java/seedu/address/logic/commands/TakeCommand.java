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
 * Represents a command to take a specified number of doses of a prescription.
 */
public class TakeCommand extends Command {
    public static final String COMMAND_WORD = "take";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Takes a specified number of doses of a prescription.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CONSUMPTION + "number_of_doses (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_CONSUMPTION + "2";

    public static final String MESSAGE_SUCCESS = "Doses taken from: %1$s.";
    public static final String MESSAGE_PRESCRIPTION_NOT_FOUND = "The specified prescription does not exist.";
    public static final String MESSAGE_INSUFFICIENT_STOCK = "There is insufficient stock for this prescription.";

    private final Index targetIndex;
    private final int dosesToTake;

    /**
     * Creates a TakePrescriptionCommand to take the specified number of doses from a prescription.
     *
     * @param targetIndex      The index of the prescription in the prescription list.
     * @param dosesToTake      Number of doses to take.
     */
    public TakeCommand(Index targetIndex, int dosesToTake) {
        this.targetIndex = targetIndex;
        this.dosesToTake = dosesToTake;
    }

    /**
     * Executes the TakePrescriptionCommand to take the specified doses from the prescription.
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

        if (totalStock.isPresent() && (Integer.parseInt(totalStock.get().toString()) - dosesToTake < 0)) {
            throw new CommandException(MESSAGE_INSUFFICIENT_STOCK);
        }

        if (totalStock.isPresent()) {
            totalStock.get().decrementCount(dosesToTake);
        }
        prescription.getConsumptionCount().incrementCount(dosesToTake);

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

        if (!(other instanceof TakeCommand)) {
            return false;
        }

        TakeCommand otherCommand = (TakeCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && dosesToTake == otherCommand.dosesToTake;
    }
}
