package seedu.module.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.module.commons.core.GuiSettings;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.module.Module;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ModuleBook.
     *
     * @see seedu.module.model.Model#getModuleBook()
     */
    ReadOnlyModuleBook getModuleBook();

    /**
     * Returns an unmodifiable view of the filtered list of modules
     */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Returns the user prefs' Module book file path.
     */
    Path getModuleBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
