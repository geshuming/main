package seedu.module.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Module;

/**
 * Returns from a module-view state (from View Command). Effectively does nothing when not in that state.
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Returns from the Module View.\n";

    public static final String MESSAGE_BACK_COMMAND_SUCCESS = "Returned to Home Page.";
    public static final String MESSAGE_BACK_COMMAND_NOT_EFFECTIVE = "Already on Home Page.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Module displayedModule = model.getDisplayedModule();

        if (displayedModule == null) {
            return new CommandResult(MESSAGE_BACK_COMMAND_NOT_EFFECTIVE,
                false, true, false);
        }

        model.setDisplayedModule(null);
        return new CommandResult(MESSAGE_BACK_COMMAND_SUCCESS, false, true, false);
    }
}
