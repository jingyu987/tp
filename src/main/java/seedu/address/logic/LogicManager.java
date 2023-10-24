package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCompletedCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PrescriptionListParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.PrescriptionList;
import seedu.address.model.ReadOnlyPrescriptionList;
import seedu.address.model.prescription.Prescription;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final PrescriptionListParser prescriptionListParser;
    private boolean isDisplayingCompletedList;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        prescriptionListParser = new PrescriptionListParser();
        this.isDisplayingCompletedList = false;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, IOException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = prescriptionListParser.parseCommand(commandText);

        if (command instanceof ListCompletedCommand) {
            // Handle the ListCompletedCommand
            this.isDisplayingCompletedList = true;
            checkAndMoveEndedPrescriptions();
        } else {
            // Handle other commands...
            this.isDisplayingCompletedList = false;
        }

        commandResult = command.execute(model);

        try {
            storage.savePrescriptionList(model.getPrescriptionList());
            storage.saveCompletedPrescriptionList(model.getCompletedPrescriptionList());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    /**
     * Deletes prescriptions that are past the end date and stores them in the completed prescription list.
     */
    private void checkAndMoveEndedPrescriptions() throws IOException {
        PrescriptionList prescriptionListCopy = new PrescriptionList(model.getPrescriptionList());
        for (Prescription prescription : prescriptionListCopy.getPrescriptionList()) {
            if (prescription.isEnded()) {
                model.deletePrescription(prescription);
                model.addCompletedPrescription(prescription);
            }
        }
        storage.savePrescriptionList(model.getPrescriptionList());
        storage.saveCompletedPrescriptionList(model.getCompletedPrescriptionList());
    }

    @Override
    public ReadOnlyPrescriptionList getPrescriptionList() {
        return model.getPrescriptionList();
    }

    @Override
    public ObservableList<Prescription> getFilteredPrescriptionList() {
        return model.getFilteredPrescriptionList();
    }
    @Override
    public ObservableList<Prescription> getFilteredCompletedPrescriptionList() {
        return model.getFilteredCompletedPrescriptionList();
    }

    @Override
    public boolean getIsDisplayingCompletedList() {
        return this.isDisplayingCompletedList;
    }
    @Override
    public Path getPrescriptionListFilePath() {
        return model.getPrescriptionListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
