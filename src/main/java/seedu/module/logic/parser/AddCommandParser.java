package seedu.module.logic.parser;

import seedu.module.logic.commands.AddCommand;
import seedu.module.model.module.Module;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public AddCommand parse(String args) {

        Module module = ArchiveStub.searchArchiveStub(args); //stub

        return new AddCommand(module);
    }

}
