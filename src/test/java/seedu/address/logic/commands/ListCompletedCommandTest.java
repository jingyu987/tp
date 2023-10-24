package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCompletedPrescriptionAtIndex;
import static seedu.address.testutil.CompletedPrescriptions.getCompletedPrescriptionList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRESCRIPTION;
import static seedu.address.testutil.TypicalPrescriptions.getTypicalPrescriptionList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PrescriptionList;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the seedu.address.model.ModelPrescription)
 * and unit tests for ListCompletedCommand.
 */
public class ListCompletedCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrescriptionList(), getCompletedPrescriptionList(), new UserPrefs());
        expectedModel = new ModelManager(model.getPrescriptionList(),
                model.getCompletedPrescriptionList(), new UserPrefs());
    }

    @Test
    public void execute_listCompletedIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCompletedCommand(), model,
                ListCompletedCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listCompletedIsFiltered_showsEverything() {
        showCompletedPrescriptionAtIndex(model, INDEX_FIRST_PRESCRIPTION);
        assertCommandSuccess(new ListCompletedCommand(), model,
                ListCompletedCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listCompletedEmptyList_showsEmptyList() {
        model.setCompletedPrescriptionList(new PrescriptionList());
        expectedModel = new ModelManager(model.getPrescriptionList(),
                model.getCompletedPrescriptionList(), new UserPrefs());

        assertCommandSuccess(new ListCompletedCommand(), model,
                ListCompletedCommand.MESSAGE_EMPTY_LIST, expectedModel);
    }
}
