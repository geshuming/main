package seedu.module.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.type.TypeReference;

import seedu.module.commons.core.LogsCenter;
import seedu.module.commons.exceptions.DataConversionException;
import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.commons.util.FileUtil;
import seedu.module.commons.util.JsonUtil;
import seedu.module.model.ModuleBook;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;

/**
 * A class to access ModuleBook data stored as a json file on the hard disk.
 */
public class JsonModuleBookStorage implements ModuleBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonModuleBookStorage.class);

    private Path filePath;

    public JsonModuleBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getModuleBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ModuleBook> readModuleBook() throws DataConversionException {
        return readModuleBook(filePath);
    }

    /**
     * Similar to {@link #readModuleBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ModuleBook> readModuleBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableModuleBook> jsonModuleBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableModuleBook.class);
        if (!jsonModuleBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonModuleBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public ArchivedModuleList readArchivedModules() {
        Optional<List<JsonAdaptedArchivedModule>> jsonArchivedModules = Optional.empty();
        ArchivedModuleList archivedModules = new ArchivedModuleList();
        Path archivedModulesPath;

        // The code is presumed to work every time.
        // TODO: Implement GUI warning to user on data failure
        try {
            archivedModulesPath = Paths.get(getClass().getClassLoader()
                .getResource("data/archivedModules.json").toURI());

            jsonArchivedModules = JsonUtil.readJsonFile(archivedModulesPath,
                new TypeReference<List<JsonAdaptedArchivedModule>>(){});
        } catch (URISyntaxException | DataConversionException e) {
            logger.warning("Failed to fetch data of archived modules. Returning an empty list." + e.toString());
            return archivedModules;
        }

        if (!jsonArchivedModules.isPresent()) {
            return archivedModules;
        }

        List<JsonAdaptedArchivedModule> modules = jsonArchivedModules.get();

        for (JsonAdaptedArchivedModule jsonAdaptedArchivedModule : modules) {
            ArchivedModule module = jsonAdaptedArchivedModule.toModelType();

            archivedModules.add(module);
        }

        return archivedModules;
    }

    @Override
    public void saveModuleBook(ReadOnlyModuleBook moduleBook) throws IOException {
        saveModuleBook(moduleBook, filePath);
    }

    /**
     * Similar to {@link #saveModuleBook(ReadOnlyModuleBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveModuleBook(ReadOnlyModuleBook moduleBook, Path filePath) throws IOException {
        requireNonNull(moduleBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableModuleBook(moduleBook), filePath);
    }

}
