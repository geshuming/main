package seedu.module.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.module.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.module.commons.exceptions.DataConversionException;
import seedu.module.model.ModuleBook;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;

public class JsonModuleBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonModuleBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readModuleBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readModuleBook(null));
    }

    private java.util.Optional<ModuleBook> readModuleBook(String filePath) throws Exception {
        return new JsonModuleBookStorage(Paths.get(filePath)).readModuleBook(addToTestDataPathIfNotNull(filePath));
    }

    private ArchivedModuleList readArchivedModules() {
        return new JsonModuleBookStorage(Paths.get("")).readArchivedModules();
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readModuleBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readModuleBook("notJsonFormatModuleBook.json"));
    }

    @Test
    public void readModuleBook_invalidModuleModuleBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readModuleBook("invalidModuleModuleBook.json"));
    }

    @Test
    public void readModuleBook_invalidAndValidModuleModuleBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readModuleBook("invalidAndValidModuleModuleBook.json"));
    }

    //     @Test
    //     public void readAndSaveAddressBook_allInOrder_success() throws Exception {
    //         Path filePath = testFolder.resolve("TempAddressBook.json");
    //         ModuleBook original = getTypicalAddressBook();
    //         JsonModuleBookStorage jsonModuleBookStorage = new JsonModuleBookStorage(filePath);

    //         // Save in new file and read back
    //         jsonModuleBookStorage.saveAddressBook(original, filePath);
    //         ReadOnlyModuleBook readBack = jsonModuleBookStorage.readAddressBook(filePath).get();
    //         assertEquals(original, new ModuleBook(readBack));

    //         // Modify data, overwrite exiting file, and read back
    //         original.addModule(HOON);
    //         original.removePerson(ALICE);
    //         jsonModuleBookStorage.saveAddressBook(original, filePath);
    //         readBack = jsonModuleBookStorage.readAddressBook(filePath).get();
    //         assertEquals(original, new ModuleBook(readBack));

    //         // Save and read without specifying file path
    //         original.addModule(IDA);
    //         jsonModuleBookStorage.saveAddressBook(original); // file path not specified
    //         readBack = jsonModuleBookStorage.readAddressBook().get(); // file path not specified
    //         assertEquals(original, new ModuleBook(readBack));

    //     }

    @Test
    public void saveModuleBook_nullModuleBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModuleBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code ModuleBook} at the specified {@code filePath}.
     */
    private void saveModuleBook(ReadOnlyModuleBook moduleBook, String filePath) {
        try {
            new JsonModuleBookStorage(Paths.get(filePath))
                    .saveModuleBook(moduleBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveModuleBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModuleBook(new ModuleBook(), null));
    }

    @Test
    public void readArchivedModules_validArchivedModules_returnArchivedModules() {
        ArchivedModuleList jsonArchivedModuleList = readArchivedModules();
        ArchivedModuleList expectedArchivedModuleList = new ArchivedModuleList();

        expectedArchivedModuleList.add(new ArchivedModule("CS2103T", "Software Engineering", "Lorem Ipsum"));

        assertEquals(jsonArchivedModuleList, expectedArchivedModuleList);
    }
}
