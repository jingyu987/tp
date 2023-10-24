package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyPrescriptionList;

/**
 * Represents a storage for the completed version of {@link seedu.address.model.PrescriptionList}.
 */
public interface CompletedPrescriptionListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCompletedPrescriptionListFilePath();

    /**
     * Returns CompletedPrescriptionList data as a {@link ReadOnlyPrescriptionList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyPrescriptionList> readCompletedPrescriptionList() throws DataLoadingException;

    /**
     * @see #getPrescriptionListFilePath()
     */
    Optional<ReadOnlyPrescriptionList> readCompletedPrescriptionList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyPrescriptionList} to the storage.
     * @param completedPrescriptionList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCompletedPrescriptionList(ReadOnlyPrescriptionList completedPrescriptionList) throws IOException;

    /**
     * @see #savePrescriptionList(ReadOnlyPrescriptionList)
     */
    void saveCompletedPrescriptionList(ReadOnlyPrescriptionList completedPrescriptionList,
            Path filePath) throws IOException;

}
