package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.DOSAGE_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DATE_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.FREQUENCY_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_ASPIRIN;
import static seedu.address.logic.commands.CommandTestUtil.STOCK_DESC_ASPIRIN;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPrescriptions.ASPIRIN;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyPrescriptionList;
import seedu.address.model.UserPrefs;
import seedu.address.model.prescription.Prescription;
import seedu.address.storage.JsonCompletedPrescriptionListStorage;
import seedu.address.storage.JsonPrescriptionListStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PrescriptionBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonPrescriptionListStorage prescriptionListStorage =
            new JsonPrescriptionListStorage(temporaryFolder.resolve("prescriptionList.json"));
        JsonCompletedPrescriptionListStorage completedPrescriptionListStorage =
            new JsonCompletedPrescriptionListStorage(temporaryFolder.resolve("completedPrescriptionList.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(
            temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(prescriptionListStorage,
                completedPrescriptionListStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    /*
    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PRESCRIPTION_DISPLAYED_INDEX);
    }
    */

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_EMPTY_LIST, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
            LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
            LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredPrescriptionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPrescriptionList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
        Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
        String expectedMessage) {
        Model expectedModel = new ModelManager(
            model.getPrescriptionList(), model.getCompletedPrescriptionList(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
        String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the LogicPrescription component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the LogicPrescription component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManagerPrescription with a PrescriptionListStorage that throws the IOException e when saving
        JsonPrescriptionListStorage prescriptionListStorage = new JsonPrescriptionListStorage(prefPath) {
            @Override
            public void savePrescriptionList(ReadOnlyPrescriptionList prescriptionList, Path filePath)
                    throws IOException {
                throw e;
            }
        };
        JsonCompletedPrescriptionListStorage completedPrescriptionListStorage =
            new JsonCompletedPrescriptionListStorage(prefPath);

        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(prescriptionListStorage,
            completedPrescriptionListStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the savePrescriptionList method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD
                + NAME_DESC_ASPIRIN
                + DOSAGE_DESC_ASPIRIN
                + FREQUENCY_DESC_ASPIRIN
                + START_DATE_DESC_ASPIRIN
                + END_DATE_DESC_ASPIRIN
                + EXPIRY_DATE_DESC_ASPIRIN
                + STOCK_DESC_ASPIRIN
                + NOTE_DESC_ASPIRIN;
        Prescription expectedPrescription = new PrescriptionBuilder(ASPIRIN).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPrescription(expectedPrescription);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
