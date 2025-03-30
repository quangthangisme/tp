package seedu.address.logic.commands.read;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ItemCommand;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Abstract command for listing all {@code T} items in the model.
 *
 * @param <T> the type of {@code Item} being listed, which must extend {@link Item}.
 */
public abstract class ListCommand<T extends Item> extends ItemCommand<T> {

    public static final String COMMAND_WORD = "list";

    /**
     * Creates a {@code ListCommand}.
     */
    public ListCommand(Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter) {
        super(managerAndListGetter);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = managerAndListGetter.apply(model);
        managerAndList.showAllItems();
        return new CommandResult(getSuccessMessage());
    }

    /**
     * Returns a success message to be displayed when the command executes successfully.
     */
    public abstract String getSuccessMessage();

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListCommand<? extends Item> otherListCommand)) {
            return false;
        }

        return managerAndListGetter.equals(otherListCommand.managerAndListGetter);
    }

    @Override
    public int hashCode() {
        return managerAndListGetter.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toList", managerAndListGetter).toString();
    }
}
