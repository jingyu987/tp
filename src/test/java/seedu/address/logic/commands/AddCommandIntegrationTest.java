package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPrescriptions.getTypicalPrescriptionList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.prescription.Prescription;
import seedu.address.testutil.PrescriptionBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrescriptionList(), getTypicalPrescriptionList(), new UserPrefs());
    }

    @Test
    public void execute_newPrescription_success() {
        Prescription validPrescription = new PrescriptionBuilder().withName("Methadone").build();

        Model expectedModel = new ModelManager(model.getPrescriptionList(),
            model.getCompletedPrescriptionList(), new UserPrefs());
        expectedModel.addPrescription(validPrescription);

        assertCommandSuccess(new AddCommand(validPrescription), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPrescription)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Prescription personInList = model.getPrescriptionList().getPrescriptionList().get(0);
        assertCommandFailure(new AddCommand(personInList), model,
                AddCommand.MESSAGE_DUPLICATE_PRESCRIPTION);
    }

}
