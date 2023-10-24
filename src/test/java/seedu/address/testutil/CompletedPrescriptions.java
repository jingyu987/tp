package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PrescriptionList;
import seedu.address.model.prescription.Prescription;

/**
 * A utility class containing a list of {@code Prescription} objects to be used in tests.
 */
public class CompletedPrescriptions {

    public static final Prescription ASPIRIN = new PrescriptionBuilder().withName("Aspirin")
            .withDosage("1")
            .withFrequency("Daily")
            .withStartDate("01/10/2021")
            .withEndDate("23/02/2022")
            .withExpiryDate("12/12/2024")
            .withStock("100")
            .withConsumptionCount("0")
            .withNote("Take before food")
            .build();

    public static final Prescription PROPRANOLOL = new PrescriptionBuilder().withName("Propranolol")
            .withDosage("4")
            .withFrequency("Daily")
            .withStartDate("01/08/2021")
            .withEndDate("20/12/2022")
            .withExpiryDate("22/07/2024")
            .withStock("500")
            .withConsumptionCount("0")
            .withNote("Take after food")
            .build();

    public static final Prescription ERGOTAMINE = new PrescriptionBuilder().withName("Ergotamine")
            .withDosage("1")
            .withFrequency("Weekly")
            .withStartDate("01/08/2021")
            .withEndDate("20/12/2022")
            .withExpiryDate("22/07/2024")
            .withStock("50")
            .withConsumptionCount("0")
            .withNote("Take before food")
            .build();

    public static final Prescription NAPROXEN = new PrescriptionBuilder().withName("Naproxen")
            .withDosage("2")
            .withFrequency("Weekly")
            .withStartDate("01/08/2021")
            .withEndDate("20/06/2022")
            .withExpiryDate("13/01/2025")
            .withStock("75")
            .withConsumptionCount("0")
            .withNote("Take before food")
            .build();

    public static final Prescription ZOMIG = new PrescriptionBuilder().withName("Zolmitriptan Rapimelt")
            .withDosage("1")
            .withFrequency("Monthly")
            .withStartDate("12/10/2021")
            .withEndDate("12/10/2022")
            .withExpiryDate("15/03/2025")
            .withStock("20")
            .withConsumptionCount("0")
            .withNote("Drowsy")
            .build();

    // Manually added
    public static final Prescription IBUPROFEN = new PrescriptionBuilder().withName("Ibuprofen")
            .withDosage("2")
            .withFrequency("Daily")
            .withStartDate("12/10/2021")
            .withEndDate("12/10/2022")
            .withExpiryDate("15/03/2025")
            .withStock("50")
            .withConsumptionCount("0")
            .withNote("Take after food")
            .build();

    public static final Prescription CAFFOX = new PrescriptionBuilder().withName("Caffox")
            .withDosage("1")
            .withFrequency("Daily")
            .withStartDate("12/10/2021")
            .withEndDate("12/10/2022")
            .withExpiryDate("15/03/2025")
            .withStock("20")
            .withConsumptionCount("0")
            .withNote("Take before food")
            .build();

    private CompletedPrescriptions() {} // prevents instantiation

    /**
     * Returns an {@code PrescriptionList} with all the completed prescriptions.
     */
    public static PrescriptionList getCompletedPrescriptionList() {
        PrescriptionList pl = new PrescriptionList();
        for (Prescription prescription : getCompletedPrescriptions()) {
            pl.addPrescription(prescription);
        }
        return pl;
    }

    public static List<Prescription> getCompletedPrescriptions() {
        return new ArrayList<>(Arrays.asList(ASPIRIN, PROPRANOLOL, ERGOTAMINE, NAPROXEN, ZOMIG));
    }
}
