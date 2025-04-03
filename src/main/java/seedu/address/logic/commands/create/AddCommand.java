package seedu.address.logic.commands.create;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ItemCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ManagerAndList;

/**
 * Abstract command to add an {@code Item} to the model.
 *
 * @param <T> the type of {@code Item} being added, which must extend {@link Item}.
 */
public abstract class AddCommand<T extends ManagerAndList<U>, U extends Item>
        extends ItemCommand<T, U> {

    public static final String COMMAND_WORD = "add";
    protected final U itemToAdd;

    /**
     * Creates an {@code AddCommand} to add the specified {@code item}
     */
    public AddCommand(U item, Function<Model, T> managerAndListGetter) {
        super(managerAndListGetter);
        requireNonNull(item);
        itemToAdd = item;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        T managerAndList = managerAndListGetter.apply(model);
        if (managerAndList.hasItem(itemToAdd)) {
            throw new CommandException(getDuplicateItemMessage());
        }

        managerAndList.addItem(itemToAdd);
        return new CommandResult(getSuccessMessage(itemToAdd));
    }

    /**
     * Returns the message to be displayed when the item being added is a duplicate.
     */
    public abstract String getDuplicateItemMessage();

    /**
     * Returns the message to be displayed when the item is successfully added to the model.
     */
    public abstract String getSuccessMessage(U itemToAdd);

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand<?, ?> otherAddCommand)) {
            return false;
        }

        return itemToAdd.equals(otherAddCommand.itemToAdd)
                && managerAndListGetter.equals(otherAddCommand.managerAndListGetter);
    }

    @Override
    public int hashCode() {
        return itemToAdd.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", itemToAdd).toString();
    }
}
