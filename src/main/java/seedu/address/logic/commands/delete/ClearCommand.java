package seedu.address.logic.commands.delete;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ItemCommand;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ManagerAndList;

/**
 * Abstract command to clear the list of {@code Item} objects in the model.
 *
 * @param <T> the type of {@code Item} being cleared, which must extend {@link Item}.
 */
public abstract class ClearCommand<T extends ManagerAndList<U>, U extends Item>
        extends ItemCommand<T, U> {

    public static final String COMMAND_WORD = "clear";

    /**
     * Creates a {@code ClearCommand} to clear the item list in the model.
     */
    public ClearCommand(Function<Model, T> managerAndListGetter) {
        super(managerAndListGetter);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        T managerAndList = managerAndListGetter.apply(model);
        clear(managerAndList);
        cascade(model);
        return new CommandResult(getSuccessMessage());
    }

    /**
     * Clear the manager and list.
     */
    public abstract void clear(T managerAndList);

    /**
     * Propagate the result of the deletion.
     */
    public abstract void cascade(Model model);

    /**
     * Returns the success message to be displayed when the item list is cleared.
     */
    public abstract String getSuccessMessage();

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClearCommand<?, ?> otherClearCommand)) {
            return false;
        }

        return managerAndListGetter.equals(otherClearCommand.managerAndListGetter);
    }

    @Override
    public int hashCode() {
        return managerAndListGetter.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toClear", managerAndListGetter)
                .toString();
    }
}
