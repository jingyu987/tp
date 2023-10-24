package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRESCRIPTIONS;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.prescription.Prescription;

/**
 * Lists all completed prescriptions to the user.
 */
public class ListCompletedCommand extends Command {

    public static final String COMMAND_WORD = "listCompleted";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all prescriptions that were completed.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listing all prescriptions that were completed.";
    public static final String MESSAGE_EMPTY_LIST = "No completed prescriptions.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<Prescription> completedPrescriptionList = model.getFilteredCompletedPrescriptionList();
        model.updateFilteredCompletedPrescriptionList(PREDICATE_SHOW_ALL_PRESCRIPTIONS);
        if (completedPrescriptionList.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        } else {
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ListCompletedCommand;
    }
}
